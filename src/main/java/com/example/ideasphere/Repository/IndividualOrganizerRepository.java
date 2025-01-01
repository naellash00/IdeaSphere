package com.example.ideasphere.Repository;


import com.example.ideasphere.Model.IndividualOrganizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface IndividualOrganizerRepository extends JpaRepository<IndividualOrganizer, Integer> {

    IndividualOrganizer findIndividualOrganizerById(Integer individualOrganizerId);
}
