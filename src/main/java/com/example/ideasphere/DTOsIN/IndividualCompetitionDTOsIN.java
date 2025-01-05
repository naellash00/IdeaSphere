package com.example.ideasphere.DTOsIN;

import com.example.ideasphere.Model.Category;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class IndividualCompetitionDTOsIN {

    private Integer individualCompetitionId;

    @NotEmpty(message = "Error: title is empty")
    @Size(min = 4 ,max = 30, message = "Error: title length must more then 4 and less then 30")
    private String title;

    @NotEmpty(message = "Error: description is empty")
    @Size(min = 30 ,max = 255, message = "Error: description length must more then 30 and less then 255")
    private String description;

    private String votingMethod;

    private String competitionImage;

    private LocalDate voteEndDate;

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
    @NotNull(message = "Error : category is empty must at least one or more")
    private Set<Category> categories;
}
