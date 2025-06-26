package com.otakumap.domain.place_like.service;

import com.otakumap.domain.place_like.dto.PlaceLikeResponseDTO;
import com.otakumap.domain.place_like.entity.PlaceLike;
import com.otakumap.domain.user.entity.User;

import java.util.List;

public interface PlaceLikeQueryService {
    PlaceLikeResponseDTO.PlaceLikePreViewListDTO getPlaceLikeList(User user, Boolean isFavorite, Long lastId, int limit);
    PlaceLikeResponseDTO.PlaceLikePreViewListDTO createPlaceLikePreviewListDTO(List<PlaceLike> placeLikes, int limit);
    boolean isPlaceLikeExist(Long id);
    PlaceLikeResponseDTO.PlaceLikeDetailDTO getPlaceLike(Long placeLikeId);
}