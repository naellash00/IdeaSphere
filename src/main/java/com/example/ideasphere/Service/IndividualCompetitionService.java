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
import java.util.*;
import java.util.stream.Collectors;
import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.DTOsIN.*;
import com.example.ideasphere.DTOsOut.IndividualCompetitionDTOOut;
import com.example.ideasphere.Model.*;
import com.example.ideasphere.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    private final CompetitionPaymentRepository competitionPaymentRepository;

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
        MyUser user = authRepository.findMyUserById(user_id);
        if (user == null){
            throw new ApiException("user not found");
        }
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
            dto.setIndividualOrganizerName(user.getName());

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
        if (individualCompetitionDTOsIN.getVotingMethod().equalsIgnoreCase("By Organizer")){
            individualCompetitionDTOsIN.setVoteEndDate(null);
        }
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
        individualCompetition.setIndividualOrganizer(user.getIndividualOrganizer());
        individualCompetition.setCompetition(competition);

        individualCompetitionRepository.save(individualCompetition);
    }

    public void updateIndividualCompetition(Integer user_id , IndividualCompetitionUpdateDTOIn individualCompetitionUpdateDTOIn){
        MyUser myUser = authRepository.findMyUserById(user_id);

        if (myUser == null) throw new ApiException("Error: user not found");

        IndividualCompetition individualCompetition = individualCompetitionRepository.findIndividualCompetitionById(individualCompetitionUpdateDTOIn.getId());

        if (individualCompetition == null) throw new ApiException("Error: individual Competition not found");

        if (!myUser.getCompanyOrganizer().getId().equals(individualCompetition.getIndividualOrganizer().getId())) throw new ApiException("Error: this Competition not belong to you");

        String status =  individualCompetition.getCompetition().getStatus();
        // check status
        if (!status.equalsIgnoreCase("Ongoing")) throw new ApiException("Error: the Competition status is ("+status+") you can't extend the Competition");

        // check category
        checkCategoryExist(individualCompetitionUpdateDTOIn.getCategories());

        Competition competition = individualCompetition.getCompetition();


        competition.setTitle(individualCompetitionUpdateDTOIn.getTitle());
        competition.setDescription(individualCompetitionUpdateDTOIn.getDescription());
        competition.setCompetitionImage(individualCompetitionUpdateDTOIn.getCompetitionImage());
        competition.setCategories(individualCompetitionUpdateDTOIn.getCategories());

        competitionRepository.save(competition);
    }

    public void checkCategoryExist(Set<Category> categories){
        for(Category category : categories){
            Category findCategory = categoryRepository.findCategoryById(category.getId());
            if (findCategory == null )throw new ApiException("Error: category not found ");
        }
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
                competition.getIndividualCompetition().getIndividualOrganizer().getMyUser().getName()

        );
    }


    public void addPayment(Integer user_id, IndividualCompetitionPaymentDTOIn CompetitionPaymentDTOIn) {
        MyUser myUser = authRepository.findMyUserById(user_id);
        if (myUser == null) throw new ApiException("Error :user not found");

        IndividualCompetition individualCompetition = individualCompetitionRepository.findIndividualCompetitionById(CompetitionPaymentDTOIn.getIndividualCompetitionId());

        if (individualCompetition == null) throw new ApiException("Error: individualCompetition not found");

        if ( !individualCompetition.getIndividualOrganizer().getId().equals(myUser.getId())) throw new ApiException("Error: this competition not belong to you, you can't make payment.");

        if (!individualCompetition.getCompetition().getStatus().equalsIgnoreCase("Waiting payment")) throw new ApiException("Error: the Competition status is ("+individualCompetition.getCompetition().getStatus()+"), you can't make payment");

        CompetitionPayment duplicatedPayment = competitionPaymentRepository.findCompetitionPaymentByCompetitionId(individualCompetition.getCompetition().getId());

        if (duplicatedPayment != null ) throw new ApiException("Error: payment already created");

        CompetitionPayment competitionPayment = new CompetitionPayment();

        competitionPayment.setPaymentMethod(CompetitionPaymentDTOIn.getPaymentMethod());
        competitionPayment.setPaymentStatus("Completed");
        competitionPayment.setAmount(individualCompetition.getMonetaryReward());
        competitionPayment.setCompetition(individualCompetition.getCompetition());

        competitionPaymentRepository.save(competitionPayment);

        Competition competition = individualCompetition.getCompetition();
        competition.setStatus("Ongoing");
        competitionRepository.save(competition);
    }

    public void extendCompetition(Integer user_id , IndividualCompetitionExtendDTOIn individualCompetitionExtendDTOIn  ){
        MyUser myUser = authRepository.findMyUserById(user_id);

        if (myUser == null) throw new ApiException("Error: user not found");

        IndividualCompetition individualCompetition = individualCompetitionRepository.findIndividualCompetitionById(individualCompetitionExtendDTOIn.getId());

        if (individualCompetition == null) throw new ApiException("Error: IndividualCompetition not found");

        if (!myUser.getIndividualOrganizer().getId().equals(individualCompetition.getIndividualOrganizer().getId())) throw new ApiException("Error: this Competition not belong to you");


        String status =  individualCompetition.getCompetition().getStatus();
        // check status
        if (status.equalsIgnoreCase("Completed")
                || status.equalsIgnoreCase("Waiting payment" )
                || status.equalsIgnoreCase("canceled" )) throw new ApiException("Error: the Competition status is ("+status+") you can't extend the Competition") ;

        Competition competition = individualCompetition.getCompetition();

        if (competition.getCountExtend() >=3) throw new ApiException("Error: you have the maximum extending of the competition ");

        if (competition.getVotingMethod().equalsIgnoreCase("By Organizer")){
            individualCompetitionExtendDTOIn.setVoteEndDate(null);
        }

        // check dates
        checkEndDateAndVoteEndDate(individualCompetition.getCompetition().getEndDate(), individualCompetitionExtendDTOIn.getEndDate(), individualCompetitionExtendDTOIn.getVoteEndDate());

        // check the competition have max Participant and make sure organizer increase Participant if he wants extend
        if (competition.getSubmissions().size() >= competition.getMaxParticipants() && individualCompetitionExtendDTOIn.getIncreaseParticipants() == 0) throw new ApiException("Error: competition have max Participant you must increase Participant if you want extend ");


        competition.setEndDate(individualCompetitionExtendDTOIn.getEndDate());
        competition.setVoteEndDate(individualCompetitionExtendDTOIn.getVoteEndDate());
        competition.setMaxParticipants(competition.getMaxParticipants() + individualCompetitionExtendDTOIn.getIncreaseParticipants());
        competition.setStatus("Ongoing");
        competition.setCountExtend(competition.getCountExtend()+1);

        competitionRepository.save(competition);
    }
    public void checkEndDateAndVoteEndDate(LocalDate endDate, LocalDate voteEndDate) {
        LocalDate today = LocalDate.now();

        // Check EndDate conditions
        if (endDate.isBefore(today) || endDate.isEqual(today)) {
            throw new ApiException("Error: EndDate must be in the future and not today.");
        }
        if (endDate.isAfter(today.plusDays(30))) {
            throw new ApiException("Error: EndDate cannot be more than 30 days from today.");
        }

        // If VoteEndDate is provided, check its conditions
        if (voteEndDate != null) {
            if (voteEndDate.isBefore(endDate) || voteEndDate.isEqual(endDate)) {
                throw new ApiException("Error: VoteEndDate must be after EndDate.");
            }
            if (voteEndDate.isAfter(endDate.plusDays(7))) {
                throw new ApiException("Error: VoteEndDate cannot be more than 7 days after EndDate.");
            }
        }
    }
    public void checkEndDateAndVoteEndDate(LocalDate oldEndDate, LocalDate endDate, LocalDate voteEndDate) {

        // Check EndDate conditions
        if (endDate.isBefore(oldEndDate) || endDate.isEqual(oldEndDate)) {
            throw new ApiException("Error: EndDate must be in the future and not equal oldEndDate.");
        }
        if (endDate.isAfter(oldEndDate.plusDays(30))) {
            throw new ApiException("Error: EndDate cannot be more than 30 days from oldEndDate.");
        }

        // If VoteEndDate is provided, check its conditions
        if (voteEndDate != null) {
            if (voteEndDate.isBefore(endDate) || voteEndDate.isEqual(endDate)) {
                throw new ApiException("Error: VoteEndDate must be after EndDate.");
            }
            if (voteEndDate.isAfter(endDate.plusDays(7))) {
                throw new ApiException("Error: VoteEndDate cannot be more than 7 days after EndDate.");
            }
        }
    }
}
