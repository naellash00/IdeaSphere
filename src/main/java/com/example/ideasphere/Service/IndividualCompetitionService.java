package com.example.ideasphere.Service;


import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.Model.IndividualCompetition;
import com.example.ideasphere.Model.MyUser;
import com.example.ideasphere.Repository.AuthRepository;
import com.example.ideasphere.Repository.IndividualCompetitionRepository;
import com.example.ideasphere.Repository.IndividualOrganizerRepository;
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
    private final AuthRepository authRepository;


    public  List<IndividualCompetition> findAllIndividualCompetitions(Integer userId){
            MyUser user = authRepository.findMyUserById( userId);
            if (userId == null){
                throw new ApiException("user not found");
            }
          return  individualCompetitionRepository.findAll();
      }

        public void addIndividualCompetition(Integer userId,IndividualCompetition individualCompetition){

            MyUser user = authRepository.findMyUserById(userId);
            individualCompetitionRepository.save(individualCompetition);
     }

    public void updateIndividualCompetition(Integer userId, IndividualCompetition individualCompetition) {

         MyUser user = authRepository.findMyUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        IndividualCompetition existingCompetition = individualCompetitionRepository.findIndividualCompetitionById(individualCompetition.getId());
        if (existingCompetition == null) {
            throw new ApiException("Competition not found");
        }

        if (!existingCompetition.getIndividualOrganizer().getMyUser().getId().equals(userId)) {
            throw new ApiException("You are not authorized to update this competition.");
        }

        existingCompetition.setMonetaryReward(individualCompetition.getMonetaryReward());

        individualCompetitionRepository.save(existingCompetition);
    }

    public void deleteIndividualCompetition(Integer userId, Integer individualCompetitionId) {

        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }


        IndividualCompetition competition = individualCompetitionRepository.findIndividualCompetitionById(individualCompetitionId);
        if (competition == null || !competition.getIndividualOrganizer().getMyUser().getId().equals(userId)) {
            throw new ApiException("You are not authorized to delete this competition.");
        }

        individualCompetitionRepository.deleteById(individualCompetitionId);
    }


}
