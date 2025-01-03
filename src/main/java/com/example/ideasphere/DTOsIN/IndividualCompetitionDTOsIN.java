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

    private String title;

    private String description;


    private String votingMethod;

    private String competitionImage;

    private LocalDate voteStartDate;

    private LocalDate voteEndDate;

    private LocalDate endDate;

    private Integer maxParticipants;

    private String status;

    private Integer monetaryReward;

    private Set<Category> categories;
}
