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
@RequestMapping( "/api/v1/individualOrganizer")
@RequiredArgsConstructor

public class IndividualOrganizerController {

    @Autowired
    private final IndividualOrganizerService individualOrganizerService;

    @PostMapping("/register-IndividualOrganizer")
    public ResponseEntity<ApiResponse> registerIndividualOrganize(@RequestBody @Valid IndividualOrganizerDTOsIN IndividualOrganizer) {
        individualOrganizerService.register(IndividualOrganizer);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully registered Individual Organizer"));
    }

    @PutMapping("/update-IndividualOrganizer")
    public ResponseEntity<ApiResponse> updateIndividualOrganize(
            @AuthenticationPrincipal MyUser myUser,
            @Valid @RequestBody IndividualOrganizerDTOsIN individualOrganizerDTOsIN) {
        individualOrganizerService.updateIndividualOrganizer(myUser.getId(), individualOrganizerDTOsIN);
        return ResponseEntity.status(200).body(new ApiResponse("individual Organizer updated successfully"));
    }

    @DeleteMapping("/delete-IndividualOrganizer")
    public ResponseEntity<ApiResponse> deleteIndividualOrganize(@AuthenticationPrincipal MyUser myUser) {
        individualOrganizerService.deleteIndividualOrganizer(myUser.getId());
        return ResponseEntity.status(200).body(new ApiResponse("individual Organizer deleted successfully"));
    }


}
