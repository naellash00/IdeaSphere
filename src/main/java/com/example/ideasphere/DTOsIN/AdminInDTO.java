package com.example.ideasphere.DTOsIN;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminInDTO {
    @NotEmpty(message = "Username Cannot Be Empty")
    private String username;

    @NotEmpty(message = "Password Cannot Be Empty")
    private String password;
}
