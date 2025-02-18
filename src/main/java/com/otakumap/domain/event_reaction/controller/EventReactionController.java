package com.otakumap.domain.event_reaction.controller;

import com.otakumap.domain.auth.jwt.annotation.CurrentUser;
import com.otakumap.domain.event_reaction.dto.EventReactionResponseDTO;
import com.otakumap.domain.event_reaction.converter.EventReactionConverter;
import com.otakumap.domain.event_reaction.entity.EventReaction;
import com.otakumap.domain.event_reaction.service.EventReactionCommandService;
import com.otakumap.domain.user.entity.User;
import com.otakumap.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events/short-reviews/{reviewId}/reaction")
public class EventReactionController {
    private final EventReactionCommandService eventReactionCommandService;

    @PostMapping
    @Operation(summary = "이벤트 한줄 리뷰에 좋아요/싫어요 남기기 및 취소하기", description = "0을 요청하면 dislike, 1을 요청하면 like이며, 이미 존재하는 반응을 요청하면 취소됩니다.")
    public ApiResponse<EventReactionResponseDTO.ReactionResponseDTO> reactToReview(
            @CurrentUser User user, @PathVariable Long reviewId, @Valid @RequestBody int reactionType) {
        EventReaction eventReaction = eventReactionCommandService.reactToReview(user, reviewId, reactionType);
        return ApiResponse.onSuccess(EventReactionConverter.toReactionResponseDTO(eventReaction.getEventShortReview(), eventReaction));
    }
}
