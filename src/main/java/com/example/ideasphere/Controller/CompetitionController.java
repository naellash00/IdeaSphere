package com.example.ideasphere.Controller;

import com.example.ideasphere.Service.CompetitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/competition")
@RequiredArgsConstructor
public class CompetitionController {


    private final CompetitionService competitionService;


}
