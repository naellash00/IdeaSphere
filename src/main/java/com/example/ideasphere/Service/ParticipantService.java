package com.example.ideasphere.Service;

import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.DTOsIN.ParticipantInDTO;
import com.example.ideasphere.DTOsOut.*;
import com.example.ideasphere.Model.*;
import com.example.ideasphere.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ParticipantService { // Naelah
    private final AuthRepository authRepository;
    private final ParticipantRepository participantRepository;
    private final SubmissionService submissionService;
    private final CompanyCompetitionRepository companyCompetitionRepository;
    private final CompetitionRepository competitionRepository;
    private final SubmissionRepository submissionRepository;

    //    public List<Participant> getAllParticipants(){
//        return participantRepository.findAll();
//    }

    public List<ParticipantOutDTO> getAllParticipants() {
        List<Participant> participants = participantRepository.findAll();
        List<ParticipantOutDTO> participantOutDTOS = new ArrayList<>();
        for (Participant p : participants) {
            List<CategoryOutDTO> categoryOutDTOS = new ArrayList<>();
            List<AchievementOutDTO> achievementOutDTOS = new ArrayList<>();
            for (Category c : p.getCategories()) {
                categoryOutDTOS.add(new CategoryOutDTO(c.getCategoryName()));
            }
            ParticipantOutDTO participantOutDTO = new ParticipantOutDTO(p.getUser().getUsername(), p.getUser().getName(), p.getUser().getEmail(), categoryOutDTOS);
            participantOutDTOS.add(participantOutDTO);
        }
        return participantOutDTOS;
    }

    public void register(ParticipantInDTO participantInDTO) {
        MyUser user = new MyUser();
        Participant participant = new Participant();
        user.setRole("PARTICIPANT");
        user.setUsername(participantInDTO.getUsername());
        String hashPassword = new BCryptPasswordEncoder().encode(participantInDTO.getPassword());
        user.setPassword(hashPassword);
        user.setName(participantInDTO.getName());
        user.setEmail(participantInDTO.getEmail());
        user.setCreatedAt(LocalDateTime.now());

        // check Category Exist
        checkCategoryExist(participantInDTO.getCategories());

        participant.setUser(user);
        participant.setCategories(participantInDTO.getCategories());
        participant.setBankAccountNumber(participantInDTO.getBankAccountNumber());

        authRepository.save(user);
        participantRepository.save(participant);
    }
    public void checkCategoryExist(Set<Category> categories){
        for(Category category : categories){
            Category findCategory = categoryRepository.findCategoryById(category.getId());
            if (findCategory == null )throw new ApiException("Error: category not found ");
        }
    }
    public void updateParticipant(Integer id, ParticipantInDTO participantInDTO) {
        MyUser user = authRepository.findMyUserById(id);
        Participant oldParticipant = participantRepository.findParticipantById(id);
        if (user == null || oldParticipant == null) {
            throw new ApiException("Participant not found");
        }
        user.setUsername(participantInDTO.getUsername());
        String hashPassword = new BCryptPasswordEncoder().encode(participantInDTO.getPassword());
        user.setPassword(hashPassword);
        user.setName(participantInDTO.getName());
        user.setEmail(participantInDTO.getEmail());
        oldParticipant.setBankAccountNumber(participantInDTO.getBankAccountNumber());

        authRepository.save(user);
        participantRepository.save(oldParticipant);
    }

    public List<SubmissionOutDTO> getMySubmissions(Integer participant_id) {
        Participant participant = participantRepository.findParticipantById(participant_id);
        List<SubmissionOutDTO> submissionOutDTOS = new ArrayList<>();
        for (Submission s : participant.getSubmissions()) {
            submissionOutDTOS.add(new SubmissionOutDTO(s.getPDFFile(), s.getFileURL(), s.getSecondFileURL(), s.getThirdFileURL(), s.getDescription(), s.getSubmittedAt(), s.getCompetition().getTitle()));
        }
        return submissionOutDTOS;
    }


    public List<AchievementOutDTO> getMyAchievements(Integer participant_id) {
        Participant participant = participantRepository.findParticipantById(participant_id);
        if (participant == null) {
            throw new ApiException("participant not found");
        }
        List<AchievementOutDTO> achievements = new ArrayList<>();
        // check it this participant won any competition
        for (Competition competition : participant.getCompetitionsWinner()) {
            CompanyCompetition companyCompetition = companyCompetitionRepository.findCompanyCompetitionById(competition.getId());
            AchievementOutDTO achievementOutDTO = new AchievementOutDTO();
            achievementOutDTO.setCompetitionTitle(competition.getTitle());
            achievementOutDTO.setVotingMethod(competition.getVotingMethod());
            achievementOutDTO.setMySubmissionFile(submissionService.findTheCompetitionIWon(competition.getId(), participant.getId()).getPDFFile());
            achievementOutDTO.setMySubmissionDescription(submissionService.findTheCompetitionIWon(competition.getId(), participant.getId()).getDescription());
            achievementOutDTO.setSubmittedAt(submissionService.findTheCompetitionIWon(competition.getId(), participant.getId()).getSubmittedAt());
            // achievementOutDTO.setRewardType(companyCompetition.getRewardType());
            // achievementOutDTO.setMonetaryReward(companyCompetition.getMonetaryReward());
            achievements.add(achievementOutDTO);
        }
        return achievements;
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
                    break; // Add each competition only once
                }
            }
            if (recommendedCompetitions.size() >= 3) {
                break;
            }
        }
        return recommendedCompetitions;
    }

    public void requestFeedbackOnSubmission(Integer participant_id, Integer submission_id) {
        Submission submission = submissionRepository.findSubmissionById(submission_id);
        if (submission.getFeedbackRequestStatus() != null) {
            throw new ApiException("Feedback request already made");
        }
        if(submission.getParticipant().getId().equals(participant_id)){
            throw new ApiException("Cannot request feedback on this submission");
        }
        // another check
        submission.setFeedbackRequestStatus("Pending");
        submissionRepository.save(submission);
    }

    public void addReviewOnCompetition(Integer participant_id, Integer competition_id, String review){
        Competition competition = competitionRepository.findCompetitionById(competition_id);
        Participant participant = participantRepository.findParticipantById(participant_id);
        if(competition == null){
            throw new ApiException("competition not found");
        }
        // check if participant is in the competition
        // helper methods get participants in a competition
        List<Participant> participants = new ArrayList<>();
        for(Submission submission : competition.getSubmissions()){
            participants.add(submission.getParticipant());
        }
        if(!participants.contains(participant)){
            throw new ApiException("cannot add review in this competition");
        }
        competition.getReviews().add(review);
        competitionRepository.save(competition);
    }

    public List<FeedbackOutDTO> getMyFeedbacks(Integer participant_id) {
        Participant participant = participantRepository.findParticipantById(participant_id);
        if (participant == null) {
            throw new ApiException("Participant not found");
        }
        List<FeedbackOutDTO> feedbackList = new ArrayList<>();
        for (Submission submission : participant.getSubmissions()) {
            if (submission.getOrganizerFeedback() != null) {
                FeedbackOutDTO feedbackOutDTO = new FeedbackOutDTO();
                feedbackOutDTO.setStatus(submission.getFeedbackRequestStatus());
                feedbackOutDTO.setFeedback(submission.getOrganizerFeedback());
                feedbackList.add(feedbackOutDTO);
            }
        }
        return feedbackList;
    }
}
