package com.otakumap.domain.payment.repository;

import com.otakumap.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByMerchantUid(String merchantUid);
}
