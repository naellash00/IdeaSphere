package com.example.ideasphere.DTOsOut;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SubmissionOutDTO {
    private String pdfFile;

    private String fileURL;

    private String secondFileURL;

    private String thirdFileURL;

    private String description;

    private LocalDateTime submittedAt;

    private String competitionTitle;
    // could be number of votes
}
