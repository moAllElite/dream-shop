package com.niangsa.dream_shop.repositories;

import com.niangsa.dream_shop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
   User findByEmail(String email);

}
