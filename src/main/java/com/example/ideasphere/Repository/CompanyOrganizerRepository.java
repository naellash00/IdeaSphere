package com.example.ideasphere.Repository;

import com.example.ideasphere.Model.CompanyOrganizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyOrganizerRepository extends JpaRepository<CompanyOrganizer , Integer> {

    CompanyOrganizer findCompanyOrganizerById(Integer id);

}
