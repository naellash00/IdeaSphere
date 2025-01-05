package com.example.ideasphere.DTOsIN;

import com.example.ideasphere.Model.Category;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ParticipantInDTO {
    @NotEmpty(message = "Username Cannot Be Empty")
    @Size(min = 5, max =  15, message = "Username Must Be Between 5 - 15 Letters")
    private String username;

    @NotEmpty(message = "Password Cannot Be Empty")
    @Size(min = 5, message = "Password Cannot Be Less Than 5 Letters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{5,}$", message = "Password must contain at least one uppercase, one lowercase, one digit, one special character, and be at least 5 characters long")
    private String password;


    @Size(min = 2, max = 20, message = "Length of name must be between 2 and 20 characters.")
    @NotEmpty(message = "name is mandatory")
    private String name;

    @NotEmpty(message = "Email Cannot Be Empty")
    @Email(message = "Enter A Valid Email")
    private String email;

    @NotEmpty(message = "Bank Number Cannot Be Empty")
    @Pattern(regexp = "^SA[0-9]{2}[0-9]{18}$", message = "Invalid Bank Account Number")
    private String BankAccountNumber;

    @NotNull(message = "Error : category is empty must at least one or more")
    Set<Category> categories;
}
