package com.example.ideasphere.Controller;

import com.example.ideasphere.ApiResponse.ApiResponse;
import com.example.ideasphere.DTOsIN.*;
import com.example.ideasphere.DTOsOut.CompanyCompetitionDTOOut;
import com.example.ideasphere.Model.MyUser;
import com.example.ideasphere.Service.CompanyCompetitionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/company-competition")
@RequiredArgsConstructor
public class CompanyCompetitionController {

    private final CompanyCompetitionService companyCompetitionService;

    @GetMapping("/get-all-competition")
    public ResponseEntity<List<CompanyCompetitionDTOOut>> getAllCompetitions() {
        List<CompanyCompetitionDTOOut> competitions = companyCompetitionService.getAllCompanyCompetition();
        return ResponseEntity.ok(competitions);
    }

    @GetMapping("/get-my-competitions")
    public ResponseEntity<List<CompanyCompetitionDTOOut>> getMyCompetitions(@AuthenticationPrincipal MyUser myUser) {
        List<CompanyCompetitionDTOOut> competitions = companyCompetitionService.getMyCompetition(myUser.getId());
        return ResponseEntity.ok(competitions);
    }

    @PostMapping("/create-competition-financial-interview")
    public ResponseEntity<ApiResponse> createFinancialInterview(
            @AuthenticationPrincipal MyUser myUser,
            @RequestBody @Valid CompanyCompetitionFinancialInterviewDTOIn competitionDTO) {
        companyCompetitionService.createCompetitionFinancialInterview(myUser.getId(), competitionDTO);
        return ResponseEntity.status(201).body(new ApiResponse("Competition created successfully."));
    }

    @PostMapping("/create-competition-interview")
    public ResponseEntity<ApiResponse> createInterview(
            @AuthenticationPrincipal MyUser myUser,
            @RequestBody @Valid CompanyCompetitionInterviewDTOIn competitionDTO) {
        companyCompetitionService.createCompetitionInterview(myUser.getId(), competitionDTO);
        return ResponseEntity.status(201).body(new ApiResponse("Competition created successfully."));
    }

    @PostMapping("/create-competition-financial-by-vote")
    public ResponseEntity<ApiResponse> createFinancialByVote(
            @AuthenticationPrincipal MyUser myUser,
            @RequestBody @Valid CompanyCompetitionFinancialByVoteDTOIn competitionDTO) {
        companyCompetitionService.createCompetitionFinancialByVoteDTOIn(myUser.getId(), competitionDTO);
        return ResponseEntity.status(201).body(new ApiResponse("Competition created successfully."));
    }

    @PostMapping("/create-competition-financial-by-organizer")
    public ResponseEntity<ApiResponse> createFinancialByOrganizer(
            @AuthenticationPrincipal MyUser myUser,
            @RequestBody @Valid CompanyCompetitionFinancialByOrganizerDTOIn competitionDTO) {
        companyCompetitionService.createCompetitionFinancialByOrganizerDTOIn(myUser.getId(), competitionDTO);
        return ResponseEntity.status(201).body(new ApiResponse("Competition created successfully."));
    }
    @PostMapping("/add-competition-payment")
    public ResponseEntity<ApiResponse> addPayment(
            @AuthenticationPrincipal MyUser myUser,
            @RequestBody @Valid CompanyCompetitionPaymentDTOIn competitionPaymentDTOIn){
        companyCompetitionService.addPayment(myUser.getId(), competitionPaymentDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Competition added payment successfully."));
    }

    @PutMapping("/extend-competition")
    public ResponseEntity<ApiResponse> extendCompetition(
            @AuthenticationPrincipal MyUser myUser,
            @RequestBody @Valid CompanyCompetitionExtendDTOIn companyCompetitionExtendDTOIn){
        companyCompetitionService.extendCompetition(myUser.getId(), companyCompetitionExtendDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Competition extend successfully."));
    }

    @PutMapping("/update-competition")
    public ResponseEntity<ApiResponse> updateCompetition(
            @AuthenticationPrincipal MyUser myUser,
            @RequestBody @Valid CompanyCompetitionUpdateDTOIn companyCompetitionUpdateDTOIn){
        companyCompetitionService.updateCompetition(myUser.getId(), companyCompetitionUpdateDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Competition updated successfully."));
    }

    @PutMapping("/select/winner/{competition_id}/{submission_id}")
    public ResponseEntity selectWinner(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer competition_id, @PathVariable Integer submission_id){
        companyCompetitionService.selectWinner(competition_id, submission_id);
        return ResponseEntity.status(200).body(new ApiResponse("Winner Selected Successfully"));
    }


}
