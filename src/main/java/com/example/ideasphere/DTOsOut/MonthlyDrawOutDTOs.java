package com.example.ideasphere.DTOsOut;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
public class MonthlyDrawOutDTOs {

    private Integer id;

    private String name;

    private String description;

    private String prize;

    private String image;

    private Integer requiredPoints;

    private LocalDate createdAt = LocalDate.now();

    private LocalDate endDate;

    private Boolean isCompleted = false;
}
