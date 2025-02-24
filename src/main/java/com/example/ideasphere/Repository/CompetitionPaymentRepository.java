package com.example.ideasphere.Repository;

import com.example.ideasphere.Model.CompetitionPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetitionPaymentRepository extends JpaRepository<CompetitionPayment,Integer> {

    CompetitionPayment findCompetitionPaymentById(Integer id);

    CompetitionPayment findCompetitionPaymentByCompetitionId(Integer competitionId);
    CompetitionPayment findCompetitionPaymentByCompetitionIdAndTypePayment(Integer competitionId , String typePayment);
    List<CompetitionPayment> findCompetitionPaymentByCompetition_CompanyCompetitionCompanyOrganizerId(Integer CompanyCompetitionId);
    List<CompetitionPayment> findCompetitionPaymentByCompetition_IndividualCompetitionId(Integer IndividualCompetitionId);
}
