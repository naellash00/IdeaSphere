package com.example.ideasphere.Service;

import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.DTOsOut.MonthlySubscriptionDTOOut;
import com.example.ideasphere.Model.MonthlySubscription;
import com.example.ideasphere.Model.MyUser;
import com.example.ideasphere.Model.SubscriptionPackage;
import com.example.ideasphere.Repository.AuthRepository;
import com.example.ideasphere.Repository.MonthlySubscriptionRepository;
import com.example.ideasphere.Repository.SubscriptionPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MonthlySubscriptionService {

    private final MonthlySubscriptionRepository monthlySubscriptionRepository;
    private final AuthRepository authRepository;
    private final SubscriptionPackageRepository subscriptionPackageRepository;



    public List<MonthlySubscriptionDTOOut> getAllMonthlySubscription(){
        List<MonthlySubscription> monthlySubscriptions = monthlySubscriptionRepository.findAll().stream().toList();
        return monthlySubscriptions.stream()
                .map(this::convertDTO)
                .collect(Collectors.toList());
    }

    public List<MonthlySubscriptionDTOOut> getMyMonthlySubscription(Integer userId){
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null) throw new ApiException("Error: user not found");

        Set<MonthlySubscription> monthlySubscriptions = myUser.getCompanyOrganizer() != null ? myUser.getCompanyOrganizer().getMonthlySubscriptions() : myUser.getIndividualOrganizer().getMonthlySubscriptions();

        return monthlySubscriptions.stream()
                .map(this::convertDTO)
                .collect(Collectors.toList());


    }

    public void subscribe(Integer userid , Integer SubscriptionPackageId){


        MyUser myUser = authRepository.findMyUserById(userid);
        if (myUser == null) throw new ApiException("Error: user not found");

        if (myUser.getCompanyOrganizer() != null){
            if (myUser.getCompanyOrganizer().getStatus().equalsIgnoreCase("Not Active")) throw new ApiException("Error: CompanyOrganizer user not active");
        }

        SubscriptionPackage subscriptionPackage = subscriptionPackageRepository.findSubscriptionPackageById(SubscriptionPackageId);

        if (subscriptionPackage == null) throw new ApiException("Error : SubscriptionPackage not found");

        if (subscriptionPackage.getStatus().equalsIgnoreCase("Not Active")) throw new ApiException("Error: SubscriptionPackage is Not Active");


        Set<MonthlySubscription> previousSubscriptions = myUser.getCompanyOrganizer() != null ? myUser.getCompanyOrganizer().getMonthlySubscriptions() : myUser.getIndividualOrganizer().getMonthlySubscriptions();

        LocalDate today = LocalDate.now();

        boolean checkActiveSubscription = false;
        for (MonthlySubscription p : previousSubscriptions){

            if (p.getEndDate().isAfter(today)) {
                checkActiveSubscription = true;
                break;
            }
        }

        if (checkActiveSubscription) throw new ApiException("Error: you already subscribed");

        MonthlySubscription monthlySubscription = new MonthlySubscription();

        monthlySubscription.setStartDate(today);
        monthlySubscription.setEndDate(today.plusMonths(subscriptionPackage.getCountMonth()));
        monthlySubscription.setAmount(subscriptionPackage.getPrice());
        if (myUser.getCompanyOrganizer() != null){
            monthlySubscription.setCompanyOrganizer(myUser.getCompanyOrganizer());
        }else{
            monthlySubscription.setIndividualOrganizer(myUser.getIndividualOrganizer());
        }
        monthlySubscription.setSubscriptionPackage(subscriptionPackage);

        monthlySubscriptionRepository.save(monthlySubscription);
    }

    public void renewSubscribe(Integer userid , Integer SubscriptionPackageId){
        MyUser myUser = authRepository.findMyUserById(userid);
        if (myUser == null) throw new ApiException("Error: user not found");

        if (myUser.getCompanyOrganizer() != null){
            if (myUser.getCompanyOrganizer().getStatus().equalsIgnoreCase("Not Active")) throw new ApiException("Error: CompanyOrganizer user not active");
        }

        SubscriptionPackage subscriptionPackage = subscriptionPackageRepository.findSubscriptionPackageById(SubscriptionPackageId);

        if (subscriptionPackage == null) throw new ApiException("Error : SubscriptionPackage not found");

        if (subscriptionPackage.getStatus().equalsIgnoreCase("Not Active")) throw new ApiException("Error: SubscriptionPackage is Not Active");


        Set<MonthlySubscription> previousSubscriptions = myUser.getCompanyOrganizer() != null ? myUser.getCompanyOrganizer().getMonthlySubscriptions() : myUser.getIndividualOrganizer().getMonthlySubscriptions();

        LocalDate today = LocalDate.now();

        List<MonthlySubscription> activeMonthlySubscriptions = new ArrayList<>();


        for (MonthlySubscription p : previousSubscriptions){

            if (!p.getEndDate().isBefore(today)) {
                activeMonthlySubscriptions.add(p);
            }
        }

        LocalDate startDate = today;
        LocalDate endDate = today.plusMonths(subscriptionPackage.getCountMonth());

        if (!activeMonthlySubscriptions.isEmpty()) {
            // make sort here get last active end date
            MonthlySubscription lastActiveSubscription = activeMonthlySubscriptions.stream()
                    .max(Comparator.comparing(MonthlySubscription::getEndDate))
                    .orElseThrow();
            startDate = lastActiveSubscription.getEndDate().plusDays(1);
            endDate = startDate.plusMonths(subscriptionPackage.getCountMonth());
        }


        MonthlySubscription monthlySubscription = new MonthlySubscription();

        monthlySubscription.setStartDate(startDate);
        monthlySubscription.setEndDate(endDate);
        monthlySubscription.setAmount(subscriptionPackage.getPrice());
        if (myUser.getCompanyOrganizer() != null){
            monthlySubscription.setCompanyOrganizer(myUser.getCompanyOrganizer());
        }else{
            monthlySubscription.setIndividualOrganizer(myUser.getIndividualOrganizer());
        }
        monthlySubscription.setSubscriptionPackage(subscriptionPackage);

        monthlySubscriptionRepository.save(monthlySubscription);
    }

    public MonthlySubscriptionDTOOut convertDTO(MonthlySubscription monthlySubscription){

        return new MonthlySubscriptionDTOOut( monthlySubscription.getStartDate(),monthlySubscription.getEndDate(),monthlySubscription.getAmount(),monthlySubscription.getIndividualOrganizer() != null? monthlySubscription.getIndividualOrganizer().getMyUser().getUsername():monthlySubscription.getCompanyOrganizer().getCompanyName() ,monthlySubscription.getSubscriptionPackage().getPackageName());
    }
}
