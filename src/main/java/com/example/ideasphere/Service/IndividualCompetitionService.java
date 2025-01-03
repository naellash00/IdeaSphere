package com.example.ideasphere.Service;


import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.DTOsIN.IndividualCompetitionDTOsIN;
import com.example.ideasphere.DTOsIN.IndividualOrganizerDTOsIN;
import com.example.ideasphere.Model.Category;
import com.example.ideasphere.Model.Competition;
import com.example.ideasphere.Model.IndividualCompetition;
import com.example.ideasphere.Model.MyUser;
import com.example.ideasphere.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


    public  List<IndividualCompetition> findAllIndividualCompetitions(Integer userId){
        MyUser user = authRepository.findMyUserById(userId);
        if (userId == null){
            throw new ApiException("user not found");
        }
        return  individualCompetitionRepository.findAll();
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




}
