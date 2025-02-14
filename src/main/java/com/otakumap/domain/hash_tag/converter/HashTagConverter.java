package com.otakumap.domain.hash_tag.converter;

import com.otakumap.domain.hash_tag.dto.HashTagResponseDTO;
import com.otakumap.domain.hash_tag.entity.HashTag;

public class HashTagConverter {
    public static HashTagResponseDTO.HashTagDTO toHashTagDTO(HashTag hashTag) {
        return HashTagResponseDTO.HashTagDTO.builder()
                .hashTagId(hashTag.getId())
                .name(hashTag.getName())
                .build();
    }
}
