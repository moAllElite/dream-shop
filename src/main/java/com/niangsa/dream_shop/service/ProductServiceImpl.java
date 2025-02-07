package com.niangsa.dream_shop.service;

import com.niangsa.dream_shop.entites.Product;
import com.niangsa.dream_shop.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
    private final ProductRepository productRepository;

    @Override
    public Product getById(Long id) {
        return this.productRepository.findById(id)
                .orElseThrow(null
                        //new EntityNotFoundException(String.format("Product with id %s not found", id))
                );
    }

    @Override
    public List<Product> getAll() {
        return List.of();
    }

    @Override
    public void add(Product product) {

    }

    @Override
    public void delete(Long id) {

    }
}
