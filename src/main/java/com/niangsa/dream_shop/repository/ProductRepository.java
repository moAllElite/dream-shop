package com.niangsa.dream_shop.repository;

import com.niangsa.dream_shop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<List<Product>> findByName(String name);
    List<Product> findByCategoryName(String categoryName);
    Optional<List<Product>> findProductsByBrand(String brand);
    Optional<List<Product>> findByBrandAndName(String brand, String name);
    Optional<List<Product>> findProductByCategoryNameAndBrand(String brand,String category);
    Long countByBrandAndName(String brand,String name);
}
