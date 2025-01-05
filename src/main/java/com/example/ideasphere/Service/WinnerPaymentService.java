package com.example.ideasphere.Service;
import com.example.ideasphere.Model.CompanyCompetition;
import com.example.ideasphere.Model.Competition;
import com.example.ideasphere.Model.Submission;
import com.example.ideasphere.Model.WinnerPayment;
import com.example.ideasphere.Repository.CompanyCompetitionRepository;
import com.example.ideasphere.Repository.CompetitionRepository;
import com.example.ideasphere.Repository.SubmissionRepository;
import com.example.ideasphere.Repository.WinnerPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class WinnerPaymentService { // Naelah
    private final WinnerPaymentRepository winnerPaymentRepository;
    private final CompetitionRepository competitionRepository;
    private final SubmissionRepository submissionRepository;
    private final CompanyCompetitionRepository companyCompetitionRepository;


    // for company competition
    public void completeWinnerPaymentDetails(Integer competition_id, Integer submission_id){
        Competition competition = competitionRepository.findCompetitionById(competition_id);
        Submission submission = submissionRepository.findSubmissionById(submission_id);
        CompanyCompetition companyCompetition = companyCompetitionRepository.findCompanyCompetitionById(competition.getId());

        WinnerPayment winnerPayment = new WinnerPayment();
        // winner payment
        winnerPayment.setParticipantWinner(submission.getParticipant());
        winnerPayment.setCompetition(competition);
        winnerPayment.setAmount(companyCompetition.getMonetaryReward());
        winnerPayment.setTransferDate(LocalDateTime.now());
        winnerPayment.setTransferStatus("Completed");
        winnerPayment.setTransferMethod("Bank Transfer");
        winnerPayment.setCreatedAt(LocalDateTime.now());
        winnerPayment.setUpdatedAt(LocalDateTime.now());

        competitionRepository.save(competition);
        companyCompetitionRepository.save(companyCompetition);
        winnerPaymentRepository.save(winnerPayment);
    }
}
