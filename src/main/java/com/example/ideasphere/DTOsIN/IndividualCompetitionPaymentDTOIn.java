package com.example.ideasphere.DTOsIN;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndividualCompetitionPaymentDTOIn {

    private Integer individualCompetitionId;

    @Pattern(regexp = "^(Meda|Visa)$", message = "Error: paymentMethod must be one of the following: Meda, Visa")
    private String paymentMethod;

    @NotEmpty(message = "Error: cardNumber is empty")
    @Pattern(regexp = "^\\d{16}$", message = "Error: cardNumber must be 16 digits long")
    private String cardNumber;

    @NotEmpty(message = "Error: cardName is empty")
    @Size(max = 30, message = "Error: cardName cannot exceed 30 characters")
    private String cardName;

    @NotNull(message = "Error: CCV is empty")
    @Digits(integer = 3, fraction = 0, message = "Error: CCV must be exactly 3 digits")
    private Integer CCV;

    @NotEmpty(message = "Error: cardDate is empty")
    @Pattern(regexp = "^(0[1-9]|1[0-2])/\\d{2}$", message = "Error: cardDate must be in the format MM/YY")
    private String cardDate;
}
