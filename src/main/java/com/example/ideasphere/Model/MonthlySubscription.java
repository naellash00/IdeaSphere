package com.example.ideasphere.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MonthlySubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate startDate;
    private LocalDate endDate;

    private Double amount;

    @ManyToOne
    @JsonIgnore
    private IndividualOrganizer individualOrganizer;

    @ManyToOne
    @JsonIgnore
    private CompanyOrganizer companyOrganizer;

    @ManyToOne
    @JsonIgnore
    private SubscriptionPackage subscriptionPackage;
}
