package com.example.ideasphere.Controller;

import com.example.ideasphere.ApiResponse.ApiResponse;
import com.example.ideasphere.DTOsIN.IndividualCompetitionDTOsIN;
import com.example.ideasphere.DTOsOut.IndividualCompetitionDTOOut;
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


    @GetMapping("/get-all-competition")
    public ResponseEntity<List<IndividualCompetitionDTOOut>> getAllIndividualCompetitions(){
        return ResponseEntity.ok(individualCompetitionService.getAllIndividualCompetitions());
    }

    @GetMapping("/get-my-competitions")
    public ResponseEntity<List<IndividualCompetitionDTOOut>> getMyCompetitions(@AuthenticationPrincipal MyUser myUser) {
        List<IndividualCompetitionDTOOut> competitions = individualCompetitionService.getMyCompetition(myUser.getId());
        return ResponseEntity.ok(competitions);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> add(
            @AuthenticationPrincipal MyUser user,
            @RequestBody @Valid IndividualCompetitionDTOsIN individualCompetitionDTOsIN){
        individualCompetitionService.addIndividualCompetition(user.getId(),individualCompetitionDTOsIN);

        return ResponseEntity.status(200).body(new ApiResponse("Successfully added individualCompetition."));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> update(
            @AuthenticationPrincipal MyUser user,
            @RequestBody @Valid IndividualCompetitionDTOsIN individualCompetitionDTOsIN){
        individualCompetitionService.updateIndividualCompetition(user.getId(),individualCompetitionDTOsIN);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully updated individualCompetition."));
    }

    @GetMapping("/get-competitions-by-status/{status}")
    public List<IndividualCompetitionDTOOut> getCompetitionsByStatus(
            @AuthenticationPrincipal MyUser user,
            @PathVariable String status) {
        return individualCompetitionService.getAllIndividualCompetitionsByStatus(user.getId(),status);
    }

    //Naelah
    @PutMapping("/select/winner/{competition_id}/{submission_id}")
    public ResponseEntity selectWinner(@PathVariable Integer competition_id, @PathVariable Integer submission_id) {
        individualCompetitionService.selectWinner(competition_id, submission_id);
        return ResponseEntity.status(200).body(new ApiResponse("Winner Selected Successfully"));
    }

    //Naelah
    @GetMapping("/get/my-competition/review/{competition_id}")
    public ResponseEntity getMyCompetitionReviews(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer competition_id) {
        return ResponseEntity.status(200).body(individualCompetitionService.getMyCompetitionReviews(competition_id));
    }


}
