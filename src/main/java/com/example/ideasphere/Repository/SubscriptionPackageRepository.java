package com.example.ideasphere.Repository;

import com.example.ideasphere.Model.SubscriptionPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionPackageRepository extends JpaRepository<SubscriptionPackage,Integer> {

    SubscriptionPackage findSubscriptionPackageById(Integer id);
}
