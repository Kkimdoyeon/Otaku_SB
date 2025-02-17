package com.otakumap.domain.place_like.service;

import com.otakumap.domain.mapping.PlaceAnimation;
import com.otakumap.domain.place.repository.PlaceRepository;
import com.otakumap.domain.place_animation.repository.PlaceAnimationRepository;
import com.otakumap.domain.place_like.converter.PlaceLikeConverter;
import com.otakumap.domain.place_like.dto.PlaceLikeRequestDTO;
import com.otakumap.domain.place_like.entity.PlaceLike;
import com.otakumap.domain.place_like.repository.PlaceLikeRepository;
import com.otakumap.domain.user.entity.User;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.apiPayload.exception.handler.PlaceHandler;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaceLikeCommandServiceImpl implements PlaceLikeCommandService {
    private final PlaceLikeRepository placeLikeRepository;
    private final EntityManager entityManager;
    private final PlaceAnimationRepository placeAnimationRepository;

    @Override
    public void deletePlaceLike(List<Long> placeIds) {
        placeLikeRepository.deleteAllByIdInBatch(placeIds);
        entityManager.flush();
        entityManager.clear();
    }

    @Override
    public void deletePlaceLike(Long placeId, Long animationId, User user) {
        PlaceAnimation placeAnimation = placeAnimationRepository.findByPlaceIdAndAnimationId(placeId, animationId).orElseThrow(() -> new PlaceHandler(ErrorStatus.PLACE_ANIMATION_NOT_FOUND));
        PlaceLike placeLike = placeLikeRepository.findByUserAndPlaceAnimation(user, placeAnimation).orElseThrow(() -> new PlaceHandler(ErrorStatus.PLACE_LIKE_NOT_FOUND));

        placeLikeRepository.delete(placeLike);
    }

    @Override
    public void savePlaceLike(User user, Long placeId, PlaceLikeRequestDTO.SavePlaceLikeDTO request) {
        PlaceAnimation placeAnimation = placeAnimationRepository.findByPlaceIdAndAnimationId(placeId, request.getAnimationId()).orElseThrow(() -> new PlaceHandler(ErrorStatus.PLACE_ANIMATION_NOT_FOUND));
        if (placeLikeRepository.existsByUserAndPlaceAnimation(user, placeAnimation)) {
            throw new PlaceHandler(ErrorStatus.PLACE_LIKE_ALREADY_EXISTS);
        }
        placeLikeRepository.save(PlaceLikeConverter.toPlaceLike(user, placeAnimation));
    }

    @Override
    public PlaceLike favoritePlaceLike(Long placeLikeId, PlaceLikeRequestDTO.FavoriteDTO request) {
        PlaceLike placeLike = placeLikeRepository.findById(placeLikeId).orElseThrow(() -> new PlaceHandler(ErrorStatus.PLACE_LIKE_NOT_FOUND));
        placeLike.setIsFavorite(request.getIsFavorite());
        return placeLikeRepository.save(placeLike);
    }
}