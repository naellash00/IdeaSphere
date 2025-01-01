package com.example.ideasphere.Service;


import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.DTOsIN.IndividualOrganizerDTOsIN;
import com.example.ideasphere.Model.IndividualOrganizer;
import com.example.ideasphere.Model.MyUser;
import com.example.ideasphere.Repository.AuthRepository;
import com.example.ideasphere.Repository.IndividualOrganizerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class IndividualOrganizerService {
    @Autowired
    private final IndividualOrganizerRepository individualOrganizerRepository;
    @Autowired
    private final AuthRepository authRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    // register Individual Organizer user
    public void register(IndividualOrganizerDTOsIN individualOrganizer) {
        MyUser myUser = new MyUser();
        myUser.setName(individualOrganizer.getName());
        myUser.setUsername(individualOrganizer.getUsername());
        myUser.setPassword(passwordEncoder.encode(individualOrganizer.getPassword()) );
        myUser.setEmail(individualOrganizer.getEmail());
        myUser.setRole("INDIVIDUAL");

        IndividualOrganizer individualOrganizer1 = new IndividualOrganizer();
        individualOrganizer1.setPhoneNumber(individualOrganizer.getPhoneNumber());
        individualOrganizer1.setMyUser(myUser);

        individualOrganizerRepository.save(individualOrganizer1);
        authRepository.save(myUser);

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

        old.setPhoneNumber(rganizerDTOsIN.getPhoneNumber());
        old.setMyUser(user);

        individualOrganizerRepository.save(old);
        authRepository.save(oldUser);

    }

    // delete Individual Organizer  user
     public void deleteIndividualOrganizer(Integer userId) {


            IndividualOrganizer Io = individualOrganizerRepository.findIndividualOrganizerById(userId);
            if (Io == null) {
                throw new ApiException("Employee not found");
            }
            MyUser user = authRepository.findMyUserById(Io.getMyUser().getId());

            individualOrganizerRepository.delete(Io);
            Io.setMyUser(null);
        }


}
