package com.example.ideasphere.DTOsOut;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AchievementOutDTO {
    private String competitionTitle;

    private String votingMethod;

    private SubmissionOutDTO mySubmission;

    private String rewardType;

    private Double monetaryReward;
}
