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
    private final MonthlyDrawRepository monthlyDrawRepository;
    private final  EmailSenderJava emailSender;
    private final MonthlyDrawParticipantRepository monthlyDrawParticipantRepository;


    @Scheduled(fixedRate = 60000) // Run every minute
    public void RunSchedule() {

        updateExpiredCompetition();
        updateStuckCompetition();
        updateCompetitionUnderVote();
        assignMonthlyWinner();
        checkLatePaymentCompetition();
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
                Random random = new Random();
                Submission winner = topSubmissions.get(random.nextInt(topSubmissions.size()));
                competition.setStatus("Completed");
                competition.setParticipantWinner(winner.getParticipant());
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



    // hussam
    public void checkLatePaymentCompetition(){
        LocalDate today = LocalDate.now();

        List<Competition> pendingCompetitions = competitionRepository.findCompetitionByStatus("Waiting payment");

        for (Competition competition : pendingCompetitions) {

            long daysSinceEnd = ChronoUnit.DAYS.between(competition.getCreationAt(), today);
            if (daysSinceEnd == 3) {
                MyUser user = competition.getIndividualCompetition() != null ? competition.getIndividualCompetition().getIndividualOrganizer().getMyUser():
                        competition.getCompanyCompetition().getCompanyOrganizer().getMyUser();
                if (competition.getEmailSentLatePayment()) continue;
                emailSender.sendEmail(
                        user.getEmail(),
                        "Reminder: Pending Payment for Competition",
                        "<html>" +
                                "<body style='background-color: #D4EBF8; font-size: 16px; color: #1F509A; font-family: Arial, sans-serif;'>" +
                                "<div style='background-color: #ffffff; border: 2px solid #1F509A; padding: 20px; border-radius: 5px;'>" +
                                "<p style='font-size: 18px; font-weight: bold; color: #0A3981;'>Dear " + user.getName() + ",</p>" +
                                "<p>We hope this message finds you well. We would like to notify you that the payment process for the following competition is still pending from the organizers:</p>" +
                                "<ul style='list-style-type: square; padding-left: 20px; color: #1F509A;'>" +
                                "<li><strong>Competition Name:</strong> " + competition.getTitle() + "</li>" +
                                "</ul>" +
                                "<p style='color: #0A3981;'>As a result, your registration remains on hold. The organizers have been informed and are expected to complete the payment within the next <strong style='color: #E38E49;'>7 days</strong>. If the payment is not resolved within this timeframe, the competition may be canceled.</p>" +
                                "<p>If you have any concerns or require additional information, please feel free to contact us at <a href='mailto:support@ideaSphere.com' style='color: #E38E49;'>support@ideaSphere.com</a>.</p>" +
                                "<p style='color: #1F509A;'>We sincerely apologize for any inconvenience caused and appreciate your patience as we work to resolve this matter.</p>" +
                                "<p style='margin-top: 20px; color: #0A3981;'>Best regards,</p>" +
                                "<p style='font-weight: bold; color: #E38E49;'>The Idea Sphere Team</p>" +
                                "</div>" +
                                "</body>" +
                                "</html>"
                );

                competition.setEmailSentLatePayment(true);
                competitionRepository.save(competition);

                // send email
                continue;
            }

            if (daysSinceEnd > 7) {
                competition.setStatus("canceled");
                competitionRepository.save(competition);
            }
        }
        System.out.println("checkLatePaymentCompetition - done");
    }
    public void assignMonthlyWinner() {
        LocalDate today = LocalDate.now();

        List<MonthlyDraw> monthlyDraws = monthlyDrawRepository.findMonthlyDrawByEndDateBeforeAndStatus(today,"Ongoing");


        for (MonthlyDraw monthlyDraw : monthlyDraws){

            // Completed|Cancel
            List<MonthlyDrawParticipant> participants = monthlyDrawParticipantRepository.findByMonthlyDrawId(monthlyDraw.getId());
            if (participants.isEmpty()){
                monthlyDraw.setStatus("Cancel");
                monthlyDrawRepository.save(monthlyDraw);
                continue;
            }
            Random random = new Random();
            int randomIndex = random.nextInt(participants.size());
            MonthlyDrawParticipant randomParticipant = participants.get(randomIndex);

            monthlyDraw.setMonthlyDrawParticipantWinner(randomParticipant.getParticipant());
            monthlyDraw.setStatus("Completed");
            monthlyDrawRepository.save(monthlyDraw);
        }
    }
}
