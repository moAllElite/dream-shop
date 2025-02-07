package com.niangsa.dream_shop.repository;

import com.niangsa.dream_shop.entites.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
