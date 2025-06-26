package com.otakumap.domain.search.service;

import com.otakumap.domain.search.dto.SearchResponseDTO;
import com.otakumap.domain.user.entity.User;

import java.util.List;

public interface SearchService {
    List<SearchResponseDTO.SearchResultDTO> getSearchedResult (User user, String keyword);
}
