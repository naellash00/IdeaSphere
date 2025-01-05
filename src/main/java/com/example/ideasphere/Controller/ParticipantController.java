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


    @GetMapping("/get/my-achievements")
    public ResponseEntity getMyAchievements(@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(200).body(participantService.getMyAchievements(myUser.getId()));
    }


    //getMyFeedbacks
    @GetMapping("/get/my-feedbacks")
    public ResponseEntity getMyFeedbacks(@AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.status(200).body(participantService.getMyFeedbacks(myUser.getId()));
    }

    @PostMapping("/send-complain")
    public ResponseEntity sendComplain(@AuthenticationPrincipal MyUser myUser, @RequestParam String subject, @RequestParam String text) {
        participantService.sendComplain(myUser.getId(), subject, text);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully sent inquiry"));
    }
}
