package com.otakumap.domain.transaction.dto;

import com.otakumap.domain.event_review.entity.EventReview;
import com.otakumap.domain.place_review.entity.PlaceReview;
import com.otakumap.domain.point.entity.Point;
import com.otakumap.domain.transaction.entity.Transaction;
import com.otakumap.domain.transaction.enums.TransactionType;
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

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransactionDetailDTO {
        private Long id;
        private Long pointId;
        private TransactionType type;
        private int amount;
        private String impUid;
        private String merchantUid;
        private Long eventReviewId;
        private Long placeReviewId;
        public Transaction toEntity(Point point, EventReview eventReview, PlaceReview placeReview) {
            Transaction.TransactionBuilder builder = Transaction.builder()
                    .point(point)
                    .type(type)
                    .amount(amount)
                    .impUid(impUid)
                    .merchantUid(merchantUid);

            if (eventReview != null) {
                builder.eventReview(eventReview);
            } else {
                builder.placeReview(placeReview);
            }

            return builder.build();
        }
    }
}
