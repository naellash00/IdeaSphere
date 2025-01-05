package com.example.ideasphere.Service;


import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.DTOsIN.IndividualCompetitionDTOsIN;
import com.example.ideasphere.DTOsIN.IndividualOrganizerDTOsIN;
import com.example.ideasphere.DTOsOut.IndividualCompetitionDTOOut;
import com.example.ideasphere.Model.*;
import com.example.ideasphere.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class IndividualCompetitionService {

    @Autowired
    private final IndividualCompetitionRepository individualCompetitionRepository;
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final CompetitionRepository competitionRepository;
    @Autowired
    private final AuthRepository authRepository;
    private final SubmissionRepository submissionRepository;
    private final WinnerPaymentService winnerPaymentService;
    private final WinnerPaymentRepository winnerPaymentRepository;





    public List<IndividualCompetitionDTOOut> getAllIndividualCompetitions() {
        List<IndividualCompetition> individualCompetitions = individualCompetitionRepository.findAll();

        if (individualCompetitions.isEmpty()) {
            return Collections.emptyList();
        }

        return individualCompetitions.stream().map(individualCompetition -> {
            Competition competition = individualCompetition.getCompetition();
            IndividualCompetitionDTOOut dto = new IndividualCompetitionDTOOut();


            dto.setCompetitionId(competition.getId());
            dto.setTitle(competition.getTitle());
            dto.setDescription(competition.getDescription());
            dto.setVotingMethod(competition.getVotingMethod());
            dto.setCompetitionImage(competition.getCompetitionImage());
            dto.setVoteEndDate(competition.getVoteEndDate());
            dto.setEndDate(competition.getEndDate());
            dto.setMaxParticipants(competition.getMaxParticipants());
            dto.setStatus(competition.getStatus());
            dto.setCategories(
                    competition.getCategories() != null
                            ? competition.getCategories().stream().map(Category::getCategoryName).collect(Collectors.toSet())
                            : Collections.emptySet()
            );
            dto.setMonetaryReward(individualCompetition.getMonetaryReward());
            dto.setIndividualName(individualCompetition.getIndividualOrganizer().getIndividualName());

            return dto;
        }).collect(Collectors.toList());
    }

    public List<IndividualCompetitionDTOOut> getAllIndividualCompetitionsByStatus(Integer userId, String status) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null){
            throw new ApiException("user not found");
        }

        List<IndividualCompetition> individualCompetitions = individualCompetitionRepository.findAll();

        if (individualCompetitions.isEmpty()) {
            return Collections.emptyList();
        }

        return individualCompetitions.stream()
                .filter(individualCompetition ->
                        status.equalsIgnoreCase(individualCompetition.getCompetition().getStatus()))
                .map(individualCompetition -> {
                    Competition competition = individualCompetition.getCompetition();
                    IndividualCompetitionDTOOut dto = new IndividualCompetitionDTOOut();

                    dto.setCompetitionId(competition.getId());
                    dto.setTitle(competition.getTitle());
                    dto.setDescription(competition.getDescription());
                    dto.setVotingMethod(competition.getVotingMethod());
                    dto.setCompetitionImage(competition.getCompetitionImage());
                    dto.setVoteEndDate(competition.getVoteEndDate());
                    dto.setEndDate(competition.getEndDate());
                    dto.setMaxParticipants(competition.getMaxParticipants());
                    dto.setStatus(competition.getStatus());
                    dto.setCategories(
                            competition.getCategories() != null
                                    ? competition.getCategories().stream()
                                    .map(Category::getCategoryName)
                                    .collect(Collectors.toSet())
                                    : Collections.emptySet()
                    );
                    dto.setMonetaryReward(individualCompetition.getMonetaryReward());
                    dto.setIndividualName(individualCompetition.getIndividualOrganizer().getIndividualName());

                    return dto;
                })
                .collect(Collectors.toList());
    }


  /*  public List<IndividualCompetitionDTOOut> getAllIndividualCompetition(){
        List<IndividualCompetition> individualCompetitions = individualCompetitionRepository.findAll();

        return individualCompetitions.stream()
                .map(this::ConvertDTO)
                .collect(Collectors.toList());
    }*/

    public List<IndividualCompetitionDTOOut> getMyCompetition(Integer user_id){
        List<IndividualCompetition> individualCompetitions = individualCompetitionRepository.findIndividualCompetitionByCompetition_Id(user_id);

        if (individualCompetitions.isEmpty()) {
            return Collections.emptyList();
        }

        return individualCompetitions.stream().map(individualCompetition -> {
            Competition competition = individualCompetition.getCompetition();
            IndividualCompetitionDTOOut dto = new IndividualCompetitionDTOOut();


            dto.setCompetitionId(competition.getId());
            dto.setTitle(competition.getTitle());
            dto.setDescription(competition.getDescription());
            dto.setVotingMethod(competition.getVotingMethod());
            dto.setCompetitionImage(competition.getCompetitionImage());
            dto.setVoteEndDate(competition.getVoteEndDate());
            dto.setEndDate(competition.getEndDate());
            dto.setMaxParticipants(competition.getMaxParticipants());
            dto.setStatus(competition.getStatus());
            dto.setCategories(
                    competition.getCategories() != null
                            ? competition.getCategories().stream().map(Category::getCategoryName).collect(Collectors.toSet())
                            : Collections.emptySet()
            );
            dto.setMonetaryReward(individualCompetition.getMonetaryReward());
            dto.setIndividualName(individualCompetition.getIndividualOrganizer().getIndividualName());

            return dto;
        }).collect(Collectors.toList());
    }


    public void addIndividualCompetition(Integer userId, IndividualCompetitionDTOsIN individualCompetitionDTOsIN){

        MyUser user = authRepository.findMyUserById(userId);
        if (user == null){
            throw new ApiException("user not found");
        }

        Competition competition = new Competition();
        competition.setTitle(individualCompetitionDTOsIN.getTitle());
        competition.setDescription(individualCompetitionDTOsIN.getDescription());
        competition.setVotingMethod(individualCompetitionDTOsIN.getVotingMethod());
        competition.setCompetitionImage(individualCompetitionDTOsIN.getCompetitionImage());
        competition.setVoteEndDate(individualCompetitionDTOsIN.getVoteEndDate());
        competition.setEndDate(individualCompetitionDTOsIN.getEndDate());
        competition.setMaxParticipants(individualCompetitionDTOsIN.getMaxParticipants());
        competition.setStatus("Waiting payment");

        if (individualCompetitionDTOsIN.getCategories().isEmpty()){
            throw new ApiException("Competition must be have at lest one or more category ");
        }

        for (Category c : individualCompetitionDTOsIN.getCategories()){
            Category category = categoryRepository.findCategoryById(c.getId());
            if (category == null){
                throw new ApiException("category not found whit id "+ c.getId());
            }
        }
        competition.setCategories(individualCompetitionDTOsIN.getCategories());

        competitionRepository.save(competition);

        IndividualCompetition individualCompetition = new IndividualCompetition();
        individualCompetition.setMonetaryReward(individualCompetitionDTOsIN.getMonetaryReward());
        individualCompetition.setCompetition(competition);

        individualCompetitionRepository.save(individualCompetition);
    }

    public void updateIndividualCompetition(Integer userId, IndividualCompetitionDTOsIN individualCompetitionDTOsIN) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        Competition existingCompetition = competitionRepository.findCompetitionById(individualCompetitionDTOsIN.getIndividualCompetitionId());
        if (existingCompetition == null) {
            throw new ApiException("Competition not found");
        }

        existingCompetition.setTitle(individualCompetitionDTOsIN.getTitle());
        existingCompetition.setDescription(individualCompetitionDTOsIN.getDescription());
        existingCompetition.setVotingMethod(individualCompetitionDTOsIN.getVotingMethod());
        existingCompetition.setCompetitionImage(individualCompetitionDTOsIN.getCompetitionImage());
        existingCompetition.setVoteEndDate(individualCompetitionDTOsIN.getVoteEndDate());
        existingCompetition.setEndDate(individualCompetitionDTOsIN.getEndDate());
        existingCompetition.setMaxParticipants(individualCompetitionDTOsIN.getMaxParticipants());

        if (individualCompetitionDTOsIN.getCategories().isEmpty()) {
            throw new ApiException("Competition must have at least one category.");
        }

        for (Category c : individualCompetitionDTOsIN.getCategories()) {
            Category category = categoryRepository.findCategoryById(c.getId());
            if (category == null) {
                throw new ApiException("Category not found with id " + c.getId());
            }
        }

        existingCompetition.setCategories(individualCompetitionDTOsIN.getCategories());
        competitionRepository.save(existingCompetition);

        IndividualCompetition existingIndividualCompetition = individualCompetitionRepository.findIndividualCompetitionById(existingCompetition.getId());
        if (existingIndividualCompetition == null) {
            throw new ApiException("Individual Competition not found.");
        }
        existingIndividualCompetition.setMonetaryReward(individualCompetitionDTOsIN.getMonetaryReward());
        existingIndividualCompetition.setCompetition(existingCompetition);
        individualCompetitionRepository.save(existingIndividualCompetition);
    }


    public List<IndividualCompetition> getCompetitionsByStatus(Integer userId, String status) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        return individualCompetitionRepository.findByStatusAndUserId(status, user.getId());
    }

    public IndividualCompetitionDTOOut ConvertDTO(IndividualCompetition individualCompetition) {
        Competition competition = individualCompetition.getCompetition();

        return new IndividualCompetitionDTOOut(
                competition.getId(),
                competition.getTitle(),
                competition.getDescription(),
                competition.getVotingMethod(),
                competition.getCompetitionImage(),
                competition.getVoteEndDate(),
                competition.getEndDate(),
                competition.getMaxParticipants(),
                competition.getStatus(),
                competition.getCategories().stream().map(Category::getCategoryName).collect(Collectors.toSet()),
                individualCompetition.getMonetaryReward(),
                individualCompetition.getIndividualOrganizer().getIndividualName()
        );
    }

    // Naelah
    public void selectWinner(Integer competition_id, Integer submission_id) {
        Competition competition = competitionRepository.findCompetitionById(competition_id);
        IndividualCompetition individualCompetition = individualCompetitionRepository.findIndividualCompetitionById(competition.getId());
        Submission submission = submissionRepository.findSubmissionById(submission_id);
        WinnerPayment winnerPayment = new WinnerPayment();
        if (competition == null || individualCompetition == null) {
            throw new ApiException("competition not found");
        }
        if (submission == null) {
            throw new ApiException("submission not found");
        }
        // check the same submission in the competition
        if (!submission.getCompetition().getId().equals(competition.getId())) {
            throw new ApiException("Incorrect submission for competition");
        }
        competition.setParticipantWinner(submission.getParticipant());
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
        winnerPaymentRepository.save(winnerPayment);
    }

    public void acceptFeedbackRequest(Integer company_organizer_id, Integer submission_id, String feedback) {
        Submission submission = submissionRepository.findSubmissionById(submission_id);
        if (submission == null) {
            throw new ApiException("submission not found");
        }
        if (!submission.getFeedbackRequestStatus().equalsIgnoreCase("Pending")) {
            throw new ApiException("submission dose not have feedback request");
        }
        if (!submission.getCompetition().getCompanyCompetition().getCompanyOrganizer().getId().equals(company_organizer_id)) {
            throw new ApiException("cannot respond to this request");
        }
        submission.setFeedbackRequestStatus("Accepted");
        submission.setOrganizerFeedback(feedback);
        submissionRepository.save(submission);
    }

    public void rejectFeedbackRequest(Integer company_organizer_id, Integer submission_id) {
        Submission submission = submissionRepository.findSubmissionById(submission_id);
        if (submission == null) {
            throw new ApiException("submission not found");
        }
        if (!submission.getFeedbackRequestStatus().equalsIgnoreCase("Pending")) {
            throw new ApiException("submission dose not have feedback request");
        }
        if (!submission.getCompetition().getCompanyCompetition().getCompanyOrganizer().getId().equals(company_organizer_id)) {
            throw new ApiException("cannot respond to this request");
        }
        submission.setFeedbackRequestStatus("Rejected");
        submission.setOrganizerFeedback("Request Is Rejected");
        submissionRepository.save(submission);
    }

    //Naelah
    public List<String> getMyCompetitionReviews(Integer competition_id) {
        Competition competition = competitionRepository.findCompetitionById(competition_id);
        if (competition == null) {
            throw new ApiException("competition not found");
        }
        List<String> reviews = new ArrayList<>();
        for (String review : competition.getReviews()) {
            reviews.add(review);
        }
        return reviews;
    }

}
