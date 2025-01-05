package com.example.ideasphere.Model;

import jakarta.persistence.*;
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
public class SubscriptionPackages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String packageName;

    private Double countMonth;

    private Double price;

    @OneToMany(mappedBy = "subscriptionPackages", cascade = CascadeType.ALL)
    private Set<MonthlySubscription> monthlySubscriptions;
}
