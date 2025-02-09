package com.niangsa.dream_shop.service.interfaces;

import com.niangsa.dream_shop.dto.ProductDto;

import  java.util.List;
public interface IProductService extends  IAbstractService<ProductDto> {
    ProductDto saveProduct(ProductDto productDto);
    List<ProductDto> getProductByBrand(String brand);
    List<ProductDto> getProductByCategory(String category);
    List<ProductDto> getProductByCategoryAndBand(String brand, String category);
    List<ProductDto> getProductByName(String name);
    Long countByBrandAndName(String brand,String name);
    List<ProductDto> getProductByNameAndBrand(String brand,String name);
}
