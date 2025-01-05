package com.example.ideasphere.Service;

import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.DTOsOut.SubmissionOutDTO;
import com.example.ideasphere.Model.Competition;
import com.example.ideasphere.Model.Participant;
import com.example.ideasphere.Model.Submission;
import com.example.ideasphere.Repository.CompetitionRepository;
import com.example.ideasphere.Repository.ParticipantRepository;
import com.example.ideasphere.Repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final ParticipantRepository participantRepository;
    private final CompetitionRepository competitionRepository;

    public List<Submission> getAllSubmissions(){
        return submissionRepository.findAll();
    }

    public void submit(Integer participant_id, Integer competition_id, Submission submission){
        Participant participant = participantRepository.findParticipantById(participant_id);
        Competition competition = competitionRepository.findCompetitionById(competition_id);
        if(participant == null){
            throw new ApiException("participant not found");
        }
        if(competition == null){
            throw new ApiException("competition not found");
        }
        submission.setParticipant(participant);
        submission.setCompetition(competition);
        submission.setSubmittedAt(LocalDateTime.now());
        submissionRepository.save(submission);
    }

    // for participant
//    public List<SubmissionOutDTO> getMySubmissions(Integer participant_id){
//        Participant participant = participantRepository.findParticipantById(participant_id);
//        List<SubmissionOutDTO> submissionOutDTOS = new ArrayList<>();
//        for(Submission s : participant.getSubmissions()){
//            submissionOutDTOS.add(new SubmissionOutDTO(s.getPDFFile(), s.getFileURL(), s.getSecondFileURL(),s.getThirdFileURL(),s.getDescription(), s.getSubmittedAt(), s.getCompetition().getTitle()));
//        }
//        return submissionOutDTOS;
//    }

    // helper method to find what competition the participant won
    public Submission findTheCompetitionIWon(Integer competition_id, Integer participant_id){
        Participant participant = participantRepository.findParticipantById(participant_id);
        Competition competition = competitionRepository.findCompetitionById(competition_id);
        if(competition == null){
            throw new ApiException("competition not found");
        }
        if(participant == null){
            throw new ApiException("participant not found");
        }

        for(Submission submission : participant.getSubmissions()){
            if(submission.getCompetition().getId().equals(competition.getId())){
                return submission;
            }
        }
        return null;
    }

}
