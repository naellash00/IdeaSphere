package com.example.ideasphere.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
// By Basil
public class CompanyCompetition {
    @Id
    private Integer id;

    @NotEmpty(message = "Error: rewardType is empty")
    @Pattern(regexp = "Financial&Interview|Financial|Interview" , message = "Error: status must is Financial&Interview|Financial|Interview")
    @Column(nullable = false )
    private String rewardType;


    private Double monetaryReward;


    @OneToOne
    @MapsId
    @JsonIgnore
    private Competition competition;

    @ManyToOne
    @JsonIgnore
    private CompanyOrganizer companyOrganizer;


}
