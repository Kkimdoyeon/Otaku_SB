package com.otakumap.domain.image.converter;

import com.otakumap.domain.image.dto.ImageResponseDTO;
import com.otakumap.domain.image.entity.Image;

public class ImageConverter {

    public static ImageResponseDTO.ImageDTO toImageDTO(Image image) {

        if(image == null) {
            return new ImageResponseDTO.ImageDTO();
        }

        return ImageResponseDTO.ImageDTO.builder()
                .id(image.getId())
                .uuid(image.getUuid())
                .fileName(image.getFileName())
                .fileUrl(image.getFileUrl())
                .build();
    }

    public static Image toImage(String uuid, String fileName, String fileUrl) {
        return Image.builder()
                .uuid(uuid)
                .fileName(fileName)
                .fileUrl(fileUrl)
                .build();
    }
}
