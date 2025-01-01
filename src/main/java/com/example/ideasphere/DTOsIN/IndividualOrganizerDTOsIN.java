package com.example.ideasphere.DTOsIN;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class IndividualOrganizerDTOsIN {

    private Integer individualOrganizerId;

    @Size(min = 2, max = 20, message = "Length of name must be between 2 and 20 characters.")
    @NotEmpty(message = "name is mandatory")
    private String name;

    @NotEmpty(message = "Username Cannot Be Empty")
    @Size(min = 5, max =  15, message = "Username Must Be Between 5 - 15 Letters")
    @Column(columnDefinition = "varchar(15) not null unique")
    private String username;

    @NotEmpty(message = "Password Cannot Be Empty")
    @Size(min = 5, message = "Password Cannot Be Less Than 5 Letters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{5,}$", message = "Password must contain at least one uppercase, one lowercase, one digit, one special character, and be at least 5 characters long")
    @Column(columnDefinition = "varchar(20) not null")
    private String password;

    @NotEmpty(message = "Email Cannot Be Empty")
    @Email(message = "Enter A Valid Email")
    @Column(columnDefinition = "varchar(30) not null unique")
    private String email;

    @Pattern(regexp = "('company'|'individual'|'participant'|'admin')")
    @Column(columnDefinition = "varchar(12)")
    private String role;

    @Column(columnDefinition = "timestamp")
    private LocalDateTime createdAt;

    @NotEmpty(message = "phone Number is mandatory")
    @Size(min = 10, max = 10, message = "phoneNumber")
    @Pattern(regexp = "^05\\d{8}$")
    @Positive(message = "MonetaryReward must be Positive number")
    @Column(nullable = false, unique = true)
    private String phoneNumber;


}
