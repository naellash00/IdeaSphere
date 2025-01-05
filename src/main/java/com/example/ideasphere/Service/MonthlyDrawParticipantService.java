package com.example.ideasphere.Service;

import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.Model.MonthlyDraw;
import com.example.ideasphere.Model.MonthlyDrawParticipant;
import com.example.ideasphere.Model.MyUser;
import com.example.ideasphere.Model.Participant;
import com.example.ideasphere.Repository.AuthRepository;
import com.example.ideasphere.Repository.MonthlyDrawParticipantRepository;
import com.example.ideasphere.Repository.MonthlyDrawRepository;
import com.example.ideasphere.Repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MonthlyDrawParticipantService {

    @Autowired
    private final MonthlyDrawParticipantRepository monthlyDrawParticipantRepository;
    @Autowired
    private final MonthlyDrawRepository monthlyDrawRepository;
    @Autowired
    private final ParticipantRepository participantRepository;
    @Autowired
    private final AuthRepository authRepository;

    public List<MonthlyDrawParticipant> getMonthlyDrawParticipants(Integer userId) {
        if (userId == null) {
            throw new ApiException("userId cannot be null");
        }
        return monthlyDrawParticipantRepository.findAll();
    }

    public void addMonthlyDrawParticipant(Integer userId, Integer monthlyDrawId) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) {
            throw new ApiException("UserId cannot be null");
        }

        MonthlyDraw monthlyDraw = monthlyDrawRepository.findMonthlyDrawById(monthlyDrawId);

        if (monthlyDraw  == null ) {
            throw new ApiException("MonthlyDraw must be provided with a valid ID.");
        }

        if (user.getParticipant().getPoints() < monthlyDraw.getRequiredPoints()){
            throw new ApiException("You have not enough required points to add monthly draw.");
        }

        MonthlyDrawParticipant monthlyDrawParticipant = new MonthlyDrawParticipant();
        monthlyDrawParticipant.setMonthlyDraw(monthlyDraw);
        monthlyDrawParticipant.setParticipant(user.getParticipant());
        monthlyDrawParticipant.setPointsUsed(monthlyDraw.getRequiredPoints());
        monthlyDrawParticipant.setSignUpDate(LocalDate.now());
        monthlyDrawParticipantRepository.save(monthlyDrawParticipant);

        Participant participant = user.getParticipant();
        participant.setPoints(participant.getPoints() - monthlyDraw.getRequiredPoints());
        participantRepository.save(participant);
    }

/*
    public void assignWinner(Integer userId, Integer monthlyDrawId) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) {
            throw new ApiException("User ID must be valid.");
        }

        MonthlyDraw monthlyDraw = monthlyDrawRepository.findMonthlyDrawById(monthlyDrawId);
        if (monthlyDraw == null) {
            throw new ApiException("MonthlyDraw must be provided with a valid ID.");
        }

        if (monthlyDraw.getIsCompleted().equals(true)){
            throw new ApiException("You have already completed the monthly draw.");
        }

        List<MonthlyDrawParticipant> participants = monthlyDrawParticipantRepository.findByMonthlyDrawId(monthlyDrawId);
        if (participants.isEmpty()) {
            throw new ApiException("No participants found in the Monthly Draw.");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(participants.size());
        MonthlyDrawParticipant randomParticipant = participants.get(randomIndex);

        monthlyDraw.setMonthlyDrawParticipantWinner(randomParticipant.getParticipant());
        monthlyDraw.setIsCompleted(true);
        monthlyDrawRepository.save(monthlyDraw);
    }*/

    public List<String> getParticipantsByDraw(Integer drawId) {

        if (!monthlyDrawRepository.existsById(drawId)) {
            throw new ApiException("MonthlyDraw not found with id " + drawId);
        }
        return monthlyDrawParticipantRepository.findParticipantNamesByMonthlyDrawId(drawId);
    }
}
