package com.example.ideasphere.Repository;

import com.example.ideasphere.Model.IndividualCompetition;
import com.example.ideasphere.Model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface IndividualCompetitionRepository extends JpaRepository<IndividualCompetition, Integer> {
    IndividualCompetition findIndividualCompetitionById(Integer id);


}
