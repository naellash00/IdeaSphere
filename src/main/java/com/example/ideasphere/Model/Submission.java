package com.example.ideasphere.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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

    private LocalDateTime submittedAt;
    //    Participant(Participant     many to one)
    @ManyToOne
    @JsonIgnore
    private Participant participant;
    //    Competition  (Competition   many to one)
    //    List<> Vote  (one to many Vote )

    @ManyToOne
    @JsonIgnore
    private Competition competition;
}
