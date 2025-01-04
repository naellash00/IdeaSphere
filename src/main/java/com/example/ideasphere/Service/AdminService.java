package com.example.ideasphere.Service;
import com.example.ideasphere.DTOsIN.AdminInDTO;
import com.example.ideasphere.Model.MyUser;
import com.example.ideasphere.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AuthRepository authRepository;

    public void register(AdminInDTO adminInDTO){
        MyUser myUser = new MyUser();
        myUser.setRole("ADMIN");
        myUser.setUsername(adminInDTO.getUsername());
        String hashPassword = new BCryptPasswordEncoder().encode(adminInDTO.getPassword());
        myUser.setPassword(hashPassword);

        authRepository.save(myUser);
    }
}
