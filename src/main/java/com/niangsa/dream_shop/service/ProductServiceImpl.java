package com.niangsa.dream_shop.service;

import com.niangsa.dream_shop.dto.ProductDto;
import com.niangsa.dream_shop.entities.Product;
import com.niangsa.dream_shop.mappers.ProductMapper;
import com.niangsa.dream_shop.repository.ProductRepository;
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
    @Override
    public ProductDto save(ProductDto productDto) {
        Product savedProduct = productRepository.save(
                productMapper.toEntity(productDto)
        );
        return productMapper.toDto(savedProduct);
    }

    @Override
    public ProductDto getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("No product was found"));
        return productMapper.toDto(product);
    }

    @Override
    public void delete(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(
                        productRepository::delete,
                        ()-> { throw new      EntityNotFoundException("Product not  found");}
                );
    }

    @Override
    public ProductDto update(Long id, ProductDto productDto) {
        if(!productRepository.existsById(id)) {
            throw  new EntityNotFoundException(String.format("Product not found with provide ID: %s",id));
        }
        Product product = productRepository.save(productMapper.toEntity(productDto));
        return productMapper.toDto(product);
    }

    @Override
    public List<ProductDto> getAll() {
        return   productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .toList();
    }
}
