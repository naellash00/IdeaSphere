package com.example.ideasphere.DTOsOut;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndividualOrganizerDTOOut {

    private String username;
    private String name;
    private String email;
    private String phoneNumber;
}
