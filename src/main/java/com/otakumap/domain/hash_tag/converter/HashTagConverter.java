package com.otakumap.domain.hash_tag.converter;

import com.otakumap.domain.animation.entity.Animation;
import com.otakumap.domain.hash_tag.dto.HashTagResponseDTO;
import com.otakumap.domain.hash_tag.entity.HashTag;
import com.otakumap.domain.mapping.AnimationHashtag;

public class HashTagConverter {
    public static HashTag toHashTag(String name) {
        return HashTag.builder()
                .name("#" + name)
                .build();

    }

    public static HashTagResponseDTO.HashTagDTO toHashTagDTO(HashTag hashTag) {
        return HashTagResponseDTO.HashTagDTO.builder()
                .hashTagId(hashTag.getId())
                .name(hashTag.getName())
                .build();
    }

    public static AnimationHashtag toAnimationHashTag(Animation animation, HashTag hashTag) {
        return AnimationHashtag.builder()
                .animation(animation)
                .hashTag(hashTag)
                .build();
    }
}
