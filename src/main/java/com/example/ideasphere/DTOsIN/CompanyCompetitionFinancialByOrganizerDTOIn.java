package com.example.ideasphere.DTOsIN;

import com.example.ideasphere.Model.Category;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyCompetitionFinancialByOrganizerDTOIn {
    private Integer id;

    @NotEmpty(message = "Error: title is empty")
    @Size(min = 4 ,max = 30, message = "Error: title length must more then 4 and less then 30")
    private String title;

    @NotEmpty(message = "Error: description is empty")
    @Size(min = 30 ,max = 255, message = "Error: description length must more then 30 and less then 255")
    private String description;

    private String competitionImage;

    @NotNull(message = "Error : category is empty must at least one or more")
    Set<Category> categories;


    @NotNull(message = "Error: endDate is empty")
    @Future(message = "Error: endDate must be in Future")
    private LocalDate endDate;

    @NotNull(message = "Error: maxParticipants is empty")
    @Max(value = 500 , message = "Error :maxParticipants the max is 500 ")
    @Positive(message = "Error: maxParticipants must be Positive")
    @Min(value = 5 , message = "Error: maxParticipants must more or equal 5")
    private Integer maxParticipants;

    @NotNull(message = "Error: monetaryReward is empty")
    @Positive(message = "Error: must be positive")
    @Min(value = 50 , message = "Error: minimum prize is 50 SAR ")
    private Double monetaryReward;
}
