package com.otakumap.domain.user.repository;

import com.otakumap.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    Optional<User> findByEmail(String email);
    boolean existsByNickname(String nickname);
    boolean existsByUserId(String userId);
    Optional<User> findByNameAndEmail(String name, String email);
    Optional<User> findByNameAndUserId(String name, String userId);
}
