package com.niangsa.dream_shop.mappers;

import com.niangsa.dream_shop.dto.ProductDto;
import com.niangsa.dream_shop.entities.Product;
import org.mapstruct.Mapper;
@Mapper
public interface ProductMapper extends IAbstractMapper<Product, ProductDto> {
}
