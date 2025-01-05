package com.example.ideasphere.Controller;

import com.example.ideasphere.ApiResponse.ApiResponse;
import com.example.ideasphere.Model.MyUser;
import com.example.ideasphere.Repository.VoteRepository;
import com.example.ideasphere.Service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/votes")
@RequiredArgsConstructor
public class VoteController {
    private final VoteService voteService;
    @PutMapping("/vote/{submission_id}")
    public ResponseEntity vote(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer submission_id){
        voteService.vote(myUser.getId(), submission_id);
        return ResponseEntity.status(200).body(new ApiResponse("Voted Successfully"));
    }
}
