package com.example.ideasphere.DTOsIN;

import com.example.ideasphere.Model.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyCompetitionUpdateDTOIn {

    private Integer id;

    @NotEmpty(message = "Error: title is empty")
    @Size(min = 4 ,max = 30, message = "Error: title length must more then 4 and less then 30")
    private String title;

    @NotEmpty(message = "Error: description is empty")
    @Size(min = 30 ,max = 255, message = "Error: description length must more then 30 and less then 255")
    private String description;

    private String competitionImage;

    @NotNull(message = "Error : category is empty must at least one or more")
    Set<Category> categories;
}
