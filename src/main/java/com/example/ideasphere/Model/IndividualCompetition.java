package com.example.ideasphere.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class IndividualCompetition {
    @Id
    private Integer id;

    @NotNull(message = "MonetaryReward is mandatory")
    @Positive(message = "MonetaryReward must be a positive number")
    private Integer monetaryReward;

    @OneToOne
    @MapsId
    @JsonIgnore
    private Competition competition;

    @ManyToOne
    @JsonIgnore
    private  IndividualOrganizer individualOrganizer;
}
