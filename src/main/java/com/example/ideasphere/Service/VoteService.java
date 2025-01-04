package com.example.ideasphere.Service;

import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.Model.Participant;
import com.example.ideasphere.Model.Submission;
import com.example.ideasphere.Model.Vote;
import com.example.ideasphere.Repository.ParticipantRepository;
import com.example.ideasphere.Repository.SubmissionRepository;
import com.example.ideasphere.Repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final ParticipantRepository participantRepository;
    private final SubmissionRepository submissionRepository;

    public void vote(Integer voter_id, Integer submission_id) {
        Participant voter = participantRepository.findParticipantById(voter_id);
        Submission submission = submissionRepository.findSubmissionById(submission_id);
        if (voter == null) {
            throw new ApiException("voter not found");
        }
        if (submission == null) {
            throw new ApiException("submission not found");
        }
        if (!submission.getCompetition().getVotingMethod().equalsIgnoreCase("By Public Vote")) {
            throw new ApiException("this competition is not under vote");
        }
        Vote vote = new Vote();
        vote.setVoter(voter);
        vote.setSubmission(submission);
        vote.setVoteDate(LocalDateTime.now());
        // add points to the voter
        voter.setPoints(voter.getPoints() + 10);

        submission.getVotes().add(vote);
        voteRepository.save(vote);
        submissionRepository.save(submission);
        participantRepository.save(voter);
    }
}
