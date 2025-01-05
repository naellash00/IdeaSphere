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

    @GetMapping("/get-all-monthly-draw-participants")
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

   /* @PutMapping("/assign-winner/{monthlyDrawId}")
    public ResponseEntity<ApiResponse> assignWinner(@AuthenticationPrincipal MyUser user,
                                                    @PathVariable Integer monthlyDrawId) {
        monthlyDrawParticipantService.assignWinner(user.getId(),monthlyDrawId);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully assigned monthly draw participant"));
    }*/

    // get all participants assign in Monthly Draw
    @GetMapping("/participants/{drawId}")
    public ResponseEntity<List<String>> getParticipantsByDraw(@PathVariable Integer drawId) {

        List<String> participants = monthlyDrawParticipantService.getParticipantsByDraw(drawId);
        return ResponseEntity.ok(participants);

    }
}
