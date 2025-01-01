package com.example.ideasphere.Repository;

import com.example.ideasphere.Model.MonthlyDrawParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface MonthlyDrawParticipantRepository extends JpaRepository<MonthlyDrawParticipant, Integer> {
}
