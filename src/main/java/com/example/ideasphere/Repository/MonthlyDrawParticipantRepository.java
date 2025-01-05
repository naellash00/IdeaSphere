package com.example.ideasphere.Repository;

import com.example.ideasphere.Model.MonthlyDrawParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ideasphere.Model.MonthlyDrawParticipant;
import com.example.ideasphere.Model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface MonthlyDrawParticipantRepository extends JpaRepository<MonthlyDrawParticipant, Integer> {
    List<MonthlyDrawParticipant> findMonthlyDrawParticipantById(Integer id);

    List<MonthlyDrawParticipant> findByMonthlyDrawId(Integer monthlyDrawId);

    @Query("SELECT mdp.participant.user.username FROM MonthlyDrawParticipant mdp WHERE mdp.monthlyDraw.id = :drawId")
    List<String> findParticipantNamesByMonthlyDrawId(@Param("drawId") Integer drawId);
}
