package com.niangsa.dream_shop.repositories;

import com.niangsa.dream_shop.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long>, PagingAndSortingRepository<Product,Long> {

    Product findByName(String name);
    List<Product> findByCategoryName(String categoryName);
    Optional<List<Product>> findProductsByBrand(String brand);
    List<Product> findByBrandAndName(String brand, String name);
    List<Product> findProductByBrandAndCategoryName(String brand, String categoryName);
    Long countByBrandAndName(String brand,String name);
    boolean existsByNameAndBrand(String name, String brand);
    Page<Product> findByPriceBetween(BigDecimal low, BigDecimal high, Pageable pageable);
}
