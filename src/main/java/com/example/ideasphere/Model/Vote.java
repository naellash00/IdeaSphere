package com.example.ideasphere.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime voteDate;

    @ManyToOne
    @JsonIgnore
    private  Submission submission;

    //    Competition  (Competition   many to one)
    //    Voter (Participant     many to one) - voter
    @ManyToOne
    @JsonIgnore
    private Participant voter;

    @ManyToOne
    @JsonIgnore
    private Competition competition;
}
















