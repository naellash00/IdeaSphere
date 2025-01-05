package com.example.ideasphere.DTOsIN;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AdminInDTO { //Naelah
    @NotEmpty(message = "Username Cannot Be Empty")
    private String username;

    @NotEmpty(message = "Password Cannot Be Empty")
    private String password;


    @Size(min = 2, max = 20, message = "Length of name must be between 2 and 20 characters.")
    @NotEmpty(message = "name is mandatory")
    private String name;

    @NotEmpty(message = "Email Cannot Be Empty")
    @Email(message = "Enter A Valid Email")
    private String email;

    @Pattern(regexp = "(COMPANY|INDIVIDUAL|PARTICIPANT|ADMIN)")
    private String role;

    @Column(columnDefinition = "timestamp")
    private LocalDateTime createdAt;
}
