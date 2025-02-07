package com.niangsa.dream_shop.repository;

import com.niangsa.dream_shop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> findProductByName(String name);
    Optional<Product> finProductByCategory(String category);
    Optional<Product> findProductByBrand(String brand);
    Optional<Product> findProductByCategoryAndBrand(String brand,String category);
}
