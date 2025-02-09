package com.niangsa.dream_shop.mappers;

import com.niangsa.dream_shop.dto.CategoryDto;
import com.niangsa.dream_shop.entities.Category;
import org.mapstruct.Mapper;

@Mapper( )
public interface CategoryMapper{
    Category toEntity(CategoryDto categoryDto);
    CategoryDto toDto(Category category);
}
