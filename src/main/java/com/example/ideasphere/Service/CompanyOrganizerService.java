package com.example.ideasphere.Service;

import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.DTOsIN.CompanyOrganizerDTOIn;
import com.example.ideasphere.Model.CompanyOrganizer;
import com.example.ideasphere.Model.MyUser;
import com.example.ideasphere.Repository.AuthRepository;
import com.example.ideasphere.Repository.CompanyOrganizerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyOrganizerService {

    private final CompanyOrganizerRepository companyOrganizerRepository;
    private final AuthRepository authRepository;

    public void register(CompanyOrganizerDTOIn companyOrganizerDTOIn){

        MyUser myUser = new MyUser();

        myUser.setUsername(companyOrganizerDTOIn.getUsername());
        myUser.setPassword(new BCryptPasswordEncoder().encode(companyOrganizerDTOIn.getPassword()));
        myUser.setName(companyOrganizerDTOIn.getName());
        myUser.setEmail(companyOrganizerDTOIn.getEmail());
        myUser.setRole("COMPANY");

        CompanyOrganizer companyOrganizer = new CompanyOrganizer();

        companyOrganizer.setCompanyName(companyOrganizerDTOIn.getCompanyName());
        companyOrganizer.setCommercialRecord(companyOrganizerDTOIn.getCommercialRecord());
        companyOrganizer.setContactEmail(companyOrganizerDTOIn.getContactEmail());
        companyOrganizer.setContactPhone(companyOrganizerDTOIn.getContactPhone());
        companyOrganizer.setStatus("Not Active");

        myUser.setCompanyOrganizer(companyOrganizer);

        authRepository.save(myUser);

    }

    public void activeCompany(Integer id){

        CompanyOrganizer companyOrganizer = companyOrganizerRepository.findCompanyOrganizerById(id);
        if (companyOrganizer == null) throw new ApiException("Error : Company organizer not found ");

        if (companyOrganizer.getStatus().equalsIgnoreCase("Active")) throw new ApiException("Error: Company already Active");


        companyOrganizer.setStatus("Active");

        companyOrganizerRepository.save(companyOrganizer);
    }


    public void detectiveCompany(Integer id){

        CompanyOrganizer companyOrganizer = companyOrganizerRepository.findCompanyOrganizerById(id);
        if (companyOrganizer == null) throw new ApiException("Error : Company organizer not found ");

        if (companyOrganizer.getStatus().equalsIgnoreCase("Not Active")) throw new ApiException("Error: Company already Not Active");


        companyOrganizer.setStatus("Not Active");

        companyOrganizerRepository.save(companyOrganizer);
    }
}
