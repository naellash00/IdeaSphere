package com.example.ideasphere.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private LocalDate paymentDate= LocalDate.now();

    @Pattern(regexp = "^(Pending|Completed|Failed)$", message = "Error: paymentStatus must be one of the following: Pending, Completed, or Failed")
    @Column(nullable = false, length = 50)
    private String paymentStatus;

    @Column(nullable = false,length = 50)
    private String paymentMethod;

    @Column(nullable = false, updatable = false)
    private LocalDate createdAt = LocalDate.now();


    @ManyToOne
    @JsonIgnore
    private Competition competition;


}