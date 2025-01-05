package com.example.ideasphere.Repository;

import com.example.ideasphere.Model.MonthlyDraw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.example.ideasphere.Model.MonthlyDraw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MonthlyDrawRepository extends JpaRepository<MonthlyDraw, Integer> {

    MonthlyDraw findMonthlyDrawById(Integer id);

    List<MonthlyDraw> findMonthlyDrawByPrizeContaining(String prize);

    List<MonthlyDraw> findMonthlyDrawByNameContaining(String name);

    boolean existsByCreatedAtBetween(LocalDate startOfMonth, LocalDate endOfMonth);

    List<MonthlyDraw> findByEndDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT m FROM MonthlyDraw m WHERE m.requiredPoints <= :points and m.status= 'Ongoing' ")
    List<MonthlyDraw> findByPointsLessThanEqual(@Param("points") Integer points);

    List<MonthlyDraw> findMonthlyDrawByEndDateBeforeAndStatus(LocalDate date , String status);
}
