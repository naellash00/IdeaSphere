package com.example.ideasphere.DTOsOut;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyCompetitionDTOOut {

    // Competition fields
    private String title;
    private String description;
    private String votingMethod;
    private String competitionImage;
    private LocalDate voteEndDate;
    private LocalDate endDate;
    private Integer maxParticipants;
    private Integer countExtend ;
    private String status;
    private Set<String> categories;
    private String winnerName;

    // CompanyCompetition fields
    private String rewardType;
    private Double monetaryReward;
    private String companyOrganizerName;
}
