package com.example.ideasphere.Repository;

import com.example.ideasphere.Model.CompetitionPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetitionPaymentRepository extends JpaRepository<CompetitionPayment,Integer> {

    CompetitionPayment findCompetitionPaymentById(Integer id);

    CompetitionPayment findCompetitionPaymentByCompetitionId(Integer competitionId);

    List<CompetitionPayment> findCompetitionPaymentByCompetition_CompanyCompetitionId(Integer CompanyCompetitionId);
}
