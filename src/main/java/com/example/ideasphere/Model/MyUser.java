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


    @Column(columnDefinition = "varchar(15) not null unique")
    private String username;

    @Column(columnDefinition = "varchar(200) not null")
    private String password;


    @Column(columnDefinition = "varchar(15) not null")
    private String name;


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
