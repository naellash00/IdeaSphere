package com.example.ideasphere.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.DTOsOut.CompetitionOutDTO;
import com.example.ideasphere.Model.*;
import com.example.ideasphere.Repository.CompanyOrganizerRepository;
import com.example.ideasphere.Repository.CompetitionRepository;
import com.example.ideasphere.Repository.IndividualOrganizerRepository;
import com.example.ideasphere.Repository.ParticipantRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompetitionService {
    private final CompetitionRepository competitionRepository;
    private final ParticipantRepository participantRepository;
    private final CompanyOrganizerRepository companyOrganizerRepository;
    private final IndividualOrganizerRepository individualOrganizerRepository;

    public List<Competition> getAllCompetition(){
        return competitionRepository.findAll().stream().toList();
    }




    public List<CompetitionOutDTO> recommendCompetitions(Integer participant_id) {
        Participant participant = participantRepository.findParticipantById(participant_id);
        List<Category> participantCategories = new ArrayList<>(participant.getCategories());
        if (participantCategories.isEmpty()) {
            throw new ApiException("participant has no categories associated with their profile.");
        }
        List<CompetitionOutDTO> recommendedCompetitions = new ArrayList<>();
        for (Competition competition : competitionRepository.findAll()) {
            for (Category category : competition.getCategories()) {
                if (participantCategories.contains(category)) {
                    CompetitionOutDTO competitionOutDTO = new CompetitionOutDTO();
                    competitionOutDTO.setTitle(competition.getTitle());
                    competitionOutDTO.setDescription(competition.getDescription());
                    competitionOutDTO.setVotingMethod(competition.getVotingMethod());
                    competitionOutDTO.setEndDate(competition.getEndDate());
                    competitionOutDTO.setMaxParticipants(competition.getMaxParticipants());

                    recommendedCompetitions.add(competitionOutDTO);
                    break; // to add each competition only once
                }
            }
            if (recommendedCompetitions.size() >= 3) {
                break;
            }
        }
        return recommendedCompetitions;
    }

    public void addReviewOnCompetition(Integer participant_id, Integer competition_id, String review) {
        Competition competition = competitionRepository.findCompetitionById(competition_id);
        Participant participant = participantRepository.findParticipantById(participant_id);
        if (competition == null) {
            throw new ApiException("competition not found");
        }
        // check if participant is in the competition
        List<Participant> participants = new ArrayList<>();
        for (Submission submission : competition.getSubmissions()) {
            participants.add(submission.getParticipant());
        }
        if (!participants.contains(participant)) {
            throw new ApiException("cannot add review in this competition");
        }
        competition.getReviews().add(review);
        competitionRepository.save(competition);
    }

    //Naelah
    public List<String> companyGetMyCompetitionReviews(Integer company_organizer_id, Integer competition_id) {
        CompanyOrganizer companyOrganizer = companyOrganizerRepository.findCompanyOrganizerById(company_organizer_id);
        Competition competition = competitionRepository.findCompetitionById(competition_id);
        if (companyOrganizer == null) {
            throw new ApiException("company organizer not found");
        }
        if (competition == null) {
            throw new ApiException("company competition not found");
        }
        if (!competition.getCompanyCompetition().getCompanyOrganizer().getId().equals(companyOrganizer.getId())) {
            throw new ApiException("company organizer cannot view this competition submissions");
        }
        List<String> reviews = new ArrayList<>();
        for (String review : competition.getReviews()) {
            reviews.add(review);
        }
        return reviews;
    }

    //NOT TESTED
    public List<String> individualGetMyCompetitionReviews(Integer individual_organizer_id, Integer competition_id) {
        IndividualOrganizer individualOrganizer = individualOrganizerRepository.findIndividualOrganizerById(individual_organizer_id);
        Competition competition = competitionRepository.findCompetitionById(competition_id);
        if (individualOrganizer == null) {
            throw new ApiException("individual organizer not found");
        }
        if (competition == null) {
            throw new ApiException("individual competition not found");
        }
        if (!competition.getIndividualCompetition().getIndividualOrganizer().getId().equals(individualOrganizer.getId())) {
            throw new ApiException("individual organizer cannot view this competition submissions");
        }
        List<String> reviews = new ArrayList<>();
        for (String review : competition.getReviews()) {
            reviews.add(review);
        }
        return reviews;
    }

}
