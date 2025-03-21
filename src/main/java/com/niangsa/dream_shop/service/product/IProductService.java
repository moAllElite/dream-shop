package com.niangsa.dream_shop.service.product;

import com.niangsa.dream_shop.dto.ProductDto;
import com.niangsa.dream_shop.entities.Product;
import com.niangsa.dream_shop.service.IAbstractService;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import  java.util.List;
public interface IProductService extends IAbstractService<ProductDto> {
    Product saveProduct(Product productDto);
    List<ProductDto> getProductByBrand(String brand);
    List<ProductDto> getProductByCategory(String category);
    List<ProductDto> getProductByCategoryAndBand(String brand, String category);
    ProductDto getProductByName(String name);
    Long countByBrandAndName(String brand,String name);
    List<ProductDto> getProductByNameAndBrand(String brand,String name);

    Page<ProductDto> getProductByMinMaxPrice(BigDecimal minPrice, BigDecimal maxPrice, int pageSize);
}
