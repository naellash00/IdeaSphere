package com.example.ideasphere.Repository;

import com.example.ideasphere.Model.MonthlyDraw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyDrawRepository extends JpaRepository<MonthlyDraw, Integer> {

    MonthlyDraw findMonthlyDrawById(Integer id);

    List<MonthlyDraw> findMonthlyDrawByPrizeContaining(String prize);

    List<MonthlyDraw> findMonthlyDrawByNameContaining(String name);
}
