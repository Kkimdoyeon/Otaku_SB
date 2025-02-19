package com.otakumap.domain.user.service;

import com.otakumap.domain.event_review.entity.EventReview;
import com.otakumap.domain.event_review.repository.EventReviewRepository;
import com.otakumap.domain.place_review.entity.PlaceReview;
import com.otakumap.domain.place_review.repository.PlaceReviewRepository;
import com.otakumap.domain.user.converter.UserConverter;
import com.otakumap.domain.user.dto.UserResponseDTO;
import com.otakumap.domain.user.entity.User;
import com.otakumap.domain.user.repository.UserRepository;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.apiPayload.exception.handler.AuthHandler;
import com.otakumap.global.apiPayload.exception.handler.ReviewHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {
    private final UserRepository userRepository;
    private final PlaceReviewRepository placeReviewRepository;
    private final EventReviewRepository eventReviewRepository;

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new AuthHandler(ErrorStatus.USER_NOT_FOUND));
    }

    @Override
    public User getUserInfo(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new AuthHandler(ErrorStatus.USER_NOT_FOUND));
    }

    @Override
    public Page<UserResponseDTO.UserReviewDTO> getMyReviews(User user, Integer page, String sort) {
        Sort sortOrder = Sort.by(Sort.Order.desc("createdAt"));
        if ("views".equals(sort)) {
            sortOrder = Sort.by(Sort.Order.desc("view"), Sort.Order.desc("createdAt"));
        }

        // 전체 데이터를 먼저 가져옵니다
        List<PlaceReview> placeReviews = placeReviewRepository.findAllByUserId(user.getId());
        List<EventReview> eventReviews = eventReviewRepository.findAllByUserId(user.getId());

        List<UserResponseDTO.UserReviewDTO> reviews = new ArrayList<>();

        // PlaceReview 처리
        placeReviews.forEach(review -> reviews.add(
                UserConverter.reviewDTO(review, getImageUrl(review))
        ));

        // EventReview 처리
        eventReviews.forEach(review -> reviews.add(
                UserConverter.reviewDTO(review, getImageUrl(review))
        ));

        // sort 파라미터에 따른 정렬
        if ("views".equals(sort)) {
            reviews.sort((r1, r2) -> {
                int viewCompare = r2.getViews().compareTo(r1.getViews());
                return viewCompare != 0 ? viewCompare : r2.getCreatedAt().compareTo(r1.getCreatedAt());
            });
        } else {
            reviews.sort((r1, r2) -> r2.getCreatedAt().compareTo(r1.getCreatedAt()));
        }

        // Pagination
        int pageSize = 3;
        int totalSize = reviews.size();
        int totalPages = (int) Math.ceil((double) totalSize / pageSize);

        if (page > totalPages) {
            throw new ReviewHandler(ErrorStatus.PAGE_NOT_FOUND);
        }

        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, totalSize);

        List<UserResponseDTO.UserReviewDTO> paginatedReviews = reviews.subList(start, end);

        return new PageImpl<>(paginatedReviews, PageRequest.of(page - 1, pageSize, sortOrder), totalSize);
    }

    private String getImageUrl(PlaceReview review) {
        return review.getImages() != null && !review.getImages().isEmpty()
                ? review.getImages().get(0).getFileUrl()
                : null;
    }

    private String getImageUrl(EventReview review) {
        return review.getImages() != null && !review.getImages().isEmpty()
                ? review.getImages().get(0).getFileUrl()
                : null;
    }
}
