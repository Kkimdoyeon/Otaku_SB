package com.otakumap.domain.transaction.repository;

import com.otakumap.domain.transaction.entity.Transaction;
import com.otakumap.domain.transaction.enums.TransactionType;
import com.otakumap.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
    List<Transaction> findAllByPoint_UserAndType(User user, TransactionType type, Pageable pageRequest);
}