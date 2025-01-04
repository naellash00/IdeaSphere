package com.example.ideasphere.Service;

import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.DTOsIN.ParticipantInDTO;
import com.example.ideasphere.DTOsOut.AchievementOutDTO;
import com.example.ideasphere.DTOsOut.CategoryOutDTO;
import com.example.ideasphere.DTOsOut.ParticipantOutDTO;
import com.example.ideasphere.Model.*;
import com.example.ideasphere.Repository.AuthRepository;
import com.example.ideasphere.Repository.CompanyCompetitionRepository;
import com.example.ideasphere.Repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipantService { // Naelah
    private final AuthRepository authRepository;
    private final ParticipantRepository participantRepository;
    private final SubmissionService submissionService;
    private final CompanyCompetitionRepository companyCompetitionRepository;

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

        participant.setUser(user);
        participant.setBankAccountNumber(participantInDTO.getBankAccountNumber());

        authRepository.save(user);
        participantRepository.save(participant);
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


    public List<AchievementOutDTO> getMyAchievements(Integer participant_id){
        Participant participant = participantRepository.findParticipantById(participant_id);
        if(participant == null){
            throw new ApiException("participant not found");
        }
        List<AchievementOutDTO> achievements = new ArrayList<>();
        // check it this participant won any competition
        for(Competition competition : participant.getCompetitionsWinner()){
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
}
