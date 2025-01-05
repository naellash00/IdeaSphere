package com.example.ideasphere.Service;


import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.DTOsIN.IndividualOrganizerDTOsIN;
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

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


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


//    public void activeIndividual(Integer id){
//
//        IndividualOrganizer individualOrganizer = individualOrganizerRepository.findIndividualOrganizerById(id);
//        if (individualOrganizer == null) {
//            throw new ApiException("Error :  Individual Organizer not found");
//        }
//        if (individualOrganizer.getStatus().equalsIgnoreCase("Active")) throw new ApiException("Error: Individual already Active");
//
//        individualOrganizer.setStatus("Active");
//
//        individualOrganizerRepository.save(individualOrganizer);
//    }
//
//
//    public void detectiveIndividual(Integer id){
//
//        IndividualOrganizer individualOrganizer = individualOrganizerRepository.findIndividualOrganizerById(id);
//        if (individualOrganizer == null) {
//            throw new ApiException("Error :  Individual Organizer not found");
//        }
//        if (individualOrganizer.getStatus().equalsIgnoreCase("Not Active")) throw new ApiException("Error: Individual already Not Active");
//
//        individualOrganizer.setStatus("Not Active");
//
//        individualOrganizerRepository.save(individualOrganizer);
//
//    }

    // Naelah
    public List<SubmissionOutDTO> viewMyCompetitionSubmissions(Integer competition_id) {
        Competition competition = competitionRepository.findCompetitionById(competition_id);
        List<SubmissionOutDTO> myCompetitionSubmissions = new ArrayList<>();
        for (Submission submission : competition.getSubmissions()) {
            SubmissionOutDTO submissionOutDTO = new SubmissionOutDTO();

            submissionOutDTO.setPdfFile(submission.getPDFFile());
            submissionOutDTO.setFileURL(submission.getFileURL());
            submissionOutDTO.setSecondFileURL(submission.getSecondFileURL());
            submissionOutDTO.setThirdFileURL(submission.getThirdFileURL());
            submissionOutDTO.setDescription(submission.getDescription());
            submissionOutDTO.setSubmittedAt(submission.getSubmittedAt());
            submissionOutDTO.setCompetitionTitle(submission.getCompetition().getTitle());

            myCompetitionSubmissions.add(submissionOutDTO);
        }
        return myCompetitionSubmissions;
    }



}
