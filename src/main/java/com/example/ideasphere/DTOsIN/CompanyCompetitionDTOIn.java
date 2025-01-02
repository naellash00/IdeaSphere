package com.example.ideasphere.DTOsIN;

import com.example.ideasphere.Model.Category;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyCompetitionDTOIn {

    private Integer id;

    @NotEmpty(message = "Error: title is empty")
    @Size(min = 4 ,max = 30, message = "Error: title length must more then 4 and less then 30")
    private String title;

    @NotEmpty(message = "Error: description is empty")
    @Size(min = 30 ,max = 255, message = "Error: description length must more then 30 and less then 255")
    private String description;

    @NotEmpty(message = "Error: votingMethod is empty")
    @Pattern(regexp = "By Organizer|By Public Vote" , message = "Error: voting method only By Organizer|By Public Vote")
    private String votingMethod;

    private String competitionImage;

    Set<Category> categories;


    @NotNull(message = "Error: voteStartDate is empty")
    @Future(message = "Error: voteStartDate must be in Future")
    private LocalDate voteStartDate;

    @NotNull(message = "Error: voteEndDate is empty")
    @Future(message = "Error: voteEndDate must be in Future")
    private LocalDate voteEndDate;

    @NotNull(message = "Error: endDate is empty")
    @Future(message = "Error: endDate must be in Future")
    private LocalDate endDate;

    @NotNull(message = "Error: maxParticipants is empty")
    @Min(value = 5 , message = "Error: maxParticipants must more or equal 5")
    private Integer maxParticipants;


}
