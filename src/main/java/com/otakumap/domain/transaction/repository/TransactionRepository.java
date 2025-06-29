package com.otakumap.domain.transaction.repository;

import com.otakumap.domain.event_review.entity.EventReview;
import com.otakumap.domain.place_review.entity.PlaceReview;
import com.otakumap.domain.transaction.entity.Transaction;
import com.otakumap.domain.transaction.enums.TransactionType;
import com.otakumap.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
    List<Transaction> findAllByPoint_UserAndType(User user, TransactionType type, Pageable pageRequest);
    Boolean existsByPoint_UserAndEventReview(User user, EventReview review);
    Boolean existsByPoint_UserAndPlaceReview(User user, PlaceReview review);
}