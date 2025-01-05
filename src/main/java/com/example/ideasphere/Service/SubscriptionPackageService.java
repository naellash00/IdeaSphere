package com.example.ideasphere.Service;

import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.Model.MyUser;
import com.example.ideasphere.Model.SubscriptionPackage;
import com.example.ideasphere.Repository.SubscriptionPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionPackageService {

    private final SubscriptionPackageRepository subscriptionPackageRepository;

    public List<SubscriptionPackage> getAllSubscriptionPackage(){
        return subscriptionPackageRepository.findAll();
    }
    public SubscriptionPackage getSubscriptionPackageById(Integer id){
        return subscriptionPackageRepository.findSubscriptionPackageById(id);
    }
    public void addSubscriptionPackage(SubscriptionPackage subscriptionPackage){

        subscriptionPackageRepository.save(subscriptionPackage);
    }

    public void updateSubscriptionPackage(SubscriptionPackage subscriptionPackage){
        SubscriptionPackage subscriptionPackageFound = subscriptionPackageRepository.findSubscriptionPackageById(subscriptionPackage.getId());

        if (subscriptionPackageFound == null) throw new ApiException("Error: SubscriptionPackage not found");

        subscriptionPackageFound.setPackageName(subscriptionPackage.getPackageName());
        subscriptionPackageFound.setCountMonth(subscriptionPackage.getCountMonth());
        subscriptionPackageFound.setPrice(subscriptionPackage.getPrice());

        subscriptionPackageRepository.save(subscriptionPackageFound);
    }


    public void activeSubscriptionPackage(Integer id){
        SubscriptionPackage subscriptionPackage = subscriptionPackageRepository.findSubscriptionPackageById(id);

        if (subscriptionPackage == null) throw new ApiException("Error: SubscriptionPackage not found");

        if (subscriptionPackage.getStatus().equalsIgnoreCase("Active")) throw new ApiException("Error: SubscriptionPackage already active");

        subscriptionPackage.setStatus("Active");

        subscriptionPackageRepository.save(subscriptionPackage);
    }

    public void detectiveSubscriptionPackage(Integer id){
        SubscriptionPackage subscriptionPackage = subscriptionPackageRepository.findSubscriptionPackageById(id);

        if (subscriptionPackage == null) throw new ApiException("Error: SubscriptionPackage not found");

        if (subscriptionPackage.getStatus().equalsIgnoreCase("Not Active")) throw new ApiException("Error: SubscriptionPackage already detective");

        subscriptionPackage.setStatus("Not Active");

        subscriptionPackageRepository.save(subscriptionPackage);
    }
}
