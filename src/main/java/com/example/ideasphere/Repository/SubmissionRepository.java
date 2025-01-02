package com.example.ideasphere.Repository;

import com.example.ideasphere.Model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
    Submission findSubmissionById(Integer id);
}
