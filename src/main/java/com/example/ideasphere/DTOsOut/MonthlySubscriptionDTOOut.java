package com.example.ideasphere.DTOsOut;

import com.example.ideasphere.Model.CompanyOrganizer;
import com.example.ideasphere.Model.IndividualOrganizer;
import com.example.ideasphere.Model.SubscriptionPackage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlySubscriptionDTOOut {


    private LocalDate startDate;
    private LocalDate endDate;

    private Double amount;


    private String subscriberName;


    private String subscriptionPackage;
}
