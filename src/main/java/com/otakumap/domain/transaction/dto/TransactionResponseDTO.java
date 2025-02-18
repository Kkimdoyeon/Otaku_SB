package com.otakumap.domain.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class TransactionResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransactionDTO {
        private String title;
        private Integer point;
        private LocalDateTime purchasedAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransactionListDTO {
        private List<TransactionResponseDTO.TransactionDTO> transactions;
        private Integer currentPage;
        private Integer totalPages;
        private Integer totalElements;
        private Boolean isLast;
    }
}
