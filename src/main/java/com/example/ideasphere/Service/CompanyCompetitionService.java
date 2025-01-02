package com.example.ideasphere.Service;

import com.example.ideasphere.Repository.CompanyCompetitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyCompetitionService {

    private final CompanyCompetitionRepository companyCompetitionRepository;
}
