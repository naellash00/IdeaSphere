package com.example.ideasphere.Service;


import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.Model.MonthlyDraw;
import com.example.ideasphere.Model.MyUser;
import com.example.ideasphere.Repository.AuthRepository;
import com.example.ideasphere.Repository.MonthlyDrawRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (userId == null){
            throw new ApiException("user notfound");
        }
        return monthlyDrawRepository.findAll();
    }


    public void addMonthlyDraw(Integer userId, MonthlyDraw monthlyDraw){
        MyUser user = authRepository.findMyUserById(userId);
        if (userId == null){
            throw new ApiException("user notfound");
        }
        monthlyDrawRepository.save(monthlyDraw);
    }

    public void updateMonthlyDraw(Integer userId, MonthlyDraw monthlyDraw) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
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


}
