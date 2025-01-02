package com.example.ideasphere.Controller;

import com.example.ideasphere.ApiResponse.ApiResponse;
import com.example.ideasphere.Repository.VoteRepository;
import com.example.ideasphere.Service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/votes")
@RequiredArgsConstructor
public class VoteController {
    private final VoteService voteService;

    @PutMapping("/vote/{voter_id}/{submission_id}")
    public ResponseEntity vote(@PathVariable Integer voter_id, @PathVariable Integer submission_id){
        voteService.vote(voter_id, submission_id);
        return ResponseEntity.status(200).body(new ApiResponse("Voted Successfully"));
    }
}
