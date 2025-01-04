package com.example.ideasphere.Service;

import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.Model.CompetitionPayment;
import com.example.ideasphere.Model.MyUser;
import com.example.ideasphere.Repository.AuthRepository;
import com.example.ideasphere.Repository.CompetitionPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompetitionPaymentService {


    private final CompetitionPaymentRepository competitionPaymentRepository;
    private final AuthRepository authRepository;

    public List<CompetitionPayment> getAllMyCompetitionPayment(Integer user_id){
        MyUser myUser = authRepository.findMyUserById(user_id);
        if (myUser == null) throw new ApiException("Error: user not found");

        return competitionPaymentRepository.findCompetitionPaymentByCompetition_CompanyCompetitionId(user_id);
    }
}
