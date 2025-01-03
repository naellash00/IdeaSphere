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

    @GetMapping("/get/submissions")
    public ResponseEntity getAllSubmissions(){
        return ResponseEntity.status(200).body(submissionService.getAllSubmissions());
    }
    @PostMapping("/submit/{participant_id}/{competition_id}")
    public ResponseEntity submit(@PathVariable Integer participant_id, @PathVariable Integer competition_id, @RequestBody @Valid Submission submission){
        //submissionService.submit(participant_id, submission);
        submissionService.submit(participant_id, competition_id, submission);
        return ResponseEntity.status(200).body(new ApiResponse("Submit Completed Successfully"));
    }

    @GetMapping("/get/my-submissions/{participant_id}")
    public ResponseEntity getMySubmissions(@PathVariable Integer participant_id){
        return ResponseEntity.status(200).body(submissionService.getMySubmissions(participant_id));
    }
}
