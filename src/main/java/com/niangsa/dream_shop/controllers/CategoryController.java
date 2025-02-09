package com.niangsa.dream_shop.controllers;

import com.niangsa.dream_shop.dto.CategoryDto;
import com.niangsa.dream_shop.service.interfaces.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/categories")
@RequiredArgsConstructor
@RestController
public class CategoryController {
    private final ICategoryService categoryService;

    /**
     * get all categories
     */
    @GetMapping()
    public ResponseEntity<List<CategoryDto>> getAll(){
        return ResponseEntity.ok(categoryService.getAll());
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<CategoryDto> update(@PathVariable Long id,CategoryDto categoryDto){
        return  ResponseEntity.ok(categoryService.update(id,categoryDto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<CategoryDto> delete(@PathVariable Long id){
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getById(id));
    }

    /**
     *
     * @param keyword String category's name
     * @return category
     */
    @GetMapping("/{keyword}/category")
    public ResponseEntity<CategoryDto> search(@PathVariable String keyword){
        return  ResponseEntity.ok(categoryService.getCategoryByName(keyword));
    }
}
