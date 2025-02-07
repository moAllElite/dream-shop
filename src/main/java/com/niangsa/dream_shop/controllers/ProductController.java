package com.niangsa.dream_shop.controllers;

import com.niangsa.dream_shop.dto.ProductDto;
import com.niangsa.dream_shop.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/products")
@RequiredArgsConstructor
@RestController
public class ProductController {
    //inject service
    private  final IProductService productService;


    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> getAllProducts(){
        return  productService.getAll();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public  void createProduct(@RequestBody ProductDto productDto){
        productService.save(productDto);
    }

    @PutMapping()
    public ProductDto update(@PathVariable Long id,@RequestBody ProductDto productDto){
        return  productService.update(id,productDto);
    }

    @DeleteMapping()
    public void  delete(@PathVariable Long id){
        productService.delete(id);
    }

}
