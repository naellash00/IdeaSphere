package com.example.ideasphere.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
public class CompanyOrganizer {

    @Id
    private Integer id;

    @NotEmpty(message = "Error: company name is empty")
    @Size(min = 4 ,max = 30, message = "Error: title length must more then 4 and less then 30")
    @Column(nullable = false , unique = true)
    private String companyName;

    @NotEmpty(message = "Error: commercialRecord is empty")
    @Pattern(regexp = "^\\d{10}$" , message = "Error: commercialRecord must be exactly 10 digits ")
    @Column(nullable = false , unique = true)
    private String commercialRecord;

    @NotEmpty(message = "Error: contactEmail is empty")
    @Size(min = 4 ,max = 30, message = "Error: title length must more then 4 and less then 30")
    @Email(message = "Error: contactEmail not formated correctly")
    @Column(nullable = false , unique = true)
    private String contactEmail;

    @NotEmpty(message = "Error: contactPhone is empty")
    @Pattern(regexp = "^05\\d{8}$" , message = "Error: contactPhone must start with 05 and must length is 10 ")
    @Column(nullable = false , unique = true)
    private String contactPhone;

    @NotEmpty(message = "Error: contactPhone is empty")
    @Pattern(regexp = "Not Active|Active" , message = "Error: status must is Not Active or Active")
    @Column(nullable = false )
    private String status;

    @OneToOne
    @MapsId
    @JsonIgnore
    private MyUser myUser;

    @OneToMany(cascade = CascadeType.ALL ,mappedBy = "companyOrganizer")
    private Set<CompanyCompetition> companyCompetitions;

    @OneToMany(mappedBy = "companyOrganizer", cascade = CascadeType.ALL)
    private Set<MonthlySubscription> monthlySubscriptions;
}
