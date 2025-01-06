package com.example.ideasphere.Service;


import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.DTOsIN.IndividualOrganizerDTOsIN;
import com.example.ideasphere.DTOsOut.IndividualOrganizerDTOOut;
import com.example.ideasphere.DTOsOut.SubmissionOutDTO;
import com.example.ideasphere.Model.Competition;
import com.example.ideasphere.Model.IndividualOrganizer;
import com.example.ideasphere.Model.MyUser;
import com.example.ideasphere.Model.Submission;
import com.example.ideasphere.Repository.AuthRepository;
import com.example.ideasphere.Repository.CompetitionRepository;
import com.example.ideasphere.Repository.IndividualOrganizerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class IndividualOrganizerService {
    @Autowired
    private final IndividualOrganizerRepository individualOrganizerRepository;
    @Autowired
    private final AuthRepository authRepository;
    private final CompetitionRepository competitionRepository;

    private final  EmailSenderJava emailSender;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public IndividualOrganizerDTOOut getMyProfile(Integer id){

        MyUser myUser = authRepository.findMyUserById(id);
        if (myUser == null) throw new ApiException("Error: user not found");

        return ConvertDTOOut(myUser);

    }
    public IndividualOrganizerDTOOut ConvertDTOOut(MyUser myUser){
        IndividualOrganizerDTOOut individualOrganizerDTOOut = new IndividualOrganizerDTOOut();
        individualOrganizerDTOOut.setUsername(myUser.getUsername());
        individualOrganizerDTOOut.setName(myUser.getName());
        individualOrganizerDTOOut.setEmail(myUser.getEmail());
        individualOrganizerDTOOut.setPhoneNumber(myUser.getIndividualOrganizer().getPhoneNumber());

        return individualOrganizerDTOOut;
    }


    // register Individual Organizer user
    public void register(IndividualOrganizerDTOsIN individualOrganizer) {
        MyUser myUser = new MyUser();
        myUser.setName(individualOrganizer.getName());
        myUser.setUsername(individualOrganizer.getUsername());
        myUser.setPassword(passwordEncoder.encode(individualOrganizer.getPassword()));
        myUser.setEmail(individualOrganizer.getEmail());
        myUser.setRole("INDIVIDUAL");

        myUser = authRepository.save(myUser);

        IndividualOrganizer individualOrganizer1 = new IndividualOrganizer();
        individualOrganizer1.setPhoneNumber(individualOrganizer.getPhoneNumber());
        individualOrganizer1.setMyUser(myUser);

        individualOrganizerRepository.save(individualOrganizer1);
        // Send a welcome email
        sendWelcomeEmail(myUser, individualOrganizer.getPhoneNumber());
    }

    private void sendWelcomeEmail(MyUser myUser, String phoneNumber) {
        String subject = "Welcome to Our Platform!";
        String message = "<html>" +
                "<body style='background-color: #D4EBF8; font-size: 16px; color: #1F509A; font-family: Arial, sans-serif;'>" +
                "<div style='background-color: #ffffff; border: 2px solid #1F509A; padding: 20px; border-radius: 5px;'>" +
                "<p style='font-size: 18px; font-weight: bold; color: #0A3981;'>Dear " + myUser.getName() + ",</p>" +
                "<p>Welcome to our platform! We are thrilled to have you join us as an Individual Organizer. Below are your registration details:</p>" +
                "<ul style='list-style-type: square; padding-left: 20px; color: #1F509A;'>" +
                "<li><strong>Name:</strong> " + myUser.getName() + "</li>" +
                "<li><strong>Username:</strong> " + myUser.getUsername() + "</li>" +
                "<li><strong>Email:</strong> " + myUser.getEmail() + "</li>" +
                "<li><strong>Phone Number:</strong> " + phoneNumber + "</li>" +
                "</ul>" +
                "<p>If you have any questions or need assistance, feel free to contact our support team at <a href='mailto:support@IdeaSphere.com' style='color: #E38E49;'>support@IdeaSphere.com</a>.</p>" +
                "<p style='color: #1F509A;'>Thank you for choosing our platform, and we wish you great success in your endeavors.</p>" +
                "<p style='margin-top: 20px; color: #0A3981;'>Best regards,</p>" +
                "<p style='font-weight: bold; color: #E38E49;'>The Idea Sphere Team</p>" +
                "</div>" +
                "</body>" +
                "</html>";

        emailSender.sendEmail(myUser.getEmail(), subject, message);
    }

    // update Individual Organizer  user
    public void updateIndividualOrganizer(Integer userid, IndividualOrganizerDTOsIN rganizerDTOsIN ) {


        IndividualOrganizer old = individualOrganizerRepository.findIndividualOrganizerById(rganizerDTOsIN.getIndividualOrganizerId());
        if (old == null) {
            throw new ApiException("Individual Organizer not found");
        }

        MyUser user = authRepository.findMyUserById(userid);

        MyUser oldUser = authRepository.findMyUserById(old.getMyUser().getId());

        oldUser.setName(rganizerDTOsIN.getName());
        oldUser.setUsername(rganizerDTOsIN.getUsername());
        oldUser.setPassword(passwordEncoder.encode(rganizerDTOsIN.getPassword()) );
        oldUser.setEmail(rganizerDTOsIN.getEmail());
        oldUser.setRole("INDIVIDUAL");
        authRepository.save(oldUser);

        old.setPhoneNumber(rganizerDTOsIN.getPhoneNumber());
        old.setMyUser(user);

        individualOrganizerRepository.save(old);


    }


    public void sendComplain(Integer userId, String subject, String text) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }
        emailSender.sendEmail(
                "alsaedihussam449@gmail.com",
                "Complaint Submission",
                "<html>" +
                        "<body style='background-color: #D4EBF8; font-size: 16px; color: #1F509A; font-family: Arial, sans-serif;'>" +
                        "<div style='background-color: #ffffff; border: 2px solid #1F509A; padding: 20px; border-radius: 5px;'>" +
                        "<p style='font-size: 18px; font-weight: bold; color: #0A3981;'>Complaint Submission</p>" +
                        "<p>Dear Support Team,</p>" +
                        "<p>A new complaint has been submitted with the following details:</p>" +
                        "<ul style='list-style-type: square; padding-left: 20px; color: #1F509A;'>" +
                        "<li><strong>Complainant's Name:</strong> " + user.getName() + "</li>" +
                        "<li><strong>Complainant's Email:</strong> " + user.getEmail() + "</li>" +
                        "<li><strong>Complaint Subject:</strong> " + subject + "</li>" +
                        "<li><strong>Complaint Message:</strong> " + text + "</li>" +
                        "</ul>" +
                        "<p style='color: #0A3981;'>Please review the complaint and address it at your earliest convenience. If you require any further details, feel free to reach out to the complainant.</p>" +
                        "<p style='margin-top: 20px; color: #0A3981;'>Best regards,</p>" +
                        "<p style='font-weight: bold; color: #E38E49;'>The Idea Sphere Team</p>" +
                        "</div>" +
                        "</body>" +
                        "</html>"
        );
    }

}
