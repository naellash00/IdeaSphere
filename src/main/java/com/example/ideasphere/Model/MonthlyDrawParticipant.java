package com.example.ideasphere.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class MonthlyDrawParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "pointsUsed is mandatory")
    @Column(nullable = false)
    private Integer pointsUsed;

    @Column(updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDate signUpDate;

    @ManyToOne
    @JsonIgnore
    private MonthlyDraw monthlyDraw;

   @ManyToOne
    private Participant participant ;


}
