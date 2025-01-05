package com.example.ideasphere.Service;

import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.Model.*;
import com.example.ideasphere.Repository.CompetitionRepository;
import com.example.ideasphere.Repository.MonthlyDrawParticipantRepository;
import com.example.ideasphere.Repository.MonthlyDrawRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

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
        System.out.println("updateExpiredCompetition - done");
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
                competition.setVoteEndDate(today.plusDays(3));
                competitionRepository.save(competition);
            }
        }
        System.out.println("updateStuckCompetition - done");
    }

    public void updateCompetitionUnderVote(){
        LocalDate today = LocalDate.now();
        List<Competition> VotingCompetitionsExpired = competitionRepository.findByVoteEndDateBeforeAndStatusWithVotes(today, "Under Voting Process");

        for (Competition competition : VotingCompetitionsExpired) {


            Map<Submission, Long> voteCountMap = competition.getVotes().stream()
                    .collect(Collectors.groupingBy(Vote::getSubmission, Collectors.counting()));

            if (voteCountMap.isEmpty()) {
                competition.setVoteEndDate(today.plusDays(3));
                competitionRepository.save(competition);
                continue;
            }

            // Find the maximum vote count
            long maxVotes = voteCountMap.values().stream().max(Long::compareTo).orElse(0L);

            // Get submissions with the maximum vote count (could be more than one in case of tie)
            List<Submission> topSubmissions = voteCountMap.entrySet().stream()
                    .filter(entry -> entry.getValue() == maxVotes)
                    .map(Map.Entry::getKey)
                    .toList();

            if (topSubmissions.size() > 1) {
                // Tie detected
                competition.setStatus("Vote Tie - Organizer Decision");
                for (Submission s : topSubmissions){
                    s.setWinnerEqualedVotes(true);
                }
                competitionRepository.save(competition);
            } else {
                // Single winner
                Submission submission = topSubmissions.get(0);
                competition.setStatus("Completed");
                competition.setParticipantWinner(submission.getParticipant());
                competitionRepository.save(competition);
            }


        }
        System.out.println("updateCompetitionUnderVote - done");
    }
}
