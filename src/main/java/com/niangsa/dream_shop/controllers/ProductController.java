package com.niangsa.dream_shop.controllers;

import com.niangsa.dream_shop.dto.ProductDto;
import com.niangsa.dream_shop.response.ApiResponse;
import com.niangsa.dream_shop.service.interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/products")
@RequiredArgsConstructor
@RestController
public class ProductController {
    private static final HttpStatus INTERNAL_ERROR_SERVER = HttpStatus.INTERNAL_SERVER_ERROR;
    private static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;
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
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto product){
        try {
           ProductDto a= productService.saveProduct(product);
           System.out.println(a);
            return ResponseEntity.ok().body(new ApiResponse("Created success ", null));
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
        try {
            productService.delete(idProduct);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("Deleted success ", idProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_ERROR_SERVER).body(new ApiResponse("Delete failed ", null));
        }
    }

    @GetMapping("/by/product-name")
    public ResponseEntity<ApiResponse> searchProductByName(@RequestParam  String name){
        try {
            List<ProductDto> productDtos =productService.getProductByName(name);
            if(productDtos.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(String.format("No product were found with name %s",name),null));
            }
            return ResponseEntity.ok().body(new ApiResponse("Search product by name ", productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    /**
     *
     * @param name string
     * @param brand string
     * @return product  counter
     */

    @GetMapping("/count/by-name-brand")
    public  ResponseEntity<ApiResponse> getCountByProductNameAndBrand(
            @RequestParam String name,
            @RequestParam String brand
    ){
        try {
            Long counter = productService.countByBrandAndName(name,brand);
            return   ResponseEntity.ok().body(new ApiResponse("Success",counter));
        } catch (Exception e) {
           return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse( e.getMessage(),null));
        }
    }

    @GetMapping("/find-by")
    public ResponseEntity<List<ProductDto>> getProductByBrand(@RequestParam(name = "brand") String brand){
        List<ProductDto> productDtos = productService.getProductByBrand(brand);
        System.out.println(productDtos);
      return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/{category}/all")
    public  ResponseEntity<ApiResponse> searchProductByCategory(@PathVariable String category){
        try {
            List<ProductDto> productDtos =productService.getProductByCategory(category);
            if(productDtos.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(String.format("No product were found with name %s",category),null));
            }
            return ResponseEntity.ok().body(new ApiResponse("Success",productDtos));
        } catch (Exception e) {
            return  ResponseEntity.status(INTERNAL_ERROR_SERVER).body(new ApiResponse("error",e.getMessage()));
        }
    }


    @GetMapping("/by/category-and-name")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category,@RequestParam String brand){
        try {
            List<ProductDto> productDtos = productService.getProductByCategoryAndBand(brand,category);
            if(productDtos.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(String.format("No product were found with name %s",category),null));
            }
            return ResponseEntity.ok().body(new ApiResponse("Success",productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_ERROR_SERVER).body(new ApiResponse("error",e.getMessage()));
        }
    }

    @GetMapping("/by/name-and-brand")
    public ResponseEntity<List<ProductDto>> getProductByNameAndBrand(@RequestParam String name,@RequestParam String brand){
        return  ResponseEntity.ok(productService.getProductByNameAndBrand(name,brand));
    }

}
