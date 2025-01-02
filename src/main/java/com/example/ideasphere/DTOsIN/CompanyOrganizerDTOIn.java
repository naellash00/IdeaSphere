package com.example.ideasphere.DTOsIN;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanyOrganizerDTOIn {

    private Integer id;

    @NotEmpty(message = "Username Cannot Be Empty")
    @Size(min = 5, max =  15, message = "Username Must Be Between 5 - 15 Letters")
    private String username;

    @NotEmpty(message = "Password Cannot Be Empty")
    @Size(min = 5, message = "Password Cannot Be Less Than 5 Letters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{5,}$", message = "Password must contain at least one uppercase, one lowercase, one digit, one special character, and be at least 5 characters long")
    private String password;


    @Size(min = 3, max = 15, message = "Length of name must be between 3 - 15 characters.")
    @NotEmpty(message = "Name Cannot Be Empty")
    private String name;

    @NotEmpty(message = "Email Cannot Be Empty")
    @Email(message = "Enter A Valid Email")
    private String email;

    @NotEmpty(message = "Error: company name is empty")
    @Size(min = 4 ,max = 30, message = "Error: title length must more then 4 and less then 30")
    private String companyName;

    @NotEmpty(message = "Error: commercialRecord is empty")
    @Pattern(regexp = "^\\d{10}$" , message = "Error: commercialRecord must be exactly 10 digits ")
    private String commercialRecord;

    @NotEmpty(message = "Error: contactEmail is empty")
    @Size(min = 4 ,max = 30, message = "Error: title length must more then 4 and less then 30")
    @Email(message = "Error: contactEmail not formated correctly")
    private String contactEmail;

    @NotEmpty(message = "Error: contactPhone is empty")
    @Pattern(regexp = "^05\\d{8}$" , message = "Error: contactPhone must start with 05 and must length is 10 ")
    private String contactPhone;

}
