package com.otakumap.domain.transaction.converter;

import com.otakumap.domain.transaction.dto.TransactionResponseDTO;
import com.otakumap.domain.transaction.entity.Transaction;
import org.springframework.data.domain.Page;

public class TransactionConverter {

    public static TransactionResponseDTO.TransactionDTO toTransactionDTO(Transaction transaction) {
        String title;
        if(transaction.getEventReview() == null) {
            title = transaction.getPlaceReview().getTitle();
        } else {
            title = transaction.getEventReview().getTitle();
        }

        return TransactionResponseDTO.TransactionDTO.builder()
                .title(title)
                .point(transaction.getAmount())
                .purchasedAt(transaction.getCreatedAt())
                .build();
    }

    public static TransactionResponseDTO.TransactionListDTO toTransactionListDTO(Page<TransactionResponseDTO.TransactionDTO> transactions) {
        return TransactionResponseDTO.TransactionListDTO.builder()
                .transactions(transactions.getContent())
                .totalPages(transactions.getTotalPages())
                .totalElements(transactions.getNumber())
                .isLast(transactions.isLast())
                .build();

    }
}
