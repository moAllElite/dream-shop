package com.niangsa.dream_shop.mappers;

import com.niangsa.dream_shop.dto.ProductDto;

public interface IAbstractMapper <T,D>{
    T toEntity(D d );
    D toDto(T t);
}
