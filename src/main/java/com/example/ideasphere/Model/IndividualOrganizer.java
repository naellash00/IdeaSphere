package com.example.ideasphere.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class IndividualOrganizer {

    @Id
    private Integer id;

    @NotEmpty(message = "phone Number is mandatory")
    @Size(min = 10, max = 10, message = "phoneNumber")
    @Pattern(regexp = "^05\\d{8}$")
    @Positive(message = "MonetaryReward must be Positive number")
    @Column(nullable = false, unique = true)
    private String phoneNumber;

     @OneToOne
     @MapsId
     @JsonIgnore
     private MyUser myUser;


    @OneToMany(mappedBy = "individualOrganizer", cascade = CascadeType.ALL)
    private Set<IndividualCompetition> individualCompetitions;





}
