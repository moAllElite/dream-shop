package com.niangsa.dream_shop.service;

import com.niangsa.dream_shop.dto.ProductDto;
import com.niangsa.dream_shop.entities.Category;
import com.niangsa.dream_shop.entities.Product;
import com.niangsa.dream_shop.mappers.ProductMapper;
import com.niangsa.dream_shop.repository.CategoryRepository;
import com.niangsa.dream_shop.repository.ProductRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
    //inject repository & mappers
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    /***
     *
     * @param category
     * @param productDto
     * @return ProductDto
     */
    @Override
    public ProductDto saveProduct(String category,ProductDto productDto) {
        Product savedProduct =productMapper.toEntity(productDto);
        if(productRepository.existsById(savedProduct.getId())){
            throw  new EntityExistsException(
                    String.format("Product already exist id:%s",savedProduct.getId())
            );
        }

        Category categoryEntity = categoryRepository.findByName(category).orElseThrow(
                ()->                new EntityNotFoundException(String.format("No category were found with provided name: %s",category)));
        savedProduct.setCategory(categoryEntity);
        return productMapper.toDto(savedProduct);
    }

    /**
     * search product by id
     * @param id
     * @return ProductDto
     */

    @Override
    public ProductDto getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("No product was found"));
        return productMapper.toDto(product);
    }

    /**
     * delete product by id
     * @param id
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
     * @param  id
     * @param productDto
     * @return ProductDto
     */
    @Override
    public ProductDto update(Long id, ProductDto productDto) {
        if(!productRepository.existsById(id)) {
            throw  new EntityNotFoundException(String.format("Product not found with provide ID: %s",id));
        }
        Product product = productRepository.save(productMapper.toEntity(productDto));
        return productMapper.toDto(product);
    }

    /**
     *
     * @return List of ProductDto
     */
    @Override
    public List<ProductDto> getAll() {
        return   productRepository.findAll()
                .stream().map(productMapper::toDto)
                .toList();
    }


    /***
     * search product by brand
     * @param  brand
     * @return ProductDTO
     */
    @Override
    public ProductDto getProductByBrand(String brand) {
        return productRepository.findProductByName(brand)
                .map(productMapper::toDto)
                .orElseThrow(
                        ()-> new EntityNotFoundException(
                                String.format("Product were not found %s", brand))
                );
    }

    /***
     * search product by category
     * @param category
     * @return ProductDTO
     */
    @Override
    public ProductDto getProductByCategory(String category) {
        return productRepository.finProductByCategory(category)
                .map(productMapper::toDto)
                .orElseThrow(
                        ()-> new EntityNotFoundException(
                                String.format("No product were found with provided category:%s",category)
                        )
                );
    }

    /***
     *  search product by category & brand
     * @param brand
     * @param category
     * @return  ProductDto
     */
    @Override
    public ProductDto getProductByCategoryAndBand(String brand,String category) {
        return productRepository.findProductByCategoryAndBrand(brand, category)
                .map(productMapper::toDto)
                .orElseThrow(()-> new EntityNotFoundException(
                        String.format("No product were found with provided category %s and brand  %s",category,brand)
                ));
    }


    /**
     * search product by name
     * @param name
     * @return ProductDto
     */
    @Override
    public ProductDto getProductByName(String name) {
        return productRepository.findProductByName(name)
                .map(productMapper::toDto)
                .orElseThrow(
                        ()-> new EntityNotFoundException(
                                String.format("No product were found with proivded name %s",name)
                        )
                );
    }
}
