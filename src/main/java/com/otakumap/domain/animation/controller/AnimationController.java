package com.otakumap.domain.animation.controller;

import com.otakumap.domain.animation.dto.AnimationRequestDTO;
import com.otakumap.domain.animation.dto.AnimationResponseDTO;
import com.otakumap.domain.animation.converter.AnimationConverter;
import com.otakumap.domain.animation.entity.Animation;
import com.otakumap.domain.animation.service.AnimationCommandService;
import com.otakumap.domain.animation.service.AnimationQueryService;
import com.otakumap.global.apiPayload.ApiResponse;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.apiPayload.exception.handler.SearchHandler;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/animations")
public class AnimationController {
    private final AnimationQueryService animationQueryService;
    private final AnimationCommandService animationCommandService;

    @GetMapping("/search")
    @Operation(summary = "애니메이션 검색", description = "키워드로 애니메이션 제목을 검색해서 조회합니다. 공백은 허용되지 않습니다.")
    public ApiResponse<AnimationResponseDTO.AnimationResultListDTO> searchAnimation(
            @RequestParam @NotBlank(message = "검색어를 입력해주세요")
            @Pattern(regexp = "^[^\\s].*$", message = "첫 글자는 공백이 될 수 없습니다") String keyword) {
        List<Animation> animationList = animationQueryService.searchAnimation(keyword);

        if (animationList.isEmpty()) {
            throw new SearchHandler(ErrorStatus.ANIMATION_NOT_FOUND);
        }

        return ApiResponse.onSuccess(AnimationConverter.animationResultListDTO(animationList));
    }

    @PostMapping
    @Operation(summary = "애니메이션 등록", description = "원하는 애니메이션이 없을 경우, 사용자가 애니메이션을 직접 등록합니다.")
    public ApiResponse<AnimationResponseDTO.AnimationCreationResponseDTO> createAnimation(
            @RequestBody @Valid AnimationRequestDTO.AnimationCreationRequestDTO request) {
        Animation animation = animationCommandService.createAnimation(request.getName());
        return ApiResponse.onSuccess(AnimationConverter.toAnimationCreationResponseDTO(animation));
    }
}
