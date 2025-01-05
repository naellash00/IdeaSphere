package com.example.ideasphere.Service;

import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.Model.MonthlySubscription;
import com.example.ideasphere.Model.MyUser;
import com.example.ideasphere.Model.SubscriptionPackage;
import com.example.ideasphere.Repository.AuthRepository;
import com.example.ideasphere.Repository.MonthlySubscriptionRepository;
import com.example.ideasphere.Repository.SubscriptionPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MonthlySubscriptionService {

    private final MonthlySubscriptionRepository monthlySubscriptionRepository;
    private final AuthRepository authRepository;
    private final SubscriptionPackageRepository subscriptionPackageRepository;



    public List<MonthlySubscription> getAllMonthlySubscription(){
        return monthlySubscriptionRepository.findAll().stream().toList();
    }

    public List<MonthlySubscription> getMyMonthlySubscription(Integer userId){
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null) throw new ApiException("Error: user not found");

        Set<MonthlySubscription> monthlySubscriptions = myUser.getCompanyOrganizer() != null ? myUser.getCompanyOrganizer().getMonthlySubscriptions() : myUser.getIndividualOrganizer().getMonthlySubscriptions();

        return monthlySubscriptions.stream().toList();
    }

    public void subscribe(Integer userid , Integer SubscriptionPackageId){

        MyUser myUser = authRepository.findMyUserById(userid);
        if (myUser == null) throw new ApiException("Error: user not found");

        SubscriptionPackage subscriptionPackage = subscriptionPackageRepository.findSubscriptionPackageById(SubscriptionPackageId);

        if (subscriptionPackage == null) throw new ApiException("Error : SubscriptionPackage not found");

        if (subscriptionPackage.getStatus().equalsIgnoreCase("Not Active")) throw new ApiException("Error: SubscriptionPackage is Not Active");


        Set<MonthlySubscription> previousSubscriptions = myUser.getCompanyOrganizer() != null ? myUser.getCompanyOrganizer().getMonthlySubscriptions() : myUser.getIndividualOrganizer().getMonthlySubscriptions();

        LocalDate today = LocalDate.now();

        boolean checkActiveSubscription = false;
        for (MonthlySubscription p : previousSubscriptions){

            if (p.getEndDate().isBefore(today)) {
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

        SubscriptionPackage subscriptionPackage = subscriptionPackageRepository.findSubscriptionPackageById(SubscriptionPackageId);

        if (subscriptionPackage == null) throw new ApiException("Error : SubscriptionPackage not found");

        if (subscriptionPackage.getStatus().equalsIgnoreCase("Not Active")) throw new ApiException("Error: SubscriptionPackage is Not Active");


        Set<MonthlySubscription> previousSubscriptions = myUser.getCompanyOrganizer() != null ? myUser.getCompanyOrganizer().getMonthlySubscriptions() : myUser.getIndividualOrganizer().getMonthlySubscriptions();

        LocalDate today = LocalDate.now();

        MonthlySubscription activeMonthlySubscription = new MonthlySubscription();


        for (MonthlySubscription p : previousSubscriptions){

            if (!p.getEndDate().isBefore(today)) {
                activeMonthlySubscription = p;
            }
        }

        LocalDate startDate = today;
        LocalDate endDate = today.plusMonths(subscriptionPackage.getCountMonth());

        if (!activeMonthlySubscription.equals(new MonthlySubscription())) {
            startDate = activeMonthlySubscription.getEndDate().plusDays(1);
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
}
