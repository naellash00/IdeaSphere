package com.example.ideasphere.Controller;

import com.example.ideasphere.ApiResponse.ApiResponse;
import com.example.ideasphere.DTOsIN.ParticipantInDTO;
import com.example.ideasphere.Service.ParticipantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/participant")
@RequiredArgsConstructor
public class ParticipantController { // Naelah
    private final ParticipantService participantService;

    @GetMapping("/get/participants")
    public ResponseEntity getAllParticipants(){
        return ResponseEntity.status(200).body(participantService.getAllParticipants());
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid ParticipantInDTO participantInDTO){
        participantService.register(participantInDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Participant Registered Successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateParticipant(@PathVariable Integer id, @RequestBody @Valid ParticipantInDTO participantInDTO){
        participantService.updateParticipant(id, participantInDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Participant Updated Successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteParticipant(@PathVariable Integer id){
        participantService.deleteParticipant(id);
        return ResponseEntity.status(200).body(new ApiResponse("Participant Deleted Successfully"));
    }
}
