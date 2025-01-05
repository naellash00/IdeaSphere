package com.example.ideasphere.DTOsOut;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndividualOrganizerDTOOut {
    private Integer id;
    private String username;
    private String name;
    private String email;
    private String role;
    private String phoneNumber;
}
