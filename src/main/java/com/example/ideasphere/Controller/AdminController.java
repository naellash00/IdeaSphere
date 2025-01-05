package com.example.ideasphere.Controller;
import com.example.ideasphere.ApiResponse.ApiResponse;
import com.example.ideasphere.DTOsIN.AdminInDTO;
import com.example.ideasphere.DTOsIN.ParticipantInDTO;
import com.example.ideasphere.Service.AdminService;
import com.example.ideasphere.Service.CompanyOrganizerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;


    @PutMapping("/active-company-user/{id}")
    public ResponseEntity active(@PathVariable Integer id){
        adminService.activeCompany(id);
        return ResponseEntity.ok().body(new ApiResponse("Company Organizer is Active"));
    }

    @PutMapping("/detective-company-user/{id}")
    public ResponseEntity detectiveCompany(@PathVariable Integer id){
        adminService.detectiveCompany(id);
        return ResponseEntity.ok().body(new ApiResponse("Company Organizer is Detectived"));
    }
}
