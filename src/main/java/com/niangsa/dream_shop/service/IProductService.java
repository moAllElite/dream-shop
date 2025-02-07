package com.niangsa.dream_shop.service;

import com.niangsa.dream_shop.dto.ProductDto;

public interface IProductService extends  IAbstractService<ProductDto> {
    ProductDto getProductByBrand(String brand);
    ProductDto getProductByCategory(String category);
    ProductDto getProductByCategoryAndBand(String brand);
}
