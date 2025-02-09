package com.niangsa.dream_shop.service.interfaces;
import com.niangsa.dream_shop.dto.CategoryDto;

public interface ICategoryService extends IAbstractService<CategoryDto> {
    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto getCategoryByName(String name);
}
