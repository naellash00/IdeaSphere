package com.example.ideasphere.Controller;

import com.example.ideasphere.ApiResponse.ApiResponse;
import com.example.ideasphere.Model.MonthlyDraw;
import com.example.ideasphere.Model.MyUser;
import com.example.ideasphere.Service.MonthlyDrawService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/api/v1/monthlyDraw")
@RequiredArgsConstructor

public class MonthlyDrawController {


    @Autowired
    private final MonthlyDrawService monthlyDrawService;


    @GetMapping("/get-All-MonthlyDraws")
    public ResponseEntity<List<MonthlyDraw>> getAllMonthlyDraws(@AuthenticationPrincipal MyUser user){
      return ResponseEntity.ok(monthlyDrawService.findAllMonthlyDraws(user.getId()));
    }

    @PostMapping("/add-MonthlyDraws")
    public ResponseEntity<ApiResponse> add(
            @AuthenticationPrincipal MyUser user,
            @RequestBody @Valid MonthlyDraw monthlyDraw){
        monthlyDrawService.addMonthlyDraw(user.getId(), monthlyDraw);
     return  ResponseEntity.status(200).body(new ApiResponse("Successfully added Monthly draw."));
    }

    @PutMapping("/update-MonthlyDraws")
    public ResponseEntity<ApiResponse> update(
            @AuthenticationPrincipal MyUser user,
            @RequestBody @Valid MonthlyDraw monthlyDraw){
        monthlyDrawService.updateMonthlyDraw(user.getId(), monthlyDraw);
     return  ResponseEntity.status(200).body(new ApiResponse("Successfully updated Monthly draw."));
    }

    @DeleteMapping("/delete-MonthlyDraws/{monthlyDrawId}")
    public ResponseEntity<ApiResponse> delete(@AuthenticationPrincipal MyUser user,@PathVariable Integer monthlyDrawId){
        monthlyDrawService.deleteMonthlyDraw(user.getId(), monthlyDrawId);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully deleted Monthly draw."));
    }



}
