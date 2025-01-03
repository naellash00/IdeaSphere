package com.example.ideasphere.Controller;

import com.example.ideasphere.ApiResponse.ApiResponse;
import com.example.ideasphere.Model.MonthlyDrawParticipant;
import com.example.ideasphere.Model.MyUser;
import com.example.ideasphere.Service.MonthlyDrawParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/api/v1/monthly-draw-participant")
@RequiredArgsConstructor

public class MonthlyDrawParticipantController {
    @Autowired
    private MonthlyDrawParticipantService monthlyDrawParticipantService;

    @GetMapping("/get")
    public ResponseEntity<List<MonthlyDrawParticipant>> getAllMonthlyDrawParticipants(
            @AuthenticationPrincipal MyUser user) {
        return ResponseEntity.status(200).body(
                monthlyDrawParticipantService.getMonthlyDrawParticipants(user.getId()));
    }

    @PostMapping("/add/{monthlyDrawId}")
    public ResponseEntity<ApiResponse> add(@AuthenticationPrincipal MyUser user,@PathVariable Integer monthlyDrawId) {

        monthlyDrawParticipantService.addMonthlyDrawParticipant(user.getId(),monthlyDrawId);
        return  ResponseEntity.status(200).body(new ApiResponse("Successfully added monthly draw participant"));
    }
}
