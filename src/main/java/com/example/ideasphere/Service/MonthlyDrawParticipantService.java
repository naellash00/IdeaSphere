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

import java.util.List;

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
        monthlyDrawParticipantRepository.save(monthlyDrawParticipant);

        Participant participant = user.getParticipant();
        participant.setPoints(participant.getPoints() - monthlyDraw.getRequiredPoints());
        participantRepository.save(participant);
    }
}
