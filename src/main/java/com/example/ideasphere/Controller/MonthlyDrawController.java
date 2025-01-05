package com.example.ideasphere.Controller;

import com.example.ideasphere.ApiResponse.ApiResponse;
import com.example.ideasphere.DTOsOut.MonthlyDrawOutDTOs;
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


    @GetMapping("/get")
    public ResponseEntity<List<MonthlyDraw>> getAllMonthlyDraws(@AuthenticationPrincipal MyUser user){
        return ResponseEntity.ok(monthlyDrawService.findAllMonthlyDraws(user.getId()));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> add(
            @AuthenticationPrincipal MyUser user,
            @RequestBody @Valid MonthlyDraw monthlyDraw){
        monthlyDrawService.addMonthlyDraw(user.getId(), monthlyDraw);
        return  ResponseEntity.status(200).body(new ApiResponse("Successfully added Monthly draw."));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> update(
            @AuthenticationPrincipal MyUser user,
            @RequestBody @Valid MonthlyDraw monthlyDraw){
        monthlyDrawService.updateMonthlyDraw(user.getId(), monthlyDraw);
        return  ResponseEntity.status(200).body(new ApiResponse("Successfully updated Monthly draw."));
    }


    // Get draws by prize or name
    @GetMapping("/findDrawsByName/{name}")
    public List<MonthlyDrawOutDTOs> getDrawsByPrizeOrName(@AuthenticationPrincipal MyUser myUser,
                                                          @PathVariable  String name) {
        return monthlyDrawService.findDrawsByName(myUser.getId(), name);
    }

    // Get draws by prize
    @GetMapping("/findMonthlyDrawByPrize/{prize}")
    public List<MonthlyDrawOutDTOs> getMonthlyDrawByPrize(@AuthenticationPrincipal MyUser myUser,
                                                          @PathVariable String prize) {
        return monthlyDrawService.findMonthlyDrawByPrize(myUser.getId(),prize);
    }

    // Get the winner of a specific monthly draw
    @GetMapping("/findMonthlyDrawWinner/{drawId}")
    public String findMonthlyDrawWinner(@AuthenticationPrincipal MyUser myUser,
                                        @PathVariable Integer drawId) {
        return monthlyDrawService.getMonthlyDrawWinner(myUser.getId(),drawId);
    }

    @GetMapping("/eligible")
    public List<MonthlyDrawOutDTOs> getEligibleMonthlyDraws(
            @AuthenticationPrincipal MyUser user) {
        return monthlyDrawService.getEligibleMonthlyDraws(user.getId());
    }

}
