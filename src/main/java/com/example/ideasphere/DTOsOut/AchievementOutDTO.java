package com.example.ideasphere.DTOsOut;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AchievementOutDTO {
    private String competitionTitle;

    private String votingMethod;

    // private WinnerSubmissionOutDTO mySubmission;
    private String mySubmissionFile;

    //private String fileURL;

    private String mySubmissionDescription;

    private LocalDateTime submittedAt;
//
    // private String rewardType;

    // private Double monetaryReward;
}
