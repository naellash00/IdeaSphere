package com.example.ideasphere.Model;

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
public class SubscriptionPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotBlank(message = "Error: Package name must not be blank")
    @Size(min = 3, max = 50, message = "Error: Package name must be between 3 and 50 characters")
    private String packageName;


    @NotNull(message = "Error: Count month must not be null")
    @Min(value = 1, message = "Error: Count month must be at least 1")
    @Positive(message = "Error: countMonth must be positive")
    private Integer countMonth;


    @NotNull(message = "Error: Price must not be null")
    @Positive(message = "Error: price must be positive")
    private Double price;

    @NotBlank(message = "Error: status must not be blank")
    @Pattern(regexp = "Active|Not Active" , message = "Error: status must is Active or Not Active")
    private String status = "Active";

    @OneToMany(mappedBy = "subscriptionPackage", cascade = CascadeType.ALL)
    private Set<MonthlySubscription> monthlySubscriptions;
}
