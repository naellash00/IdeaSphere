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
    private final EmailSenderJava emailSender;
    private final CategoryRepository categoryRepository;

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

    public void sendComplain(Integer participant_id, String subject, String text) {
        MyUser user = authRepository.findMyUserById(participant_id);
        if (user == null) {
            throw new ApiException("User not found");
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
                        "<li><strong>Participant's Name:</strong> " + user.getName() + "</li>" +
                        "<li><strong>Participant's Email:</strong> " + user.getEmail() + "</li>" +
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
}
