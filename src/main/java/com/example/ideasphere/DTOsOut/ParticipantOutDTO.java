package com.example.ideasphere.DTOsOut;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ParticipantOutDTO { //Naelah
    private String username;

    private String name;

    private String email;

    private List<CategoryOutDTO> categories;
}
