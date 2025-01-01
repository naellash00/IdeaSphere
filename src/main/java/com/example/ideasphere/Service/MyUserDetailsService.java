package com.example.ideasphere.Service;

import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.Model.MyUser;
import com.example.ideasphere.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final AuthRepository authRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = authRepository.findMyUserByUsername(username);
        if(myUser == null)throw new ApiException("Error: username or password is incorrect");

        return myUser;
    }
}

