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

//    @PutMapping("/active/{id}")
//    public ResponseEntity<ApiResponse> active(@PathVariable Integer id){
//        individualOrganizerService.activeIndividual(id);
//        return ResponseEntity.ok().body(new ApiResponse("Individual Organizer is Active"));
//    }
//
//    @PutMapping("/detective/{id}")
//    public ResponseEntity<ApiResponse> detectiveCompany(@PathVariable Integer id){
//        individualOrganizerService.detectiveIndividual(id);
//        return ResponseEntity.ok().body(new ApiResponse("Individual Organizer is Detective"));
//    }

}
