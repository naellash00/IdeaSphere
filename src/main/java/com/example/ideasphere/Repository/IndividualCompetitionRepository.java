package com.example.ideasphere.Repository;

import com.example.ideasphere.Model.IndividualCompetition;
import com.example.ideasphere.Model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface IndividualCompetitionRepository extends JpaRepository<IndividualCompetition, Integer> {
    IndividualCompetition findIndividualCompetitionById(Integer id);

    List<IndividualCompetition> findIndividualCompetitionByCompetition_Id(Integer userId);

    List<IndividualCompetition> findIndividualCompetitionByIndividualOrganizerId(Integer userid);

    @Query("SELECT ic FROM IndividualCompetition ic JOIN ic.competition c WHERE c.status = :status AND ic.individualOrganizer.myUser.id = :userId")
    List<IndividualCompetition> findByStatusAndUserId(@Param("status") String status, @Param("userId") Integer userId);


}
