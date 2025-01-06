package com.example.ideasphere.Service;

import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.DTOsOut.SubmissionOutDTO;
import com.example.ideasphere.Model.*;
import com.example.ideasphere.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionService { //Naelah
    private final SubmissionRepository submissionRepository;
    private final ParticipantRepository participantRepository;
    private final CompetitionRepository competitionRepository;
    private final CompanyCompetitionRepository companyCompetitionRepository;
    private final WinnerPaymentService winnerPaymentService;
    private final CompanyOrganizerRepository companyOrganizerRepository;
    private final IndividualOrganizerRepository individualOrganizerRepository;
    private final IndividualCompetitionRepository individualCompetitionRepository;
    private final EmailSenderJava emailSender;
    private final AuthRepository authRepository;

    public List<Submission> getAllSubmissions() {
        return submissionRepository.findAll();
    }

    public void submit(Integer participant_id, Integer competition_id, Submission submission) {
        Participant participant = participantRepository.findParticipantById(participant_id);
        Competition competition = competitionRepository.findCompetitionById(competition_id);
        if (participant == null) {
            throw new ApiException("participant not found");
        }
        if (competition == null) {
            throw new ApiException("competition not found");
        }
        if (!competition.getStatus().equalsIgnoreCase("Ongoing")) {
            throw new ApiException("cannot submit, competition is not ongoing");
        }
//        // check participant didnt submit before
//        if (competition.getSubmissions().contains(submission)) {
//            throw new ApiException("cannot submit, you already submitted for this competition");
//        }
        for (Submission sub : competition.getSubmissions()) {
            if (sub.getParticipant().getId().equals(participant_id)) {
                throw new ApiException("cannot submit, you already submitted for this competition");
            }
        }
        if (Collections.disjoint(competition.getCategories(), participant.getCategories())) {
            throw new ApiException("Participant cannot submit on this competition, no matching categories");
        }
        submission.setParticipant(participant);
        submission.setCompetition(competition);
        submission.setSubmittedAt(LocalDateTime.now());
        submissionRepository.save(submission);
    }


    // helper method to find what competition the participant won
    public Submission findTheCompetitionIWon(Integer competition_id, Integer participant_id) {
        Participant participant = participantRepository.findParticipantById(participant_id);
        Competition competition = competitionRepository.findCompetitionById(competition_id);
        if (competition == null) {
            throw new ApiException("competition not found");
        }
        if (participant == null) {
            throw new ApiException("participant not found");
        }

        for (Submission submission : participant.getSubmissions()) {
            if (submission.getCompetition().getId().equals(competition.getId())) {
                return submission;
            }
        }
        return null;
    }

    // made by participant
    public List<SubmissionOutDTO> getMySubmissions(Integer participant_id) {
        Participant participant = participantRepository.findParticipantById(participant_id);
        List<SubmissionOutDTO> submissionOutDTOS = new ArrayList<>();
        for (Submission s : participant.getSubmissions()) {
            submissionOutDTOS.add(new SubmissionOutDTO(s.getPDFFile(), s.getFileURL(), s.getSecondFileURL(), s.getThirdFileURL(), s.getDescription(), s.getSubmittedAt(), s.getCompetition().getTitle()));
        }
        return submissionOutDTOS;
    }

    // made by participant
    public void requestFeedbackOnSubmission(Integer participant_id, Integer submission_id) {
        Submission submission = submissionRepository.findSubmissionById(submission_id);
        if (submission.getFeedbackRequestStatus() != null) {
            throw new ApiException("Feedback request already made");
        }
        if (!submission.getParticipant().getId().equals(participant_id)) {
            throw new ApiException("Cannot request feedback on this submission");
        }
        // another check
        submission.setFeedbackRequestStatus("Pending");
        submissionRepository.save(submission);
    }

    public void companyAcceptFeedbackRequest(Integer company_organizer_id, Integer submission_id, String feedback) {
        Submission submission = submissionRepository.findSubmissionById(submission_id);
        if (submission == null) {
            throw new ApiException("submission not found");
        }
        if (!submission.getFeedbackRequestStatus().equalsIgnoreCase("Pending")) {
            throw new ApiException("submission dose not have feedback request");
        }
        if (!submission.getCompetition().getCompanyCompetition().getCompanyOrganizer().getId().equals(company_organizer_id)) {
            throw new ApiException("company organizer cannot respond to this request");
        }
        submission.setFeedbackRequestStatus("Accepted");
        submission.setOrganizerFeedback(feedback);
        sendFeedbackEmail(company_organizer_id, "Submission Feedback", feedback);

        submissionRepository.save(submission);
    }

    public void companyRejectFeedbackRequest(Integer company_organizer_id, Integer submission_id) {
        Submission submission = submissionRepository.findSubmissionById(submission_id);
        if (submission == null) {
            throw new ApiException("submission not found");
        }
        if (!submission.getFeedbackRequestStatus().equalsIgnoreCase("Pending")) {
            throw new ApiException("submission dose not have feedback request");
        }
        if (!submission.getCompetition().getCompanyCompetition().getCompanyOrganizer().getId().equals(company_organizer_id)) {
            throw new ApiException("company organizer cannot respond to this request");
        }
        submission.setFeedbackRequestStatus("Rejected");
        submission.setOrganizerFeedback("Request Is Rejected");

        submissionRepository.save(submission);
    }


    public void individualAcceptFeedbackRequest(Integer individual_organizer_id, Integer submission_id, String feedback) {
        Submission submission = submissionRepository.findSubmissionById(submission_id);
        if (submission == null) {
            throw new ApiException("submission not found");
        }
        if (!submission.getFeedbackRequestStatus().equalsIgnoreCase("Pending")) {
            throw new ApiException("submission dose not have feedback request");
        }
        if (!submission.getCompetition().getIndividualCompetition().getIndividualOrganizer().getId().equals(individual_organizer_id)) {
            throw new ApiException("individual organizer cannot respond to this request");
        }
        submission.setFeedbackRequestStatus("Accepted");
        submission.setOrganizerFeedback(feedback);
        sendFeedbackEmail(individual_organizer_id, "Submission Feedback", feedback);

        submissionRepository.save(submission);
    }

    public void individualRejectFeedbackRequest(Integer individual_organizer_id, Integer submission_id) {
        Submission submission = submissionRepository.findSubmissionById(submission_id);
        if (submission == null) {
            throw new ApiException("submission not found");
        }
        if (!submission.getFeedbackRequestStatus().equalsIgnoreCase("Pending")) {
            throw new ApiException("submission dose not have feedback request");
        }
        if (!submission.getCompetition().getIndividualCompetition().getIndividualOrganizer().getId().equals(individual_organizer_id)) {
            throw new ApiException("individual organizer cannot respond to this request");
        }
        submission.setFeedbackRequestStatus("Rejected");
        submission.setOrganizerFeedback("Request Is Rejected");

        submissionRepository.save(submission);
    }

    public void sendFeedbackEmail(Integer organizer_id, String subject, String text) {
        MyUser user = authRepository.findMyUserById(organizer_id);
        if (user == null) {
            throw new ApiException("organizer not found");
        }
        emailSender.sendEmail(
                "naellaohun@gmail.com",
                "Complaint Submission",
                "<html>" +
                        "<body style='background-color: #D4EBF8; font-size: 16px; color: #1F509A; font-family: Arial, sans-serif;'>" +
                        "<div style='background-color: #ffffff; border: 2px solid #1F509A; padding: 20px; border-radius: 5px;'>" +
                        "<p style='font-size: 18px; font-weight: bold; color: #0A3981;'>Complaint Submission</p>" +
                        "<p>Dear Support Team,</p>" +
                        "<p>A new complaint has been submitted with the following details:</p>" +
                        "<ul style='list-style-type: square; padding-left: 20px; color: #1F509A;'>" +
                        "<li><strong>Organizer's Name:</strong> " + user.getName() + "</li>" +
                        "<li><strong>Organizer's Email:</strong> " + user.getEmail() + "</li>" +
                        "<li><strong>Complaint Subject:</strong> " + subject + "</li>" +
                        "<li><strong>Complaint Message:</strong> " + text + "</li>" +
                        "</ul>" +
                        "<p style='color: #0A3981;'>Please review the complaint and address it at your earliest convenience. If you require any further details, feel free to reach out to the complainant.</p>" +
                        "<p style='margin-top: 20px; color: #0A3981;'>Best regards,</p>" +
                        "<p style='font-weight: bold; color: #E38E49;'>The Idea Sphere Team</p>" +
                        "</div>" +
                        "</body>" +
                        "</html>"
        );
    }

    // Naelah
    public void companySelectWinner(Integer company_organizer_id, Integer competition_id, Integer submission_id) {
        CompanyOrganizer companyOrganizer = companyOrganizerRepository.findCompanyOrganizerById(company_organizer_id);
        Competition competition = competitionRepository.findCompetitionById(competition_id);
        CompanyCompetition companyCompetition = companyCompetitionRepository.findCompanyCompetitionById(competition.getId());
        Submission submission = submissionRepository.findSubmissionById(submission_id);
        if (companyOrganizer == null) {
            throw new ApiException("company organizer not found");
        }
        if (!companyCompetition.getCompanyOrganizer().getId().equals(companyOrganizer.getId())) {
            throw new ApiException("company organizer cannot select winner for this competition");
        }
        if (competition == null || companyCompetition == null) {
            throw new ApiException("competition not found");
        }
        if (submission == null) {
            throw new ApiException("submission not found");
        }
        // check submission is for this competition
        if (!submission.getCompetition().getId().equals(competition.getId())) {
            throw new ApiException("Incorrect submission for competition");
        }
        if (!competition.getStatus().equalsIgnoreCase("Winner Selection in Progress")) {
            throw new ApiException("cannot select winner yet, competition is not in winner selection progress");
        }
//        if (!competition.getParticipantWinner().equals(null)) {
//            throw new ApiException("winner is selected for this competition");
//        }
        competition.setParticipantWinner(submission.getParticipant());
        competition.setStatus("Completed");

        // winner payment
        winnerPaymentService.companyCompleteWinnerPaymentDetails(competition.getId(), submission.getId());

        competitionRepository.save(competition);
        companyCompetitionRepository.save(companyCompetition);
        String prize = (competition.getCompanyCompetition().getMonetaryReward() != 0 ? competition.getCompanyCompetition().getMonetaryReward().toString() : "" )+"/"+ (!competition.getCompanyCompetition().getRewardType().equalsIgnoreCase("Financial") ? "Interview":"");

        emailSender.sendEmail(
                submission.getParticipant().getUser().getEmail(),
                "Congratulations: You Have Won the Competition!",
                "<html>" +
                        "<body style='background-color: #D4EBF8; font-size: 16px; color: #1F509A; font-family: Arial, sans-serif;'>" +
                        "<div style='background-color: #ffffff; border: 2px solid #1F509A; padding: 20px; border-radius: 5px;'>" +
                        "<p style='font-size: 18px; font-weight: bold; color: #0A3981;'>Dear " + submission.getParticipant().getUser().getName() + ",</p>" +
                        "<p>We are thrilled to inform you that you have emerged as the <strong style='color: #E38E49;'>winner</strong> of the following competition:</p>" +
                        "<ul style='list-style-type: square; padding-left: 20px; color: #1F509A;'>" +
                        "<li><strong>Competition Name:</strong> " + competition.getTitle() + "</li>" +
                        "<li><strong>Organizer:</strong> " + competition.getCompanyCompetition().getCompanyOrganizer().getCompanyName() + "</li>" +
                        "<li><strong>Prize:</strong> " + prize + "</li>" +
                        "</ul>" +
                        "<p style='color: #0A3981;'>This achievement highlights your exceptional skills and creativity. We hope this victory inspires you to continue striving for excellence in all your endeavors.</p>" +
                        "<p>If you have any questions or require additional information, please feel free to contact us at <a href='mailto:support@ideaSphere.com' style='color: #E38E49;'>support@ideaSphere.com</a>.</p>" +
                        "<p style='color: #1F509A;'>Once again, congratulations on this remarkable achievement. We look forward to seeing your continued success in the future.</p>" +
                        "<p style='margin-top: 20px; color: #0A3981;'>Best regards,</p>" +
                        "<p style='font-weight: bold; color: #E38E49;'>The Idea Sphere Team</p>" +
                        "</div>" +
                        "</body>" +
                        "</html>"
        );

    }

    // Not tested
    public void individualSelectWinner(Integer individual_organizer_id, Integer competition_id, Integer submission_id) {
        IndividualOrganizer individualOrganizer = individualOrganizerRepository.findIndividualOrganizerById(individual_organizer_id);
        Competition competition = competitionRepository.findCompetitionById(competition_id);
        IndividualCompetition individualCompetition = individualCompetitionRepository.findIndividualCompetitionById(competition.getId());
        Submission submission = submissionRepository.findSubmissionById(submission_id);
        if (individualOrganizer == null) {
            throw new ApiException("individual organizer not found");
        }
        if (!individualCompetition.getIndividualOrganizer().getId().equals(individualOrganizer.getId())) {
            throw new ApiException("individual organizer cannot select winner for this competition");
        }
        if (competition == null || individualCompetition == null) {
            throw new ApiException("competition not found");
        }
        if (submission == null) {
            throw new ApiException("submission not found");
        }
        // check submission is for this competition
        if (!submission.getCompetition().getId().equals(competition.getId())) {
            throw new ApiException("Incorrect submission for competition");
        }
        if (!competition.getStatus().equalsIgnoreCase("Winner Selection in Progress")) {
            throw new ApiException("cannot select winner yet, competition is not in winner selection progress");
        }
        if (!competition.getParticipantWinner().equals(null)) {
            throw new ApiException("winner is selected for this competition");
        }
        competition.setParticipantWinner(submission.getParticipant());
        competition.setStatus("Completed");
        // winner payment
        winnerPaymentService.individualCompleteWinnerPaymentDetails(competition.getId(), submission.getId());

        competitionRepository.save(competition);
        individualCompetitionRepository.save(individualCompetition);

        emailSender.sendEmail(
                submission.getParticipant().getUser().getEmail(),
                "Congratulations: You Have Won the Competition!",
                "<html>" +
                        "<body style='background-color: #D4EBF8; font-size: 16px; color: #1F509A; font-family: Arial, sans-serif;'>" +
                        "<div style='background-color: #ffffff; border: 2px solid #1F509A; padding: 20px; border-radius: 5px;'>" +
                        "<p style='font-size: 18px; font-weight: bold; color: #0A3981;'>Dear " + submission.getParticipant().getUser().getName() + ",</p>" +
                        "<p>We are thrilled to inform you that you have emerged as the <strong style='color: #E38E49;'>winner</strong> of the following competition:</p>" +
                        "<ul style='list-style-type: square; padding-left: 20px; color: #1F509A;'>" +
                        "<li><strong>Competition Name:</strong> " + competition.getTitle() + "</li>" +
                        "<li><strong>Organizer:</strong> " + competition.getIndividualCompetition().getIndividualOrganizer().getMyUser().getName() + "</li>" +
                        "<li><strong>Prize:</strong> " +  competition.getIndividualCompetition().getMonetaryReward() + "</li>" +
                        "</ul>" +
                        "<p style='color: #0A3981;'>This achievement highlights your exceptional skills and creativity. We hope this victory inspires you to continue striving for excellence in all your endeavors.</p>" +
                        "<p>If you have any questions or require additional information, please feel free to contact us at <a href='mailto:support@ideaSphere.com' style='color: #E38E49;'>support@ideaSphere.com</a>.</p>" +
                        "<p style='color: #1F509A;'>Once again, congratulations on this remarkable achievement. We look forward to seeing your continued success in the future.</p>" +
                        "<p style='margin-top: 20px; color: #0A3981;'>Best regards,</p>" +
                        "<p style='font-weight: bold; color: #E38E49;'>The Idea Sphere Team</p>" +
                        "</div>" +
                        "</body>" +
                        "</html>"
        );
    }

    public List<SubmissionOutDTO> companyViewMyCompetitionSubmissions(Integer company_organizer_id, Integer competition_id) {
        CompanyOrganizer companyOrganizer = companyOrganizerRepository.findCompanyOrganizerById(company_organizer_id);
        Competition competition = competitionRepository.findCompetitionById(competition_id);
        if(companyOrganizer == null){
            throw new ApiException("company organizer not found");
        }
        if(competition == null){
            throw new ApiException("competition not found");
        }
        if(!competition.getCompanyCompetition().getCompanyOrganizer().getId().equals(companyOrganizer.getId())){
            throw new ApiException("company organizer cannot view this competition submissions");
        }
        List<SubmissionOutDTO> myCompetitionSubmissions = new ArrayList<>();
        for (Submission submission : competition.getSubmissions()) {
            SubmissionOutDTO submissionOutDTO = new SubmissionOutDTO();

            submissionOutDTO.setPdfFile(submission.getPDFFile());
            submissionOutDTO.setFileURL(submission.getFileURL());
            submissionOutDTO.setSecondFileURL(submission.getSecondFileURL());
            submissionOutDTO.setThirdFileURL(submission.getThirdFileURL());
            submissionOutDTO.setDescription(submission.getDescription());
            submissionOutDTO.setSubmittedAt(submission.getSubmittedAt());
            submissionOutDTO.setCompetitionTitle(submission.getCompetition().getTitle());

            myCompetitionSubmissions.add(submissionOutDTO);
        }
        return myCompetitionSubmissions;
    }

    // Naelah
    public List<SubmissionOutDTO> individualViewMyCompetitionSubmissions(Integer individual_organizer_id, Integer competition_id) {
        IndividualOrganizer individualOrganizer = individualOrganizerRepository.findIndividualOrganizerById(individual_organizer_id);
        Competition competition = competitionRepository.findCompetitionById(competition_id);
        if(individualOrganizer == null){
            throw new ApiException("individual organizer not found");
        }
        if(competition == null){
            throw new ApiException("competition not found");
        }
        if(!competition.getIndividualCompetition().getIndividualOrganizer().getId().equals(individualOrganizer.getId())){
            throw new ApiException("individual organizer cannot view this competition submissions");
        }
        List<SubmissionOutDTO> myCompetitionSubmissions = new ArrayList<>();
        for (Submission submission : competition.getSubmissions()) {
            SubmissionOutDTO submissionOutDTO = new SubmissionOutDTO();

            submissionOutDTO.setPdfFile(submission.getPDFFile());
            submissionOutDTO.setFileURL(submission.getFileURL());
            submissionOutDTO.setSecondFileURL(submission.getSecondFileURL());
            submissionOutDTO.setThirdFileURL(submission.getThirdFileURL());
            submissionOutDTO.setDescription(submission.getDescription());
            submissionOutDTO.setSubmittedAt(submission.getSubmittedAt());
            submissionOutDTO.setCompetitionTitle(submission.getCompetition().getTitle());

            myCompetitionSubmissions.add(submissionOutDTO);
        }
        return myCompetitionSubmissions;
    }

}
