package com.otakumap.domain.point.controller;

import com.otakumap.domain.auth.jwt.annotation.CurrentUser;
import com.otakumap.domain.point.dto.PointRequestDTO;
import com.otakumap.domain.point.service.PointCommandService;
import com.otakumap.domain.user.entity.User;
import com.otakumap.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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

    @Operation(summary = "포인트 충전", description = "사용자가 포인트를 충전합니다.")
    @PostMapping("/charge")
    public ApiResponse<String> chargePoints(
            @RequestBody @Valid PointRequestDTO.PointChargeDTO request,
            @CurrentUser User user
    ) {
        pointCommandService.chargePoints(user, request.getPoint());
        return ApiResponse.onSuccess("충전 성공하였습니다.");
    }
}