package com.example.ideasphere.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WinnerPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JsonIgnore
    private Participant participantWinner;

    @ManyToOne
    @JsonIgnore
    private Competition competition;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private LocalDateTime transferDate;

    @Column(nullable = false, length = 50)
    private String transferStatus; // e.g., Pending, Completed, Failed

    @Column(length = 50)
    private String transferMethod; // e.g., Bank Transfer, PayPal

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;


}