package com.example.ideasphere.Controller;

import com.example.ideasphere.ApiResponse.ApiResponse;
import com.example.ideasphere.DTOsIN.IndividualCompetitionDTOsIN;
import com.example.ideasphere.DTOsIN.IndividualCompetitionExtendDTOIn;
import com.example.ideasphere.DTOsIN.IndividualCompetitionPaymentDTOIn;
import com.example.ideasphere.DTOsIN.IndividualCompetitionUpdateDTOIn;
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
            @RequestBody @Valid IndividualCompetitionUpdateDTOIn individualCompetitionUpdateDTOIn){
        individualCompetitionService.updateIndividualCompetition(user.getId(),individualCompetitionUpdateDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully updated individualCompetition."));
    }

    @GetMapping("/get-competitions-by-status/{status}")
    public List<IndividualCompetitionDTOOut> getCompetitionsByStatus(
            @AuthenticationPrincipal MyUser user,
            @PathVariable String status) {
        return individualCompetitionService.getAllIndividualCompetitionsByStatus(user.getId(),status);
    }

    @PostMapping("/add-competition-payment")
    public ResponseEntity<ApiResponse> addPayment(
            @AuthenticationPrincipal MyUser myUser,
            @RequestBody @Valid IndividualCompetitionPaymentDTOIn individualCompetitionPaymentDTOIn){
        individualCompetitionService.addPayment(myUser.getId(), individualCompetitionPaymentDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Competition added payment successfully."));
    }
    @PutMapping("/cancel-competition/{individualCompetitionId}")
    public ResponseEntity<ApiResponse> cancelCompetition(
            @AuthenticationPrincipal MyUser myUser,
            @PathVariable Integer individualCompetitionId){
        individualCompetitionService.cancelCompetition(myUser.getId(), individualCompetitionId);
        return ResponseEntity.status(200).body(new ApiResponse("Competition canceled Successfully."));
    }
    @PutMapping("/extend-competition")
    public ResponseEntity<ApiResponse> extendCompetition(
            @AuthenticationPrincipal MyUser myUser,
            @RequestBody @Valid IndividualCompetitionExtendDTOIn individualCompetitionExtendDTOIn){
        individualCompetitionService.extendCompetition(myUser.getId(), individualCompetitionExtendDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Competition extend successfully."));
    }


}
