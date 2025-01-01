package com.example.ideasphere.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
// By Basil
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Error: categoryName is empty")
    @Column(nullable = false )
    private String categoryName ;

    @ManyToMany(mappedBy = "categories")
    private Set<Competition> competitions;

    @ManyToMany(mappedBy = "categories")
    private Set<Participant> participants;
}
