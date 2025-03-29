package com.niangsa.dream_shop.controllers;

import com.niangsa.dream_shop.dto.ProductDto;
import com.niangsa.dream_shop.entities.Product;
import com.niangsa.dream_shop.response.ApiResponse;
import com.niangsa.dream_shop.service.product.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RequestMapping("/products")
@RequiredArgsConstructor
@RestController
public class ProductController {
    //inject service
    private  final IProductService productService;

    /**
     *
     * @param pageNumber int
     * @param pageSize int represent the index
     * @return Page of products
     */
    @GetMapping("")
    public Page<ProductDto> getAllProducts(
            @RequestParam(defaultValue = "0",name = "page",required = false) int pageNumber,
            @RequestParam(name = "size", defaultValue = "20",required = false) int pageSize){
        return  productService.getPaginatedProducts(pageNumber,pageSize);
    }

    /***
     * create new product
     * @param product
     * on success Http status 201
     */
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody Product product){
          productService.saveProduct(product);
          return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Created success ", product));
    }


    /**
     *
     * @param id
     * @param productDto
     * @return
     */
    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id,@Valid @RequestBody ProductDto productDto){
            productService.update(id, productDto);
            return  ResponseEntity.ok().body(new ApiResponse("Updated success ", productDto));
    }

    /**
     *
     * @param idProduct long
     * @return Http Status 200
     */
    @DeleteMapping("/{idProduct}/delete")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long idProduct){
            productService.delete(idProduct);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("Deleted success ", idProduct));

    }

    @GetMapping("/search/by")
    public ResponseEntity<ProductDto> searchProductByName(@RequestParam  String name){
     return  ResponseEntity.ok(productService.getProductByName(name));
    }

    /**
     *
     * @param name's product
     * @param brand's name
     * @return product  counter
     */

    @GetMapping("/count/by-name-brand")
    public  ResponseEntity<Long> getCountByProductNameAndBrand(@RequestParam String name, @RequestParam String brand){
            return   ResponseEntity.ok(productService.countByBrandAndName(name,brand));
    }

    @GetMapping("/search-by")
    public ResponseEntity<List<ProductDto>> getProductByBrand(@RequestParam String brand){
      return ResponseEntity.ok( productService.getProductByBrand(brand));
    }

    @GetMapping("/search-by/{category}/all")
    public  ResponseEntity<List<ProductDto>> searchProductByCategory(@PathVariable String category){
        return ResponseEntity.ok(productService.getProductByCategory(category));
    }

    @GetMapping("/search-by/category-and-brand")
    public ResponseEntity<List<ProductDto>> getProductByCategoryAndBrand(@RequestParam(name = "category") String category,@RequestParam(name = "brand") String brand){

                return ResponseEntity.ok(productService.getProductByCategoryAndBand(brand,category));
    }

    @GetMapping("/search-by/name-and-brand")
    public ResponseEntity<List<ProductDto>> getProductByNameAndBrand(@RequestParam String name,@RequestParam String brand){
        return  ResponseEntity.ok(productService.getProductByNameAndBrand(name,brand));
    }

    @GetMapping("/search-by/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId){
        return  ResponseEntity.ok(productService.getById(productId));
    }

    @GetMapping("/search-by/prices")
    public ResponseEntity<Page<ProductDto>> filterProductByMinimumAndMaximumPrice(
            @RequestParam(defaultValue = "20",required = false) int pageNumber,
            @RequestParam(defaultValue = "1") int pageSize,
            @RequestParam(name = "min") BigDecimal minPrice,
            @RequestParam(name = "max") BigDecimal maxPrice
    ){
        return  ResponseEntity.ok(productService.getProductByMinMaxPrice(minPrice, maxPrice,pageNumber, pageSize));
    }
}
