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


    @GetMapping("/get-all-competition")
    public ResponseEntity getAllCompetition() {
        return ResponseEntity.status(200).body(competitionService.getAllCompetition());
    }

    //Naelah
    @GetMapping("/get/recommend/competitions")
    public ResponseEntity recommendCompetitions(@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(200).body(competitionService.recommendCompetitions(myUser.getId()));
    }

    //Naelah
    @PutMapping("/add-review/{competition_id}")
    public ResponseEntity addReviewOnCompetition(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer competition_id, @RequestParam String review){
        competitionService.addReviewOnCompetition(myUser.getId(), competition_id, review);
        return ResponseEntity.status(200).body(new ApiResponse("Your Review Is Added Successfully"));
    }

}
