package com.niangsa.dream_shop.repository;

import com.niangsa.dream_shop.entites.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
