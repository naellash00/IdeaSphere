package com.example.ideasphere.Service;


import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.DTOsOut.MonthlyDrawOutDTOs;
import com.example.ideasphere.Model.MonthlyDraw;
import com.example.ideasphere.Model.MyUser;
import com.example.ideasphere.Repository.AuthRepository;
import com.example.ideasphere.Repository.MonthlyDrawRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.DTOsOut.MonthlyDrawOutDTOs;
import com.example.ideasphere.Model.MonthlyDraw;
import com.example.ideasphere.Model.MyUser;
import com.example.ideasphere.Repository.AuthRepository;
import com.example.ideasphere.Repository.MonthlyDrawRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class MonthlyDrawService {


    @Autowired
    private final MonthlyDrawRepository monthlyDrawRepository;

    @Autowired
    private final AuthRepository authRepository;
    public List<MonthlyDraw> findAllMonthlyDraws(Integer userId){
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null){
            throw new ApiException("user not found with id " + userId);
        }
        return monthlyDrawRepository.findAll();
    }


    public void addMonthlyDraw(Integer userId, MonthlyDraw monthlyDraw){
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null){
            throw new ApiException("user not found with id " + userId);
        }

        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate endOfMonth = startOfMonth.plusMonths(1).minusDays(1);

        boolean existingDraw = monthlyDrawRepository.existsByCreatedAtBetween(startOfMonth, endOfMonth);
        if (existingDraw) {
            throw new ApiException("A Monthly Draw already exists for this month.");
        }

        monthlyDrawRepository.save(monthlyDraw);
    }

    public void updateMonthlyDraw(Integer userId, MonthlyDraw monthlyDraw) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) {
            throw new ApiException("User not found with id " + userId);
        }

        MonthlyDraw existingMonthlyDraw = monthlyDrawRepository.findMonthlyDrawById(monthlyDraw.getId());
        if (existingMonthlyDraw == null) {
            throw new ApiException("Monthly Draw not found");
        }

        existingMonthlyDraw.setPrize(monthlyDraw.getPrize());
        existingMonthlyDraw.setDescription(monthlyDraw.getDescription());
        existingMonthlyDraw.setName(monthlyDraw.getName());
        existingMonthlyDraw.setImage(monthlyDraw.getImage());
        existingMonthlyDraw.setEndDate(monthlyDraw.getEndDate());
        existingMonthlyDraw.setRequiredPoints(monthlyDraw.getRequiredPoints());
        existingMonthlyDraw.setIsCompleted(monthlyDraw.getIsCompleted());

        monthlyDrawRepository.save(existingMonthlyDraw);
    }


    public void deleteMonthlyDraw(Integer userId, Integer monthlyDrawID){
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        MonthlyDraw existingMonthlyDraw = monthlyDrawRepository.findMonthlyDrawById(monthlyDrawID);
        if (existingMonthlyDraw == null) {
            throw new ApiException("Monthly Draw not found");
        }

        monthlyDrawRepository.delete(existingMonthlyDraw);
    }


    // Get draws by specific name
    public List<MonthlyDrawOutDTOs> findDrawsByName(Integer userId, String name) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null){
            throw new ApiException("User not found with id " + userId);
        }
        List<MonthlyDraw> monthlyDraws = monthlyDrawRepository.findMonthlyDrawByNameContaining(name);
        List<MonthlyDrawOutDTOs> monthlyDrawOutDTOs = new ArrayList<>();
        for (MonthlyDraw monthlyDraw : monthlyDraws) {
            MonthlyDrawOutDTOs monthlyDrawOutDTOs1 = new MonthlyDrawOutDTOs(
                    monthlyDraw.getId(),
                    monthlyDraw.getName(),monthlyDraw.getDescription(),monthlyDraw.getPrize(),
                    monthlyDraw.getImage(),monthlyDraw.getRequiredPoints(),
                    monthlyDraw.getCreatedAt(),monthlyDraw.getEndDate(),monthlyDraw.getIsCompleted());

            monthlyDrawOutDTOs.add(monthlyDrawOutDTOs1);
        }
        return monthlyDrawOutDTOs;
    }

    // Get draws by specific prize
    public List<MonthlyDrawOutDTOs> findMonthlyDrawByPrize(Integer userId,String prize) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null){
            throw new ApiException("User not found with id " + userId);
        }

        List<MonthlyDraw> monthlyDraws = monthlyDrawRepository.findMonthlyDrawByPrizeContaining(prize);
        List<MonthlyDrawOutDTOs> monthlyDrawOutDTOs = new ArrayList<>();

        for (MonthlyDraw monthlyDraw : monthlyDraws) {
            MonthlyDrawOutDTOs monthlyDrawOutDTOs1 = new MonthlyDrawOutDTOs(
                    monthlyDraw.getId(),
                    monthlyDraw.getName(),monthlyDraw.getDescription(),monthlyDraw.getPrize(),
                    monthlyDraw.getImage(),monthlyDraw.getRequiredPoints(),
                    monthlyDraw.getCreatedAt(),monthlyDraw.getEndDate(),monthlyDraw.getIsCompleted());

            monthlyDrawOutDTOs.add(monthlyDrawOutDTOs1);
        }
        return monthlyDrawOutDTOs;
    }


    // Get Monthly Draws by Date Range
    public List<MonthlyDraw> getDrawsByDateRange(LocalDate startDate, LocalDate endDate) {
        return monthlyDrawRepository.findByEndDateBetween(startDate, endDate);
    }

    // get Eligible Monthly Draws
    public List<MonthlyDrawOutDTOs> getEligibleMonthlyDraws(Integer userId) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) {
            throw new ApiException("User not found with id " + userId);
        }
        List<MonthlyDraw> monthlyDraws = monthlyDrawRepository.findByPointsLessThanEqual(user.getParticipant().getPoints());

        List<MonthlyDrawOutDTOs> monthlyDrawOutDTOs = new ArrayList<>();

        for (MonthlyDraw monthlyDraw : monthlyDraws) {
            MonthlyDrawOutDTOs monthlyDrawOutDTOs1 = new MonthlyDrawOutDTOs(
                    monthlyDraw.getId(),
                    monthlyDraw.getName(),monthlyDraw.getDescription(),monthlyDraw.getPrize(),
                    monthlyDraw.getImage(),monthlyDraw.getRequiredPoints(),
                    monthlyDraw.getCreatedAt(),monthlyDraw.getEndDate(),monthlyDraw.getIsCompleted());

            monthlyDrawOutDTOs.add(monthlyDrawOutDTOs1);
        }
        return monthlyDrawOutDTOs;
    }

    // Get the winner of a specific monthly draw
    public String getMonthlyDrawWinner(Integer userId, Integer drawId) {
        MonthlyDraw monthlyDraw = monthlyDrawRepository.findMonthlyDrawById(drawId);
        MyUser user = authRepository.findMyUserById(monthlyDraw.getMonthlyDrawParticipantWinner().getId());
        return "The Winner is " + user.getName();
    }

}
