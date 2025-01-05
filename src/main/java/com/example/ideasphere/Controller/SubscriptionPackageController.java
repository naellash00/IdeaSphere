package com.example.ideasphere.Controller;


import com.example.ideasphere.ApiResponse.ApiResponse;
import com.example.ideasphere.Model.SubscriptionPackage;
import com.example.ideasphere.Service.SubscriptionPackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscription-package")
@RequiredArgsConstructor
public class SubscriptionPackageController {

    private final SubscriptionPackageService subscriptionPackageService;



    @GetMapping("/get-all-subscription-package")
    public ResponseEntity<List<SubscriptionPackage>> getAllSubscriptionPackages() {
        List<SubscriptionPackage> packages = subscriptionPackageService.getAllSubscriptionPackage();
        return ResponseEntity.ok(packages);
    }


    @GetMapping("/get-subscription-package-by-id/{id}")
    public ResponseEntity<SubscriptionPackage> getSubscriptionPackageById(@PathVariable Integer id) {
        SubscriptionPackage subscriptionPackage = subscriptionPackageService.getSubscriptionPackageById(id);
        return ResponseEntity.ok(subscriptionPackage);
    }


    @PostMapping("/add-subscription-package")
    public ResponseEntity<ApiResponse> addSubscriptionPackage(@RequestBody SubscriptionPackage subscriptionPackage) {
        subscriptionPackageService.addSubscriptionPackage(subscriptionPackage);
        return ResponseEntity.ok(new ApiResponse("Subscription package added successfully."));
    }


    @PutMapping("/update-subscription-package")
    public ResponseEntity<ApiResponse> updateSubscriptionPackage(@RequestBody SubscriptionPackage subscriptionPackage) {
        subscriptionPackageService.updateSubscriptionPackage(subscriptionPackage);
        return ResponseEntity.ok(new ApiResponse("Subscription package updated successfully."));
    }

    @PutMapping("/active-subscription-package/{id}")
    public ResponseEntity<ApiResponse> active(@PathVariable Integer id) {
        subscriptionPackageService.activeSubscriptionPackage(id);
        return ResponseEntity.ok(new ApiResponse("Subscription package Activated successfully."));
    }
    @PutMapping("/deactivated-subscription-package/{id}")
    public ResponseEntity<ApiResponse> deactivate(@PathVariable Integer id) {
        subscriptionPackageService.detectiveSubscriptionPackage(id);
        return ResponseEntity.ok(new ApiResponse("Subscription package deactivated successfully."));
    }
}
