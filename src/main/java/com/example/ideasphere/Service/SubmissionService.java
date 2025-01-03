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

    public List<Submission> getAllSubmissions(){
        return submissionRepository.findAll();
    }
    // add submission
    public void submit(Integer participant_id, Submission submission){
        Participant participant = participantRepository.findParticipantById(participant_id);
        if(participant == null){
            throw new ApiException("participant not found");
        }
        submission.setParticipant(participant);
        submissionRepository.save(submission);
    }

    public void updateSubmission(Integer participant_id, Integer submission_id, Submission submission){
        Participant participant = participantRepository.findParticipantById(participant_id);
        Submission oldSubmission = submissionRepository.findSubmissionById(submission_id);
        if(participant == null){
            throw new ApiException("Participant not found");
        }
        if(oldSubmission == null){
            throw new ApiException("Submission not found");
        }
        if(oldSubmission.getParticipant().getId().equals(participant.getId())){
            throw new ApiException("participant cannot update this submission");
        }
        oldSubmission.setPDFFile(submission.getPDFFile());
        oldSubmission.setFileURL(submission.getFileURL());
        oldSubmission.setSecondFileURL(submission.getSecondFileURL());
        oldSubmission.setThirdFileURL(submission.getThirdFileURL());
        oldSubmission.setDescription(submission.getDescription());
        // ?
        oldSubmission.setSubmittedAt(LocalDateTime.now());
        submissionRepository.save(oldSubmission);
    }

    public void deleteSubmission(Integer participant_id, Integer submission_id){

    }


}
