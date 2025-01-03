package com.example.ideasphere.Controller;

import com.example.ideasphere.ApiResponse.ApiResponse;
import com.example.ideasphere.Model.IndividualCompetition;
import com.example.ideasphere.Model.MyUser;
import com.example.ideasphere.Service.IndividualCompetitionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/api/v1/individual-competition")
@RequiredArgsConstructor
public class IndividualCompetitionController {

    @Autowired
    private final IndividualCompetitionService individualCompetitionService;

    @GetMapping("/get-IndividualCompetitions")
   public ResponseEntity<List<IndividualCompetition>> getIndividualCompetitions(@AuthenticationPrincipal MyUser user){
        return ResponseEntity.ok(individualCompetitionService.findAllIndividualCompetitions(user.getId()));
    }

    @PostMapping("/add-IndividualCompetition")
    public ResponseEntity<ApiResponse> add(
            @AuthenticationPrincipal MyUser user,
            @RequestBody @Valid IndividualCompetition individualCompetition){
        individualCompetitionService.addIndividualCompetition(user.getId(),individualCompetition);

        return ResponseEntity.status(200).body(new ApiResponse("Successfully added individualCompetition."));
    }

    @PutMapping("/update-IndividualCompetition")
    public ResponseEntity<ApiResponse> update(
            @AuthenticationPrincipal MyUser user,
            @RequestBody @Valid IndividualCompetition individualCompetition){
        individualCompetitionService.updateIndividualCompetition(user.getId(),individualCompetition);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully updated individualCompetition."));
    }

    @DeleteMapping("/delete-IndividualCompetition/{individualCompetitionId}")
    public ResponseEntity<ApiResponse> delete(
            @AuthenticationPrincipal MyUser user,
            @PathVariable Integer individualCompetitionId){
        individualCompetitionService.deleteIndividualCompetition(user.getId(),individualCompetitionId );
        return ResponseEntity.status(200).body(new ApiResponse("Successfully Deleted individualCompetition."));
    }

}
