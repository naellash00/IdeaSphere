package com.example.ideasphere.Repository;

import com.example.ideasphere.Model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {
    Participant findParticipantById(Integer id);
}
