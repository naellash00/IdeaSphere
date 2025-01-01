package com.example.ideasphere.Service;

import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.DTOsIN.ParticipantInDTO;
import com.example.ideasphere.Model.MyUser;
import com.example.ideasphere.Model.Participant;
import com.example.ideasphere.Repository.AuthRepository;
import com.example.ideasphere.Repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipantService { // Naelah
    private final AuthRepository authRepository;
    private final ParticipantRepository participantRepository;

    public List<Participant> getAllParticipants(){
        return participantRepository.findAll();
    }

    public void register(ParticipantInDTO participantInDTO){
        MyUser user = new MyUser();
        //Participant participant = new Participant(null, participantInDTO.getBankAccountNumber(), null, user, null);
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

    public void updateParticipant(Integer id, ParticipantInDTO participantInDTO){
        MyUser user = authRepository.findMyUserById(id);
        Participant oldParticipant = participantRepository.findParticipantById(id);
        if(user == null || oldParticipant == null){
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

    public void deleteParticipant(Integer id){
        MyUser user = authRepository.findMyUserById(id);
        Participant participant = participantRepository.findParticipantById(id);
        if(user == null || participant == null){
            throw  new ApiException("Participant not found");
        }
        authRepository.delete(user);
        participantRepository.delete(participant);
    }
}
