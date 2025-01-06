package com.example.ideasphere.Service;

import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.DTOsIN.CompanyOrganizerDTOIn;
import com.example.ideasphere.DTOsOut.CompanyOrganizerDTOOut;
import com.example.ideasphere.DTOsOut.SubmissionOutDTO;
import com.example.ideasphere.Model.CompanyOrganizer;
import com.example.ideasphere.Model.Competition;
import com.example.ideasphere.Model.MyUser;
import com.example.ideasphere.Model.Submission;
import com.example.ideasphere.Repository.AuthRepository;
import com.example.ideasphere.Repository.CompanyOrganizerRepository;
import com.example.ideasphere.Repository.CompetitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyOrganizerService {

    private final CompanyOrganizerRepository companyOrganizerRepository;
    private final AuthRepository authRepository;
    private final CompetitionRepository competitionRepository;
    private final  EmailSenderJava emailSender;

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


        // Send a welcome email
        sendWelcomeEmail(myUser,companyOrganizer);
    }

    private void sendWelcomeEmail(MyUser myUser,CompanyOrganizer companyOrganizer) {
        String subject = "Welcome to Our Platform!";
        String message = "<html>" +
                "<body style='background-color: #D4EBF8; font-size: 16px; color: #1F509A; font-family: Arial, sans-serif;'>" +
                "<div style='background-color: #ffffff; border: 2px solid #1F509A; padding: 20px; border-radius: 5px;'>" +
                "<p style='font-size: 18px; font-weight: bold; color: #0A3981;'>Dear " + myUser.getName() + ",</p>" +
                "<p>Welcome to our platform! We are thrilled to have you join us as a Company Organizer. Below are your registration details:</p>" +
                "<ul style='list-style-type: square; padding-left: 20px; color: #1F509A;'>" +
                "<li><strong>Company Name:</strong> " + companyOrganizer.getCompanyName() + "</li>" +
                "<li><strong>Email:</strong> " + companyOrganizer.getContactEmail() + "</li>" +
                "<li><strong>Commercial Record:</strong> " + companyOrganizer.getCommercialRecord() + "</li>" +
                "</ul>" +
                "<p>If you have any questions or need assistance, feel free to contact our support team at " +
                "<a href='mailto:support@IdeaSphere.com' style='color: #E38E49;'>support@IdeaSphere.com</a>.</p>" +
                "<p style='color: #1F509A;'>Thank you for choosing our platform, and we wish you great success in your endeavors.</p>" +
                "<p style='color: #E38E49; font-weight: bold;'>We are currently processing your account activation. You will receive a notification as soon as your account is successfully activated. We appreciate your patience and look forward to serving you.</p>" +
                "<p style='margin-top: 20px; color: #0A3981;'>Best regards,</p>" +
                "<p style='font-weight: bold; color: #E38E49;'>The Idea Sphere Team</p>" +
                "</div>" +
                "</body>" +
                "</html>";


        emailSender.sendEmail(myUser.getEmail(), subject, message);
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



    public CompanyOrganizerDTOOut ConvertDTOOut(MyUser myUser){
        CompanyOrganizerDTOOut companyOrganizerDTOOut = new CompanyOrganizerDTOOut();

        companyOrganizerDTOOut.setUsername(myUser.getUsername());
        companyOrganizerDTOOut.setName(myUser.getName());
        companyOrganizerDTOOut.setEmail(myUser.getEmail());
        companyOrganizerDTOOut.setCompanyName(myUser.getCompanyOrganizer().getCompanyName());
        companyOrganizerDTOOut.setCommercialRecord(myUser.getCompanyOrganizer().getCommercialRecord());
        companyOrganizerDTOOut.setContactEmail(myUser.getCompanyOrganizer().getContactEmail());
        companyOrganizerDTOOut.setContactPhone(myUser.getCompanyOrganizer().getContactPhone());
        companyOrganizerDTOOut.setStatus(myUser.getCompanyOrganizer().getStatus());

        return companyOrganizerDTOOut;
    }

}
