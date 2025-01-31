package com.otakumap.domain.reviews.controller;

import com.otakumap.domain.reviews.dto.ReviewResponseDTO;
import com.otakumap.domain.reviews.enums.ReviewType;
import com.otakumap.domain.reviews.service.ReviewQueryService;
import com.otakumap.global.apiPayload.ApiResponse;
import com.otakumap.global.validation.annotation.ValidReviewId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Validated
public class ReviewController {

    private final ReviewQueryService reviewQueryService;

    @GetMapping("/reviews/top7")
    @Operation(summary = "조회수 Top7 여행 후기 목록 조회", description = "조회수 Top7 여행 후기 목록을 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ApiResponse<ReviewResponseDTO.Top7ReviewPreViewListDTO> getTop7ReviewList() {
        return null;
    }

    @GetMapping("/reviews/search")
    @Operation(summary = "키워드로 여행 후기 검색", description = "키워드로 여행 후기를 검색해서 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "keyword", description = "검색 키워드입니다."),
            @Parameter(name = "page", description = "페이지 번호 (0부터 시작)", example = "0"),
            @Parameter(name = "size", description = "한 페이지당 최대 리뷰 수", example = "10"),
            @Parameter(name = "sort", description = "정렬 기준 (latest 또는 views)", example = "latest")
    })
    public ApiResponse<Page<ReviewResponseDTO.SearchedReviewPreViewDTO>> getSearchedReviewList(@RequestParam String keyword,
                                                                                               @RequestParam(defaultValue = "0") int page,
                                                                                               @RequestParam(defaultValue = "10") int size,
                                                                                               @RequestParam(defaultValue = "latest") String sort) {

        Page<ReviewResponseDTO.SearchedReviewPreViewDTO> searchResults = reviewQueryService.searchReviewsByKeyword(keyword, page, size, sort);

        return ApiResponse.onSuccess(searchResults);
    }

    @GetMapping("/reviews/{reviewId}")
    @Operation(summary = "특정 여행 후기 조회", description = "특정 여행 후기를 상세 페이지에서 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "reviewId", description = "이벤트 or 명소의 후기 id 입니다."),
            @Parameter(name = "type", description = "리뷰의 종류를 특정합니다. 'EVENT' 또는 'PLACE' 여야 합니다.")
    })
    public ApiResponse<ReviewResponseDTO.ReviewDetailDTO> getReviewDetail(@PathVariable @ValidReviewId Long reviewId, @RequestParam(defaultValue = "PLACE") ReviewType type) {

        return ApiResponse.onSuccess(reviewQueryService.getReviewDetail(reviewId, type));
    }
}
