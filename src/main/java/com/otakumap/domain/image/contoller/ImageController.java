package com.otakumap.domain.image.contoller;

import com.otakumap.domain.image.dto.ImageRequestDTO;
import com.otakumap.domain.image.entity.Image;
import com.otakumap.domain.image.service.ImageCommandService;
import com.otakumap.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController {
    private final ImageCommandService imageCommandService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "이미지 업로드", description = "폴더를 선택하여 이미지를 업로드합니다.")
    public ApiResponse<String> uploadImage(@Parameter(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE), description = "폴더는 profile, review, event 중 하나를 선택해주세요.")
                                           @RequestPart("folder") @Valid ImageRequestDTO.uploadDTO folder,
                                           @RequestPart("image") MultipartFile image ) {
        Image uploadedImage = imageCommandService.uploadImage(image, folder.getFolder());
        return ApiResponse.onSuccess("이미지가 성공적으로 업로드되었습니다. URL: " + uploadedImage.getFileUrl());
    }
}
