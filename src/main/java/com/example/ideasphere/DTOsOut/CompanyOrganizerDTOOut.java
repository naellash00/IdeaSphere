package com.example.ideasphere.DTOsOut;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyOrganizerDTOOut {


    private String username;
    private String name;
    private String email;
    private String role;
    private String companyName;
    private String commercialRecord;
    private String contactEmail;
    private String contactPhone;
    private String status;
}
