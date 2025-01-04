package com.example.ideasphere.Service;
import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.DTOsIN.AdminInDTO;
import com.example.ideasphere.Model.CompanyOrganizer;
import com.example.ideasphere.Model.MyUser;
import com.example.ideasphere.Repository.AuthRepository;
import com.example.ideasphere.Repository.CompanyOrganizerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AuthRepository authRepository;
    private final CompanyOrganizerRepository companyOrganizerRepository;


    public void register(AdminInDTO adminInDTO){
        MyUser myUser = new MyUser();
        myUser.setRole("ADMIN");
        myUser.setUsername(adminInDTO.getUsername());
        String hashPassword = new BCryptPasswordEncoder().encode(adminInDTO.getPassword());
        myUser.setPassword(hashPassword);

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
