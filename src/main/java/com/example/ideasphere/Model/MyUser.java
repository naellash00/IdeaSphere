package com.example.ideasphere.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MyUser implements UserDetails { // Naelah
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Username Cannot Be Empty")
    @Size(min = 5, max =  15, message = "Username Must Be Between 5 - 15 Letters")
    @Column(columnDefinition = "varchar(15) not null unique")
    private String username;

    @NotEmpty(message = "Password Cannot Be Empty")
    @Size(min = 5, message = "Password Cannot Be Less Than 5 Letters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{5,}$", message = "Password must contain at least one uppercase, one lowercase, one digit, one special character, and be at least 5 characters long")
    @Column(columnDefinition = "varchar(20) not null")
    private String password;


    @Size(min = 3, max = 15, message = "Length of name must be between 3 - 15 characters.")
    @NotEmpty(message = "Name Cannot Be Empty")
    @Column(columnDefinition = "varchar(15) not null")
    private String name;

    @NotEmpty(message = "Email Cannot Be Empty")
    @Email(message = "Enter A Valid Email")
    @Column(columnDefinition = "varchar(30) not null unique")
    private String email;

    @Pattern(regexp = "(COMPANY|INDIVIDUAL|PARTICIPANT|ADMIN)")
    @Column(columnDefinition = "varchar(12)")
    private String role;

    @Column(columnDefinition = "timestamp")
    private LocalDateTime createdAt;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Participant participant;

    // hussam
    @OneToOne(mappedBy = "myUser", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private IndividualOrganizer individualOrganizer;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "myUser")
    private CompanyOrganizer companyOrganizer;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
