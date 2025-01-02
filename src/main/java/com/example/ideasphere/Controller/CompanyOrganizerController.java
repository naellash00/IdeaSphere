package com.example.ideasphere.Controller;

import com.example.ideasphere.ApiResponse.ApiResponse;
import com.example.ideasphere.DTOsIN.CompanyOrganizerDTOIn;
import com.example.ideasphere.Service.CompanyOrganizerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/company-organizer")
@RequiredArgsConstructor
public class CompanyOrganizerController {

    private final CompanyOrganizerService companyOrganizerService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid CompanyOrganizerDTOIn companyOrganizerDTOIn){
        companyOrganizerService.register(companyOrganizerDTOIn);
        return ResponseEntity.status(201).body(new ApiResponse("Company Organizer is Registered"));
    }

    @PutMapping("/active/{id}")
    public ResponseEntity active(@PathVariable Integer id){
        companyOrganizerService.activeCompany(id);
        return ResponseEntity.ok().body(new ApiResponse("Company Organizer is Active"));
    }

    @PutMapping("/detective/{id}")
    public ResponseEntity detectiveCompany(@PathVariable Integer id){
        companyOrganizerService.detectiveCompany(id);
        return ResponseEntity.ok().body(new ApiResponse("Company Organizer is Detectived"));
    }
}
