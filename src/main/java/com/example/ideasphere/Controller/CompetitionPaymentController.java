package com.example.ideasphere.Controller;

import com.example.ideasphere.Model.MyUser;
import com.example.ideasphere.Service.CompetitionPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/competition-payment")
@RequiredArgsConstructor
public class CompetitionPaymentController {

    private final CompetitionPaymentService competitionPaymentService;


    // By Basil
    // get my competition payment
    @GetMapping("/get-my-competition-payment")
    public ResponseEntity getAllMyCompetitionPayment(@AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.ok(competitionPaymentService.getAllMyCompetitionPayment(myUser.getId()));
    }

    // hussam
    @GetMapping("/get-my-competition-payment-individual")
    public ResponseEntity getAllMyCompetitionPayment_Individual(@AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.ok(competitionPaymentService.getAllMyCompetitionPayment_Individual(myUser.getId()));
    }
}
