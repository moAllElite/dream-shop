package com.niangsa.dream_shop.service.category;
import com.niangsa.dream_shop.dto.CategoryDto;
import com.niangsa.dream_shop.service.IAbstractService;

public interface ICategoryService extends IAbstractService<CategoryDto> {
    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto getCategoryByName(String name);
}
