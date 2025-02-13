package com.niangsa.dream_shop.controllers;

import com.niangsa.dream_shop.dto.ProductDto;
import com.niangsa.dream_shop.response.ApiResponse;
import com.niangsa.dream_shop.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin("*")
@RequestMapping("/products")
@RequiredArgsConstructor
@RestController
public class ProductController {
    private static final HttpStatus INTERNAL_ERROR_SERVER = HttpStatus.INTERNAL_SERVER_ERROR;
    //inject service
    private  final IProductService productService;

    /**
     * get all informations about products
     * @return List<ProductDTO>
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> getAllProducts(){
        return  productService.getAll();
    }

    /***
     * create new product
     * @param product
     * on success Http status 201
     */
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto product){
        try {
          productService.saveProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Created success ", null));
        } catch (Exception e) {
            return  ResponseEntity.status(INTERNAL_ERROR_SERVER).body(new ApiResponse("Create failed", e.getMessage()));
        }
    }


    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody ProductDto productDto){
        try {
            productService.update(id, productDto);
            return  ResponseEntity.ok().body(new ApiResponse("Updated success ", productDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_ERROR_SERVER).body(new ApiResponse("Update failed", INTERNAL_ERROR_SERVER));
        }
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
    public ResponseEntity<List<ProductDto>> searchProductByName(@RequestParam  String name){
     return  ResponseEntity.ok(productService.getProductByName(name));
    }

    /**
     *
     * @param name string
     * @param brand string
     * @return product  counter
     */

    @GetMapping("/count/by-name-brand")
    public  ResponseEntity<Long> getCountByProductNameAndBrand(@RequestParam String name, @RequestParam String brand){
            return   ResponseEntity.ok(productService.countByBrandAndName(name,brand));
    }

    @GetMapping("/find-by")
    public ResponseEntity<List<ProductDto>> getProductByBrand(@RequestParam String brand){
      return ResponseEntity.ok( productService.getProductByBrand(brand));
    }

    @GetMapping("/{category}/all")
    public  ResponseEntity<List<ProductDto>> searchProductByCategory(@PathVariable String category){
        return ResponseEntity.ok(productService.getProductByCategory(category));
    }

    @GetMapping("/search-by/category-and-brand")
    public ResponseEntity<List<ProductDto>> getProductByCategoryAndBrand(@RequestParam(name = "category") String category,@RequestParam(name = "brand") String brand){

                return ResponseEntity.ok(productService.getProductByCategoryAndBand(brand,category));
    }

    @GetMapping("/by/name-and-brand")
    public ResponseEntity<List<ProductDto>> getProductByNameAndBrand(@RequestParam String name,@RequestParam String brand){
        return  ResponseEntity.ok(productService.getProductByNameAndBrand(name,brand));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId){
        return  ResponseEntity.ok(productService.getById(productId));
    }
}
