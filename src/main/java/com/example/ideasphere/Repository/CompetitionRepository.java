package com.example.ideasphere.Repository;

import com.example.ideasphere.Model.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition,Integer> {

    Competition findCompetitionById(Integer id);

    @Query("SELECT c FROM Competition c LEFT JOIN FETCH c.submissions WHERE c.endDate < :endDate AND c.status = :status")
    List<Competition> findByEndDateBeforeAndStatusWithSubmissions(@Param("endDate") LocalDate endDate, @Param("status") String status);

    List<Competition> findCompetitionByStatus(String status);
}
