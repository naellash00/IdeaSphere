package com.example.ideasphere.Repository;

import org.springframework.stereotype.Repository;
import com.example.ideasphere.Model.WinnerPayment;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface WinnerPaymentRepository extends JpaRepository<WinnerPayment, Integer>{
    WinnerPayment findWinnerPaymentById(Integer id);
}
