package com.example.ideasphere.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class MonthlyDraw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Name is mandatory")
    @Size(min = 2, max = 12,message = "name character must be between 2  to 10")
    @Column(nullable = false)
    private String name;

    @NotEmpty(message = "description is mandatory")
    @Column(nullable = false,  length = 500)
    private String description;

    @NotEmpty(message = "Prize is required")
    @Size(min = 2, max = 12,message = "prize character must be between 2  to 10")
    @Column(nullable = false)
    private String prize;

    @NotEmpty(message = "image is mandatory")
    @Column(nullable = false)
    private String image;

    @NotNull(message = "requiredPoints is mandatory")
    @Column(nullable = false)
    private Integer requiredPoints;

    @Column(updatable = false)
    private LocalDate createdAt = LocalDate.now();

    @NotNull(message = "requiredPoints is mandatory")
    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = true)
    private Boolean isCompleted = false;

    @ManyToOne
    @JsonIgnore
    private Participant monthlyDrawParticipantWinner;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "monthlyDraw")
    private Set<MonthlyDrawParticipant> monthlyDrawParticipant;

}
