package com.example.ideasphere.DTOsOut;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndividualCompetitionDTOOut {

    // Competition fields
    private String title;
    private String description;
    private String votingMethod;
    private String competitionImage;
    private LocalDate voteEndDate;
    private LocalDate endDate;
    private Integer maxParticipants;
    private String status;
    private Set<String> categories;

    private  Double  monetaryReward;
    private String individualOrganizerName;

}
