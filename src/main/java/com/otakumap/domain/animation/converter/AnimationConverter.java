package com.otakumap.domain.animation.converter;

import com.otakumap.domain.animation.dto.AnimationResponseDTO;
import com.otakumap.domain.animation.entity.Animation;
import com.otakumap.domain.hash_tag.dto.HashTagResponseDTO;
import com.otakumap.domain.mapping.PlaceAnimation;

import java.util.List;

public class AnimationConverter {

    public static AnimationResponseDTO.AnimationInfoDTO toAnimationInfoDTO(PlaceAnimation placeAnimation, Boolean isLiked,
                                                                           List<HashTagResponseDTO.HashTagDTO> hashTags) {
        return AnimationResponseDTO.AnimationInfoDTO.builder()
                .animationId(placeAnimation.getAnimation().getId())
                .animationName(placeAnimation.getAnimation().getName())
                .isLiked(isLiked)
                .hashTags(hashTags)
                .build();
    }

    public static AnimationResponseDTO.AnimationResultDTO animationResultDTO(Animation animation) {
        return AnimationResponseDTO.AnimationResultDTO.builder()
                .animationId(animation.getId())
                .name(animation.getName())
                .build();
    }

    public static AnimationResponseDTO.AnimationResultListDTO animationResultListDTO(List<Animation> animations) {
        List<AnimationResponseDTO.AnimationResultDTO> animationResultDTOs = animations.stream()
                .map(AnimationConverter::animationResultDTO)
                .toList();

        return AnimationResponseDTO.AnimationResultListDTO.builder()
                .animations(animationResultDTOs)
                .listSize(animationResultDTOs.size())
                .build();
    }

    public static AnimationResponseDTO.AnimationCreationResponseDTO toAnimationCreationResponseDTO(Animation animation) {
        return AnimationResponseDTO.AnimationCreationResponseDTO.builder()
                .animationId(animation.getId())
                .name(animation.getName())
                .createdAt(animation.getCreatedAt())
                .build();
    }

    public static Animation toAnimation(String name) {
        return Animation.builder()
                .name(name)
                .build();
    }
}
