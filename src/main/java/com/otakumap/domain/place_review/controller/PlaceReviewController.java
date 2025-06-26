package com.otakumap.domain.place_review.controller;

import com.otakumap.domain.place_review.dto.PlaceReviewResponseDTO;
import com.otakumap.domain.place_review.service.PlaceReviewQueryService;
import com.otakumap.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PlaceReviewController {
    private final PlaceReviewQueryService placeReviewQueryService;

    @GetMapping("/places/{placeId}/reviews")
    @Operation(summary = "특정 장소의 전체 후기 조회", description = "특정 장소의 후기들을 조회합니다")
    @Parameters({
            @Parameter(name = "placeId", description = "특정 장소의 id 입니다."),
            @Parameter(name = "page", description = "페이지 번호 (0부터 시작)", example = "0"),
            @Parameter(name = "size", description = "한 페이지당 최대 리뷰 수", example = "10"),
            @Parameter(name = "sort", description = "정렬 기준 (latest 또는 views)", example = "latest")
    })
    public ApiResponse<PlaceReviewResponseDTO.PlaceAnimationReviewDTO> getPlaceReviewList(@PathVariable Long placeId,
                                                                                          @RequestParam(defaultValue = "0") int page,
                                                                                          @RequestParam(defaultValue = "10") int size,
                                                                                          @RequestParam(defaultValue = "latest") String sort) {

        PlaceReviewResponseDTO.PlaceAnimationReviewDTO results = placeReviewQueryService.getReviewsByPlace(placeId, page, size, sort);

        return ApiResponse.onSuccess(results);
    }
}
