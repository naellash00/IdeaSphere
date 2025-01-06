package com.example.ideasphere.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

// By Basil
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Error: title is empty")
    @Size(min = 4 ,max = 30, message = "Error: title length must more then 4 and less then 30")
    @Column(nullable = false )
    private String title;

    @NotEmpty(message = "Error: description is empty")
    @Size(min = 30 ,max = 255, message = "Error: description length must more then 30 and less then 255")
    @Column(nullable = false )
    private String description;
    @NotEmpty(message = "Error: votingMethod is empty")
    @Pattern(regexp = "By Organizer|By Public Vote" , message = "Error: voting method only By Organizer|By Public Vote")
    @Column(nullable = false )
    private String votingMethod;

    private String competitionImage;



    private LocalDate voteEndDate;

    @NotNull(message = "Error: endDate is empty")
    @Column(nullable = false )
    private LocalDate endDate;

    @NotNull(message = "Error: maxParticipants is empty")
    @Min(value = 5 , message = "Error: maxParticipants must more or equal 5")
    @Max(value = 500 , message = "Error :maxParticipants the max is 500 ")
    @Positive(message = "Error: maxParticipants must be Positive")
    @Column(nullable = false )
    private Integer maxParticipants;

    @PositiveOrZero(message = "Error: extendNumber must be positive or zero")
    private Integer countExtend = 0;


    @NotEmpty(message = "Error: status is empty")
    @Pattern(regexp = "Ongoing|Completed|Winner Selection in Progress|Under Voting Process|Waiting payment|Competition without submissions|canceled|Vote Tie - Organizer Decision" , message = "Error: status method only Ongoing|Completed|Winner Selection in Progress|Under Voting Process|Waiting payment|Competition without submissions|canceled|Vote Tie - Organizer Decision")
    @Column(nullable = false )
    private String status;
    @Column(nullable = false,updatable = false)
    private LocalDate creationAt= LocalDate.now();

    private Boolean emailSentLatePayment = false;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "competition")
    private CompanyCompetition companyCompetition;
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "competition")
    private IndividualCompetition individualCompetition;

    @ManyToOne
    @JsonIgnore
    private Participant participantWinner;

    @ManyToMany
    @JoinTable(
            name = "competition_category",
            joinColumns = @JoinColumn(name = "competition_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @OneToMany(cascade = CascadeType.ALL ,mappedBy = "competition")
    private Set<Submission> submissions;

    @OneToMany(cascade = CascadeType.ALL ,mappedBy = "competition")
    private Set<Vote> votes;

    @OneToMany(cascade = CascadeType.ALL ,mappedBy = "competition")
    private Set<CompetitionPayment> competitionPayments;


    @OneToMany(cascade = CascadeType.ALL ,mappedBy = "competition")
    private Set<WinnerPayment> winnerPayments;

    @ElementCollection
    @CollectionTable(name = "competition_reviews", joinColumns = @JoinColumn(name = "competition_id"))
    private List<String> reviews;

}
