package com.otakumap.domain.point.controller;

import com.otakumap.domain.auth.jwt.annotation.CurrentUser;
import com.otakumap.domain.point.converter.PointConverter;
import com.otakumap.domain.point.dto.PointRequestDTO;
import com.otakumap.domain.point.dto.PointResponseDTO;
import com.otakumap.domain.point.service.PointCommandService;
import com.otakumap.domain.point.service.PointQueryservice;
import com.otakumap.domain.user.entity.User;
import com.otakumap.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/points")
@RequiredArgsConstructor
@Validated
public class PointController {
    private final PointCommandService pointCommandService;
    private final PointQueryservice pointQueryservice;

    @Operation(summary = "포인트 충전", description = "사용자가 포인트를 충전합니다.")
    @PostMapping("/charge")
    public ApiResponse<String> chargePoints(
            @RequestBody @Valid PointRequestDTO.PointChargeDTO request,
            @CurrentUser User user
    ) {
        pointCommandService.chargePoints(user, request.getPoint(), request.getMerchantUid());
        return ApiResponse.onSuccess("충전 성공하였습니다.");
    }

    @Operation(summary = "포인트 충전 내역 확인", description = "포인트 충전 내역을 확인합니다. page는 1부터 시작합니다.")
        @PostMapping("/transactions/charges")
        public ApiResponse<PointResponseDTO.PointPreViewListDTO> getChargePointList(@CurrentUser User user, @RequestParam(name = "page") Integer page) {
            return ApiResponse.onSuccess(PointConverter.pointPreViewListDTO(pointQueryservice.getChargePointList(user, page)));
    }
}