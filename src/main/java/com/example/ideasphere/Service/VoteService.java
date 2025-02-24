package com.example.ideasphere.Service;

import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.Model.Participant;
import com.example.ideasphere.Model.Submission;
import com.example.ideasphere.Model.Vote;
import com.example.ideasphere.Repository.CompetitionRepository;
import com.example.ideasphere.Repository.ParticipantRepository;
import com.example.ideasphere.Repository.SubmissionRepository;
import com.example.ideasphere.Repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class VoteService { // naelah
    private final VoteRepository voteRepository;
    private final ParticipantRepository participantRepository;
    private final SubmissionRepository submissionRepository;
    private final CompetitionRepository competitionRepository;

    public void vote(Integer voter_id, Integer submission_id) {
        Participant voter = participantRepository.findParticipantById(voter_id);
        Submission submission = submissionRepository.findSubmissionById(submission_id);
        if (voter == null) {
            throw new ApiException("voter not found");
        }
        if (submission == null) {
            throw new ApiException("submission not found");
        }


        if (!"Under Voting Process".equalsIgnoreCase(submission.getCompetition().getStatus())) {
            throw new ApiException("this competition is not under vote process");
        }
        // check voter is not in the competition
        if(submission.getParticipant().getId().equals(voter.getId())){
            throw new ApiException("Participant cannot vote on this competition");
        }
        //disjoint to check  two collections have no mutual elements
        if(Collections.disjoint(submission.getCompetition().getCategories(), voter.getCategories())){
            throw new ApiException("Participant cannot vote on this competition, no matching categories");
        }
        // to vote once only
        for (Vote vote : submission.getVotes()) {
            if (vote.getVoter().getId().equals(voter.getId())) {
                throw new ApiException("You have already voted for this submission");
            }
        }

        Vote vote = new Vote();
        vote.setVoter(voter);
        vote.setSubmission(submission);
        vote.setCompetition(submission.getCompetition());
        vote.setVoteDate(LocalDateTime.now());
        // add points to the voter
        voter.setPoints(voter.getPoints() + 10);

        submission.getVotes().add(vote);
        voteRepository.save(vote);
        submissionRepository.save(submission);
        participantRepository.save(voter);
    }
}
