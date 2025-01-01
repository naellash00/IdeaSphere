package com.example.ideasphere.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Participant { // Naelah
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Bank Number Cannot Be Empty")
    @Pattern(regexp = "^SA[0-9]{2}[0-9]{18}$", message = "Invalid Bank Account Number")
    private String BankAccountNumber;

    @Column(columnDefinition = "int")
    private Integer points;

    @OneToOne
    @MapsId
    @JsonIgnore
    private MyUser user;



    @OneToMany(cascade = CascadeType.ALL ,mappedBy = "participantWinner")
    private Set<Competition> competitionsWinner;


    @OneToMany(cascade = CascadeType.ALL ,mappedBy = "monthlyDrawParticipantWinner")
    private Set<MonthlyDraw> monthlyDraws;

    @ManyToMany
    @JoinTable(
            name = "participant_category",
            joinColumns = @JoinColumn(name = "participant_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;
}
