package com.example.ideasphere.Controller;

import com.example.ideasphere.ApiResponse.ApiResponse;
import com.example.ideasphere.Model.MyUser;
import com.example.ideasphere.Model.Submission;
import com.example.ideasphere.Service.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequestMapping("/api/v1/submission")
@RequiredArgsConstructor
public class SubmissionController { // Naelah
    private final SubmissionService submissionService;

    @GetMapping("/get/submissions")
    public ResponseEntity getAllSubmissions() {
        return ResponseEntity.status(200).body(submissionService.getAllSubmissions());
    }

    @PostMapping("/submit/{competition_id}")
    public ResponseEntity submit(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer competition_id, @RequestBody @Valid Submission submission) {
        submissionService.submit(myUser.getId(), competition_id, submission);
        return ResponseEntity.status(200).body(new ApiResponse("Submit Completed Successfully"));
    }

    @GetMapping("/get/my-submissions")
    public ResponseEntity getMySubmissions(@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(200).body(submissionService.getMySubmissions(myUser.getId()));
    }

    @PostMapping("/request-feedback/{submission_id}")
    public ResponseEntity requestFeedbackOnSubmission(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer submission_id) {
        submissionService.requestFeedbackOnSubmission(myUser.getId(), submission_id);
        return ResponseEntity.status(200).body(new ApiResponse("Your Feedback Request Is Sent Successfully"));
    }

    @PutMapping("/company/accept/feedback/request/{submission_id}")
    public ResponseEntity companyAcceptFeedbackRequest(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer submission_id, @RequestParam String feedback) {
        submissionService.companyAcceptFeedbackRequest(myUser.getId(),submission_id, feedback);
        return ResponseEntity.status(200).body(new ApiResponse("Your Feedback Request Is Sent Successfully"));
    }

    //not tested
    @PutMapping("/company/reject/feedback/request/{submission_id}")
    public ResponseEntity companyRejectFeedbackRequest(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer submission_id) {
        submissionService.companyRejectFeedbackRequest(myUser.getId(),submission_id);
        return ResponseEntity.status(200).body(new ApiResponse("Your Request Rejection Is Sent Successfully"));
    }

    //Not tested
    @PutMapping("/individual/accept/feedback/request/{submission_id}")
    public ResponseEntity individualAcceptFeedbackRequest(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer submission_id, @RequestParam String feedback) {
        submissionService.individualAcceptFeedbackRequest(myUser.getId(),submission_id, feedback);
        return ResponseEntity.status(200).body(new ApiResponse("Your Feedback Request Is Sent Successfully"));
    }

    //not tested
    @PutMapping("/individual/reject/feedback/request/{submission_id}")
    public ResponseEntity individualRejectFeedbackRequest(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer submission_id) {
        submissionService.individualRejectFeedbackRequest(myUser.getId(),submission_id);
        return ResponseEntity.status(200).body(new ApiResponse("Your Request Rejection Is Sent Successfully"));
    }

    @PutMapping("/company/select/winner/{competition_id}/{submission_id}")
    public ResponseEntity companySelectWinner(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer competition_id, @PathVariable Integer submission_id){
        submissionService.companySelectWinner(myUser.getId(), competition_id, submission_id);
        return ResponseEntity.status(200).body(new ApiResponse("Company Competition Winner Selected Successfully"));
    }

    @PutMapping("/individual/select/winner/{competition_id}/{submission_id}")
    public ResponseEntity individualSelectWinner(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer competition_id, @PathVariable Integer submission_id){
        submissionService.individualSelectWinner(myUser.getId(), competition_id, submission_id);
        return ResponseEntity.status(200).body(new ApiResponse("Individual Competition Winner Selected Successfully"));
    }

    @GetMapping("/company/view/my-competition/submissions/{competition_id}")
    public ResponseEntity CompanyViewMyCompetitionSubmissions(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer competition_id){
        return ResponseEntity.status(200).body(submissionService.companyViewMyCompetitionSubmissions(myUser.getId(), competition_id));
    }

    @GetMapping("/individual/view/my-competition/submissions/{competition_id}")
    public ResponseEntity individualViewMyCompetitionSubmissions(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer competition_id){
        return ResponseEntity.status(200).body(submissionService.individualViewMyCompetitionSubmissions(myUser.getId(), competition_id));
    }
}
