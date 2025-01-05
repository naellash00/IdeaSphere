package com.example.ideasphere.Service;
import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.Model.*;
import com.example.ideasphere.Repository.*;
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
    private final IndividualCompetitionRepository individualCompetitionRepository;

    // for company competition
    public void companyCompleteWinnerPaymentDetails(Integer competition_id, Integer submission_id) {
        Competition competition = competitionRepository.findCompetitionById(competition_id);
        Submission submission = submissionRepository.findSubmissionById(submission_id);
        CompanyCompetition companyCompetition = companyCompetitionRepository.findCompanyCompetitionById(competition.getId());
        ;
        WinnerPayment winnerPayment = new WinnerPayment();
        if (competition == null) {
            throw new ApiException("Competition not found");
        }
        if (submission == null) {
            throw new ApiException("Submission not found");
        }
        if (companyCompetition == null) {
            throw new ApiException("Company competition not found");
        }
        if (companyCompetition.getMonetaryReward() == null) {
            throw new ApiException("Monetary reward is not set for this competition");
        }

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

    public void individualCompleteWinnerPaymentDetails(Integer competition_id, Integer submission_id) {
        Competition competition = competitionRepository.findCompetitionById(competition_id);
        Submission submission = submissionRepository.findSubmissionById(submission_id);
        IndividualCompetition individualCompetition = individualCompetitionRepository.findIndividualCompetitionById(competition.getId());

        WinnerPayment winnerPayment = new WinnerPayment();
        if (competition == null) {
            throw new ApiException("Competition not found");
        }
        if (submission == null) {
            throw new ApiException("Submission not found");
        }
        if (individualCompetition == null) {
            throw new ApiException("individual competition not found");
        }
        if (individualCompetition.getMonetaryReward() == null) {
            throw new ApiException("Monetary reward is not set for this competition");
        }
        // winner payment
        winnerPayment.setParticipantWinner(submission.getParticipant());
        winnerPayment.setCompetition(competition);
        winnerPayment.setAmount(individualCompetition.getMonetaryReward());
        winnerPayment.setTransferDate(LocalDateTime.now());
        winnerPayment.setTransferStatus("Completed");
        winnerPayment.setTransferMethod("Bank Transfer");
        winnerPayment.setCreatedAt(LocalDateTime.now());
        winnerPayment.setUpdatedAt(LocalDateTime.now());

        competitionRepository.save(competition);
        individualCompetitionRepository.save(individualCompetition);
        winnerPaymentRepository.save(winnerPayment);
    }
}
