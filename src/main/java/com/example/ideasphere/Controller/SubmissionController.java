package com.example.ideasphere.Controller;

import com.example.ideasphere.ApiResponse.ApiResponse;
import com.example.ideasphere.Model.Submission;
import com.example.ideasphere.Service.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/submission")
@RequiredArgsConstructor
public class SubmissionController {
    private final SubmissionService submissionService;

    @PostMapping("/submit/{participant_id}")
    public ResponseEntity submit(@PathVariable Integer participant_id, @RequestBody @Valid Submission submission){
        submissionService.submit(participant_id, submission);
        return ResponseEntity.status(200).body(new ApiResponse("Submit Completed Successfully"));
    }

    @PutMapping("/update/{participant_id}/{submission_id}")
    public ResponseEntity updateSubmission(@PathVariable Integer participant_id, @PathVariable Integer submission_id, @RequestBody @Valid Submission submission){
        submissionService.updateSubmission(participant_id, submission_id, submission);
        return ResponseEntity.status(200).body(new ApiResponse("Submission Updated Successfully"));
    }
}
