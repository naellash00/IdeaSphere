package com.example.ideasphere.Repository;

import com.example.ideasphere.Model.MonthlySubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlySubscriptionRepository extends JpaRepository<MonthlySubscription,Integer> {

    MonthlySubscription findMonthlySubscriptionById(Integer id);
}
