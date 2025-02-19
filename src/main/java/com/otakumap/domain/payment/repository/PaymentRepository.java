package com.otakumap.domain.payment.repository;

import com.otakumap.domain.payment.entity.UserPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<UserPayment, Long> {
    boolean existsByMerchantUid(String merchantUid);
    Optional<UserPayment> findByMerchantUid(String merchantUid);
}
