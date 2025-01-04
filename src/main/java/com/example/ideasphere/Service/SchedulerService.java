package com.example.ideasphere.Service;

import com.example.ideasphere.Model.Competition;
import com.example.ideasphere.Repository.CompetitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final CompetitionRepository competitionRepository;



    @Scheduled(fixedRate = 60000) // Run every minute
    public void RunSchedule() {

        updateExpiredCompetition();
        updateStuckCompetition();
    }
    //  Handle competitions Expired
    public void updateExpiredCompetition(){
        LocalDate today = LocalDate.now();

        List<Competition> expiredCompetitions = competitionRepository.findByEndDateBeforeAndStatusWithSubmissions(today, "Ongoing");

        for (Competition competition : expiredCompetitions) {
            if (competition.getVotingMethod().equalsIgnoreCase("By Organizer")){

                if (competition.getSubmissions().isEmpty()){
                    competition.setStatus("Competition without submissions");
                    competitionRepository.save(competition);
                    continue;
                }
                competition.setStatus("Winner Selection in Progress");
                competitionRepository.save(competition);
            }else if(competition.getVotingMethod().equalsIgnoreCase("By Public Vote")){

                if (competition.getSubmissions().isEmpty()){
                    competition.setStatus("Competition without submissions");
                    competitionRepository.save(competition);
                    continue;
                }

                competition.setStatus("Under Voting Process");
                competitionRepository.save(competition);
            }

        }
    }
    //  Handle competitions stuck in "Winner Selection in Progress"
    public void updateStuckCompetition(){
        LocalDate today = LocalDate.now();

        List<Competition> pendingCompetitions = competitionRepository.findCompetitionByStatus("Winner Selection in Progress");

        for (Competition competition : pendingCompetitions) {
            if (competition.getCompanyCompetition() != null && !competition.getCompanyCompetition().getRewardType().equalsIgnoreCase("Financial")){
                continue;
            }

            long daysSinceEnd = ChronoUnit.DAYS.between(competition.getEndDate(), today);

            if (daysSinceEnd > 7) {
                competition.setStatus("Under Voting Process");
                competition.setEndDate(today.plusDays(3));
                competitionRepository.save(competition);
            }
        }
    }
}
