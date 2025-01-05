package com.example.ideasphere.Controller;

import com.example.ideasphere.ApiResponse.ApiResponse;
import com.example.ideasphere.DTOsIN.CompanyOrganizerDTOIn;
import com.example.ideasphere.Model.MyUser;
import com.example.ideasphere.Service.CompanyOrganizerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/company-organizer")
@RequiredArgsConstructor
public class CompanyOrganizerController {

    private final CompanyOrganizerService companyOrganizerService;


    @GetMapping("/get-profile")
    public ResponseEntity getProfile(@AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.ok(companyOrganizerService.getMyProfile(myUser.getId()));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid CompanyOrganizerDTOIn companyOrganizerDTOIn){
        companyOrganizerService.register(companyOrganizerDTOIn);
        return ResponseEntity.status(201).body(new ApiResponse("Company Organizer is Registered"));
    }
    @PutMapping("/update-profile")
    public ResponseEntity update(@AuthenticationPrincipal MyUser myUser ,@RequestBody @Valid CompanyOrganizerDTOIn companyOrganizerDTOIn){
        companyOrganizerService.updateProfile(myUser.getId() , companyOrganizerDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Company Organizer is updated"));
    }

}
