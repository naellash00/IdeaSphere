package com.example.ideasphere.Controller;

import com.example.ideasphere.ApiResponse.ApiResponse;
import com.example.ideasphere.DTOsIN.IndividualOrganizerDTOsIN;
import com.example.ideasphere.Model.MyUser;
import com.example.ideasphere.Service.IndividualOrganizerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/api/v1/individual-organizer")
@RequiredArgsConstructor

public class IndividualOrganizerController {

    @Autowired
    private final IndividualOrganizerService individualOrganizerService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerIndividualOrganize(@RequestBody @Valid IndividualOrganizerDTOsIN IndividualOrganizer) {
        individualOrganizerService.register(IndividualOrganizer);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully registered Individual Organizer"));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateIndividualOrganize(
            @AuthenticationPrincipal MyUser myUser,
            @Valid @RequestBody IndividualOrganizerDTOsIN individualOrganizerDTOsIN) {
        individualOrganizerService.updateIndividualOrganizer(myUser.getId(), individualOrganizerDTOsIN);
        return ResponseEntity.status(200).body(new ApiResponse("individual Organizer updated successfully"));
    }


    // hussam
    @PostMapping("/send-inquiry")
    public ResponseEntity<ApiResponse> sendMail1(
            @AuthenticationPrincipal MyUser myUser,
            @RequestParam String subject,
            @RequestParam String text) {
        individualOrganizerService.send_inquiry(myUser.getId(), subject, text);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully sent inquiry"));
    }

    //Naelah
    @GetMapping("/view/my-competition/submissions/{competition_id}")
    public ResponseEntity viewMyCompetitionSubmissions(@PathVariable Integer competition_id) {
        return ResponseEntity.status(200).body(individualOrganizerService.viewMyCompetitionSubmissions(competition_id));
    }

}
