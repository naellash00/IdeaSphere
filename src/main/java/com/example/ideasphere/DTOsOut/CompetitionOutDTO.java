package com.example.ideasphere.DTOsOut;

import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

// for reccomend competition out dto
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionOutDTO { //Naelah
    //private Integer id;
    private String title;
    private String description;
    private String votingMethod;
    private LocalDate endDate;
    private Integer maxParticipants;
    // private String status;
}
