package com.example.ideasphere.DTOsOut;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantOutDTO { //Naelah
    private String username;

    private String name;

    private String email;

    private List<CategoryOutDTO> categories;
    private Integer points = 0;
}
