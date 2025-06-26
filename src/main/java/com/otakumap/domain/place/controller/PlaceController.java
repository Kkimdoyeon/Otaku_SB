package com.otakumap.domain.place.controller;

import com.otakumap.domain.mapping.PlaceAnimation;
import com.otakumap.domain.place.DTO.PlaceResponseDTO;
import com.otakumap.domain.place.converter.PlaceConverter;
import com.otakumap.domain.place.service.PlaceQueryService;
import com.otakumap.global.apiPayload.ApiResponse;
import com.otakumap.global.validation.annotation.ExistPlace;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
@Validated
public class PlaceController {
    private final PlaceQueryService placeQueryService;

    @GetMapping("/{placeId}/animations")
    @Operation(summary = "해당 장소와 관련된 애니메이션 목록 조회", description = "해당 장소와 관련된 애니메이션 목록을 조회합니다. PlaceAnimationId도 함께 반환되며, 이를 후기 작성 시 사용할 수 있습니다.")
    public ApiResponse<PlaceResponseDTO.PlaceAnimationListDTO> getPlaceAnimations(@RequestParam @ExistPlace Long placeId) {
        List<PlaceAnimation> placeAnimations = placeQueryService.getPlaceAnimations(placeId);
        return ApiResponse.onSuccess(PlaceConverter.toPlaceAnimationListDTO(placeAnimations));
    }

}