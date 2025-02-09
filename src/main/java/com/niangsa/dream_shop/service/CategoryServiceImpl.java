package com.niangsa.dream_shop.service;

import com.niangsa.dream_shop.dto.CategoryDto;
import com.niangsa.dream_shop.entities.Category;
import com.niangsa.dream_shop.exceptions.ApiRequestException;
import com.niangsa.dream_shop.mappers.CategoryMapper;
import com.niangsa.dream_shop.repository.CategoryRepository;
import com.niangsa.dream_shop.service.interfaces.ICategoryService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements ICategoryService {
    // inject repository & mappers
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    /**
     *
     * @param categoryDto from form
     * @return CategoryDto
     */

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        return   Optional.of(category)
                .filter(c-> !categoryRepository.existsByName(category.getName()))
                        .map(categoryMapper::toDto)
                        .orElseThrow( ()-> new EntityExistsException("Category already exist !"));
      //  return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto getCategoryByName(String name) {
        return categoryMapper.toDto(
                categoryRepository.findByName(name)//.orElseThrow( ()-> new EntityExistsException("Category already exist"))
        );
    }

    @Override
    public CategoryDto getById(Long id) {
        return categoryMapper.toDto(
                categoryRepository.findById(id)
                        .orElseThrow( ()-> new EntityExistsException("Category already exist"))
        );
    }

    @Override
    public void delete(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(
                        categoryRepository::delete,
                        ()-> {throw  new ApiRequestException("No category were found");}
                );
    }


    /**
     *
     * @param id long
     * @param categoryDto from form
     * @return categorydto
     */
    @Override
    public CategoryDto update(Long id, CategoryDto categoryDto) {
        if(!categoryRepository.existsById(id)){
            throw  new EntityNotFoundException(String.format("no category were found %s",id));
        }
        categoryDto.setId(id);
        Category save = categoryRepository.save(categoryMapper.toEntity(categoryDto));
        return categoryMapper.toDto(save);
    }


    /**
     *
     * @return list of CategoryDto
     */
    @Override
    public List<CategoryDto> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }
}
