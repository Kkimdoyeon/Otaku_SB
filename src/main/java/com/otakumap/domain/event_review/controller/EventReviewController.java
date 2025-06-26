package com.otakumap.domain.event_review.controller;


import com.otakumap.domain.event_review.converter.EventReviewConverter;
import com.otakumap.domain.event_review.dto.EventReviewResponseDTO;
import com.otakumap.domain.event_review.service.EventReviewCommandService;
import com.otakumap.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api")
public class EventReviewController {

    private final EventReviewCommandService eventReviewCommandService;

    @GetMapping("/events/{eventId}/reviews")
    @Operation(summary = "특정 이벤트의 후기 목록 조회", description = "특정 이벤트의 후기 목록(4개씩)을 불러옵니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "eventId", description = "이벤트의 아이디입니다."),
            @Parameter(name = "page", description = "페이지 번호입니다. 0부터 시작합니다.", example = "0")
    })
    public ApiResponse<EventReviewResponseDTO.EventReviewPreViewListDTO> getEventReviewList(@PathVariable(name = "eventId") Long eventId, @RequestParam(name = "page") Integer page) {
        return ApiResponse.onSuccess(EventReviewConverter.eventReviewPreViewListDTO(eventReviewCommandService.getEventReviews(eventId, page)));
    }
}
