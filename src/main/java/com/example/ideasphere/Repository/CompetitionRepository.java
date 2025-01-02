package com.example.ideasphere.Repository;

import com.example.ideasphere.Model.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition,Integer> {

    Competition findCompetitionById(Integer id);
}
