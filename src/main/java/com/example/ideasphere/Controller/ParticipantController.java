package com.example.ideasphere.Controller;

import com.example.ideasphere.ApiResponse.ApiResponse;
import com.example.ideasphere.DTOsIN.ParticipantInDTO;
import com.example.ideasphere.Model.MyUser;
import com.example.ideasphere.Service.CategoryService;
import com.example.ideasphere.Service.ParticipantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/participant")
@RequiredArgsConstructor
public class ParticipantController { // Naelah
    private final ParticipantService participantService;
    private final CategoryService categoryService;

    @GetMapping("/get/participants")
    public ResponseEntity getAllParticipants() {
        return ResponseEntity.status(200).body(participantService.getAllParticipants());
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid ParticipantInDTO participantInDTO) {
        participantService.register(participantInDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Participant Registered Successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateParticipant(@PathVariable Integer id, @RequestBody @Valid ParticipantInDTO participantInDTO) {
        participantService.updateParticipant(id, participantInDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Participant Updated Successfully"));
    }

    @PutMapping("/add/category/to/participant/{participant_id}/{category_id}")
    public ResponseEntity addCategory(@PathVariable Integer participant_id, @PathVariable Integer category_id) {
        categoryService.addCategory(participant_id, category_id);
        return ResponseEntity.status(200).body(new ApiResponse("Category Added To Your Profile Successfully"));
    }

    @GetMapping("/get/my-submissions")
    public ResponseEntity getMySubmissions(@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(200).body(participantService.getMySubmissions(myUser.getId()));
    }

    @GetMapping("/get/my-achievement")
    public ResponseEntity getMyAchievements(@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(200).body(participantService.getMyAchievements(myUser.getId()));
    }

    @GetMapping("/get/recommend/competitions")
    public ResponseEntity recommendCompetitions(@AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.status(200).body(participantService.recommendCompetitions(myUser.getId()));
    }

    @PostMapping("/request-feedback/{submission_id}")
    public ResponseEntity requestFeedbackOnSubmission(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer submission_id){
        participantService.requestFeedbackOnSubmission(myUser.getId(), submission_id);
        return ResponseEntity.status(200).body(new ApiResponse("Your Feedback Request Is Sent Successfully"));
    }

    @PutMapping("/add-review/{competition_id}/{review}")
    public ResponseEntity addReviewOnCompetition(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer competition_id, @PathVariable String review){
        participantService.addReviewOnCompetition(myUser.getId(), competition_id, review);
        return ResponseEntity.status(200).body(new ApiResponse("Your Review  Is Added Successfully"));
    }

    //getMyFeedbacks
    @GetMapping("/get/my-feedbacks")
    public ResponseEntity getMyFeedbacks(@AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.status(200).body(participantService.getMyFeedbacks(myUser.getId()));
    }
}
