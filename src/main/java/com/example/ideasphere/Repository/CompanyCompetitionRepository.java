package com.example.ideasphere.Repository;

import com.example.ideasphere.Model.CompanyCompetition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyCompetitionRepository extends JpaRepository<CompanyCompetition,Integer> {

    CompanyCompetition findCompanyCompetitionById(Integer id);
}
