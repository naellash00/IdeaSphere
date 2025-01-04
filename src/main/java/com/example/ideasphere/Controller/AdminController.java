package com.example.ideasphere.Controller;
import com.example.ideasphere.ApiResponse.ApiResponse;
import com.example.ideasphere.DTOsIN.AdminInDTO;
import com.example.ideasphere.DTOsIN.ParticipantInDTO;
import com.example.ideasphere.Service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid AdminInDTO adminInDTO){
        adminService.register(adminInDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Admin Registered Successfully"));
    }
}
