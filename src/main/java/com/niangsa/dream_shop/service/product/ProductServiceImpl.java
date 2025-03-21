package com.niangsa.dream_shop.service.product;

import com.niangsa.dream_shop.dto.ProductDto;
import com.niangsa.dream_shop.entities.Category;
import com.niangsa.dream_shop.entities.Product;
import com.niangsa.dream_shop.mappers.ProductMapper;
import com.niangsa.dream_shop.repositories.CategoryRepository;
import com.niangsa.dream_shop.repositories.ProductRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class ProductServiceImpl implements IProductService {
    /**
     inject repositories & mappers*     */

    private final ProductRepository productRepository;
    private final   ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    /***
     *
     * @param  request from formular
     *      1- check if category is found on DB
     *     2-         if yes set it as new category
     *     3-       if no , then save as new category
     */
    @Override
    public Product saveProduct(Product request){
        if(productExists(request.getName(),request.getBrand())){
            throw new EntityExistsException(
                    String.format("Product name already exists with provided name %s and brand %s, you may update this product instead",request.getName(),request.getBrand()));
        }
        String categoryName = request.getCategory().getName();
        Category categoryEntity = Optional.ofNullable(categoryRepository.findByName(categoryName))
                .orElseGet(
                        () -> {
                            Category cat = new  Category(categoryName);
                            return categoryRepository.save(cat);
                        }
                );
       request.setCategory(categoryEntity);
   //    Product entity= productRepository.save(productMapper.toProductEntity(request));
       return productRepository.save(request);
    }

    /**
     * search product by id
     * @param id Long
     * @return ProductDto
     */

    @Override
    public ProductDto getById(Long id) {
       return    productRepository.findById(id)
                .map(productMapper::toProductDto)
                .orElseThrow(()-> new EntityNotFoundException("No product was found with id:" +id));

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
     * @param  id of product
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
     * @return  List of  Products in Database
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
                .orElseThrow(()-> new EntityNotFoundException("No product found with name: " + brand))
                .stream().map(productMapper::toProductDto)
                .toList();
    }

    /***
     * search products by category
     * @param category name
     * @return List of Product
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
     * @return List of products
     */
    @Override
    public ProductDto getProductByName(String name) {
        Product product = productRepository.findByName(name);
        if(product == null) {
            throw  new EntityNotFoundException(String.format("Product not found with provided name: %s",name));
        }
        return productMapper.toProductDto(product)  ;
    }

    /**
     *
     * @param brand of product
     * @param name of product
     * @return the length of a specific product
     */
    @Override
    public Long countByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }


    /**
     *
     * @param brand of product
     * @param name of product
     * @return List of products by name and brand
     */
    @Override
    public List<ProductDto> getProductByNameAndBrand(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name)
                .stream().map(productMapper::toProductDto)
                .toList();
    }

    /**
     *
     * @param minPrice of product
     * @param maxPrice of product
     * @param pageSize number of item which show
     * @return List of product following a range of price
     */
    @Override
    public Page<ProductDto> getProductByMinMaxPrice(BigDecimal minPrice, BigDecimal maxPrice, int pageSize) {
        Pageable firstPage = PageRequest.of(0, pageSize);
        List<ProductDto> productList =  productRepository.findByPriceBetween(minPrice,maxPrice)
                .stream().map(productMapper::toProductDto).toList();
        return new PageImpl<>(productList,firstPage,productList.size());

    }

    /**
     * check if product already exist
     * @param name of the product
     * @param brand of the product
     * @return True if product is already registered otherwise False
     */
    private boolean productExists(String name, String brand) {
     return    productRepository.existsByNameAndBrand(name,brand);
    }
}
