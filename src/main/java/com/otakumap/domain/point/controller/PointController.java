package com.otakumap.domain.point.controller;

import com.otakumap.domain.auth.jwt.annotation.CurrentUser;
import com.otakumap.domain.point.converter.PointConverter;
import com.otakumap.domain.point.dto.PointResponseDTO;
import com.otakumap.domain.point.entity.Point;
import com.otakumap.domain.point.service.PointQueryservice;
import com.otakumap.domain.user.entity.User;
import com.otakumap.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/points")
@RequiredArgsConstructor
@Validated
public class PointController {
    //private final PointCommandService pointCommandService;
    private final PointQueryservice pointQueryservice;

    @Operation(summary = "포인트 충전", description = "사용자가 포인트를 충전합니다.")
    @PostMapping("/charge")
    @Parameters({
            @Parameter(name = "point", description = "충전할 포인트")
    })
    public ApiResponse<String> chargePoints(@RequestParam Long point, @CurrentUser User user) {
        // 포인트 충전 서비스 호출
        //Point pointRecord = pointCommandService.chargePoints(user, point);

        // 성공적인 응답 반환
        //return ApiResponse.onSuccess("충전 성공하였습니다.", pointRecord);
        return null;
    }

    @Operation(summary = "포인트 충전 내역 확인", description = "포인트 충전 내역을 확인합니다. page는 1부터 시작합니다.")
    @PostMapping("/transactions/charges")
    public ApiResponse<PointResponseDTO.PointPreViewListDTO> getChargePointList(@CurrentUser User user, @RequestParam(name = "page") Integer page) {
        return ApiResponse.onSuccess(PointConverter.pointPreViewListDTO(pointQueryservice.getChargePointList(user, page)));
    }

    @Operation(summary = "현재 포인트 조회", description = "현재 포인트를 조회합니다.")
    @PostMapping("/balance")
    public ApiResponse<PointResponseDTO.CurrentPointDTO> getCurrentPointBalance(@CurrentUser User user) {
        return ApiResponse.onSuccess(pointQueryservice.getCurrentPoint(user));
    }
}