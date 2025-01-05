package com.example.ideasphere.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "PDF File Cannot Be Empty")
    @Column(columnDefinition = "varchar(100) not null")
    private String PDFFile;

    @NotEmpty(message = "File Cannot Be Empty")
    @Column(columnDefinition = "varchar(100) not null")
    private String fileURL;

    @Column(columnDefinition = "varchar(100)")
    private String secondFileURL;

    @Column(columnDefinition = "varchar(100)")
    private String thirdFileURL;

    @Column(columnDefinition = "text not null")
    private String description;


    private Boolean winnerEqualedVotes = false;

    @Column(columnDefinition = "timestamp")
    private LocalDateTime submittedAt;

    @ManyToOne
    @JsonIgnore
    private Participant participant;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "submission")
    private Set<Vote> votes;

    @ManyToOne
    @JsonIgnore
    private Competition competition;

    @Column(columnDefinition = "text")
    private String organizerFeedback;

    @Column(columnDefinition = "varchar(9)")
    @Pattern(regexp = "(Pending|Accepted|Rejected)")
    private String feedbackRequestStatus;
}
