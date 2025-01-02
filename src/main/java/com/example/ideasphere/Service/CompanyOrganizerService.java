package com.example.ideasphere.Service;

import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.DTOsIN.CompanyOrganizerDTOIn;
import com.example.ideasphere.DTOsOut.CompanyOrganizerDTOOut;
import com.example.ideasphere.Model.CompanyOrganizer;
import com.example.ideasphere.Model.MyUser;
import com.example.ideasphere.Repository.AuthRepository;
import com.example.ideasphere.Repository.CompanyOrganizerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyOrganizerService {

    private final CompanyOrganizerRepository companyOrganizerRepository;
    private final AuthRepository authRepository;

    public CompanyOrganizerDTOOut getMyProfile(Integer id){

        MyUser myUser = authRepository.findMyUserById(id);
        if (myUser == null) throw new ApiException("Error: user not found");

        return ConvertDTOOut(myUser);

    }

    public void register(CompanyOrganizerDTOIn companyOrganizerDTOIn){

        MyUser myUser = new MyUser();

        myUser.setUsername(companyOrganizerDTOIn.getUsername());
        myUser.setPassword(new BCryptPasswordEncoder().encode(companyOrganizerDTOIn.getPassword()));
        myUser.setName(companyOrganizerDTOIn.getName());
        myUser.setEmail(companyOrganizerDTOIn.getEmail());
        myUser.setRole("COMPANY");

        myUser =  authRepository.save(myUser);

        CompanyOrganizer companyOrganizer = new CompanyOrganizer();

        companyOrganizer.setCompanyName(companyOrganizerDTOIn.getCompanyName());
        companyOrganizer.setCommercialRecord(companyOrganizerDTOIn.getCommercialRecord());
        companyOrganizer.setContactEmail(companyOrganizerDTOIn.getContactEmail());
        companyOrganizer.setContactPhone(companyOrganizerDTOIn.getContactPhone());
        companyOrganizer.setStatus("Not Active");
        companyOrganizer.setMyUser(myUser);

        companyOrganizerRepository.save(companyOrganizer);


    }


    public void updateProfile(Integer user_id, CompanyOrganizerDTOIn companyOrganizerDTOIn){

        MyUser myUser  = authRepository.findMyUserById(user_id);
        if (myUser == null) throw new ApiException("Error: user not found");


        MyUser myUserOld = authRepository.findMyUserById(companyOrganizerDTOIn.getId());
        if (myUserOld == null) throw new ApiException("Error: user not found");

        if (!myUserOld.getId().equals(myUser.getId())) throw new ApiException("Error : this user not belong to you update ");
        myUserOld.setUsername(companyOrganizerDTOIn.getUsername());
        myUserOld.setPassword(new BCryptPasswordEncoder().encode(companyOrganizerDTOIn.getPassword()));
        myUserOld.setName(companyOrganizerDTOIn.getName());
        myUserOld.setEmail(companyOrganizerDTOIn.getEmail());

        authRepository.save(myUserOld);

        CompanyOrganizer companyOrganizer = myUserOld.getCompanyOrganizer();

        companyOrganizer.setCompanyName(companyOrganizerDTOIn.getCompanyName());
        companyOrganizer.setCommercialRecord(companyOrganizerDTOIn.getCommercialRecord());
        companyOrganizer.setContactEmail(companyOrganizerDTOIn.getContactEmail());
        companyOrganizer.setContactPhone(companyOrganizerDTOIn.getContactPhone());
        companyOrganizer.setStatus("Not Active");

        companyOrganizerRepository.save(companyOrganizer);
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

    public CompanyOrganizerDTOOut ConvertDTOOut(MyUser myUser){
        CompanyOrganizerDTOOut companyOrganizerDTOOut = new CompanyOrganizerDTOOut();

        companyOrganizerDTOOut.setUsername(myUser.getUsername());
        companyOrganizerDTOOut.setName(myUser.getName());
        companyOrganizerDTOOut.setEmail(myUser.getEmail());
        companyOrganizerDTOOut.setRole(myUser.getRole());
        companyOrganizerDTOOut.setCompanyName(myUser.getCompanyOrganizer().getCompanyName());
        companyOrganizerDTOOut.setCommercialRecord(myUser.getCompanyOrganizer().getCommercialRecord());
        companyOrganizerDTOOut.setContactEmail(myUser.getCompanyOrganizer().getContactEmail());
        companyOrganizerDTOOut.setContactPhone(myUser.getCompanyOrganizer().getContactPhone());
        companyOrganizerDTOOut.setStatus(myUser.getCompanyOrganizer().getStatus());

        return companyOrganizerDTOOut;
    }
}
