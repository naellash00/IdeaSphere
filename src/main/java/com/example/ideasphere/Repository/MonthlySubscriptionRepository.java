package com.example.ideasphere.Repository;

import com.example.ideasphere.Model.MonthlySubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MonthlySubscriptionRepository extends JpaRepository<MonthlySubscription,Integer> {

    MonthlySubscription findMonthlySubscriptionById(Integer id);

    List<MonthlySubscription> findMonthlySubscriptionByEndDateAfterAndCompanyOrganizerId(LocalDate date , Integer user_id);
    List<MonthlySubscription> findMonthlySubscriptionByEndDateAfterAndIndividualOrganizerId(LocalDate date , Integer user_id);
}
