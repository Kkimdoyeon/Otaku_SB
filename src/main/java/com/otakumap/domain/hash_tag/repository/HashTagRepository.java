package com.otakumap.domain.hash_tag.repository;

import com.otakumap.domain.hash_tag.entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {
}
