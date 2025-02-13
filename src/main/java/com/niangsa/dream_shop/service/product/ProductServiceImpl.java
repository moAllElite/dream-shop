package com.niangsa.dream_shop.service.product;

import com.niangsa.dream_shop.dto.ProductDto;
import com.niangsa.dream_shop.entities.Category;
import com.niangsa.dream_shop.entities.Product;
import com.niangsa.dream_shop.exceptions.ApiRequestException;
import com.niangsa.dream_shop.mappers.ProductMapper;
import com.niangsa.dream_shop.repositories.CategoryRepository;
import com.niangsa.dream_shop.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class ProductServiceImpl implements IProductService {
    //inject repository & mappers
    private final ProductRepository productRepository;
    private final   ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    /***
     *
     * @param  request from formular
     *      1- check if category is found on DB
     *           2-         if yes set it as new category
     *             3-       if no , then save as new category
     */
    @Override
    public ProductDto saveProduct(ProductDto request) {
        String categoryName = request.getCategory().getName();
        Category categoryEntity = Optional.ofNullable(categoryRepository.findByName(categoryName))
                .orElseGet(
                        () -> {
                            Category cat = new  Category(categoryName);
                            return categoryRepository.save(cat);
                        }
                );
       request.setCategory(categoryEntity);
       Product entity= productRepository.save(productMapper.toProductEntity(request));
       return productMapper.toProductDto(entity);
    }




    /**
     * search product by id
     * @param id Long
     * @return ProductDto
     */

    @Override
    public ProductDto getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("No product was found with id:" +id));
        return productMapper.toProductDto(product);
    }

    /**
     * delete product by id
     * @param id Long
     */
    @Override
    public void delete(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(
                        productRepository::delete,
                        ()-> { throw new      EntityNotFoundException("Product not  found");}
                );
    }

    /**
     *
     * @param  id Long
     * @param productDto from formular
     * @return ProductDto
     */
    @Override
    public ProductDto update(Long id, ProductDto productDto) {
        if(!productRepository.existsById(id)) {
            throw  new EntityNotFoundException(String.format("Product not found with provide ID: %s",id));
        }
        productDto.setId(id);
        Product product = productRepository.save(productMapper.toProductEntity(productDto));
        return productMapper.toProductDto(product);
    }

    /**
     *
     * @return  List of  ProductDto in Database
     */
    @Override
    public List<ProductDto> getAll() {
        return   productRepository.findAll()
                .stream().map(productMapper::toProductDto)
                .toList();
    }


    /***
     * search products by brand
     * @param  brand string
     * @return LIst of ProductDTO
     */
    @Override
    public List<ProductDto> getProductByBrand(String brand) {
        return productRepository.findProductsByBrand(brand)
                .orElseThrow(()-> new ApiRequestException("No product found with name: " + brand))
                .stream().map(productMapper::toProductDto)
                .toList();
    }

    /***
     * search products by category
     * @param category name
     * @return List of ProductDTO
     */
    @Override
    public List<ProductDto> getProductByCategory(String category) {
        return productRepository.findByCategoryName(category)
                .stream().map(productMapper::toProductDto)
                .toList();
    }

    /***
     *  search product by category & brand
     * @param brand string
     * @param category name
     * @return  ProductDto
     */
    @Override
    public List<ProductDto> getProductByCategoryAndBand(String brand, String category) {
       return productRepository.findProductByBrandAndCategoryName( brand,category)
                .stream().map(productMapper::toProductDto)
                .toList();
    }


    /**
     * search product by name
     * @param name string
     * @return ProductDto
     */
    @Override
    public List<ProductDto> getProductByName(String name) {
        return productRepository.findByName(name)
                .orElseThrow(()-> new ApiRequestException("No product found with name: " + name))
                .stream().map(productMapper::toProductDto)
                .toList();
    }

    @Override
    public Long countByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }

    @Override
    public List<ProductDto> getProductByNameAndBrand(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name)
                .stream().map(productMapper::toProductDto)
                .toList();
    }
}
