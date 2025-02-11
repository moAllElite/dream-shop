package com.niangsa.dream_shop.service;

import com.niangsa.dream_shop.dto.CartDto;
import com.niangsa.dream_shop.dto.CartItemDto;
import com.niangsa.dream_shop.dto.ProductDto;
import com.niangsa.dream_shop.entities.Cart;
import com.niangsa.dream_shop.entities.CartItem;
import com.niangsa.dream_shop.entities.Product;
import com.niangsa.dream_shop.mappers.CartItemMapper;
import com.niangsa.dream_shop.mappers.CartMapper;
import com.niangsa.dream_shop.mappers.ProductMapper;
import com.niangsa.dream_shop.repository.CartItemRepository;
import com.niangsa.dream_shop.repository.CartRepository;
import com.niangsa.dream_shop.service.interfaces.ICartItem;
import com.niangsa.dream_shop.service.interfaces.ICartService;
import com.niangsa.dream_shop.service.interfaces.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CartItemImpl implements ICartItem {
    private final CartItemRepository cartItemRepository;
    private final ICartService cartService;
    private final CartItemMapper cartItemMapper;
    private final ProductMapper productMapper;
    private final CartMapper cartMapper;
    private final CartRepository cartRepository;
    private final IProductService productService;
    /**
     * @param cartId  long
     * @param productId long
     * @param quantity int
     */
    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        //1. Get the cart
        //2. Get the product
        //3. check if the product already in the cart
        // 4. If Yes, increase the quantity with reequested quantity
        //5. If No, initiate a new cartItem entry
        CartDto cartDto = cartService.getCart(cartId);
        ProductDto productDto = productService.getById(productId);
        CartItemDto cartItemDto = cartDto.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItemDto());
        if(cartItemDto.getId() == null){
            cartItemDto.setCart(cartDto);
            cartItemDto.setProduct(productDto);
            cartItemDto.setQuantity(quantity);
            cartItemDto.setUnitPrice(productDto.getPrice());
        } else {
            cartItemDto.setQuantity(quantity + cartItemDto.getQuantity() );
        }

        CartItem cartItem =  cartItemMapper.toCartItemEntity(cartItemDto);
        cartItem.setTotalPrice();
        Cart cart = cartMapper.toCartEntity(cartDto);
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    /**
     * @param cartId long
     * @param productId long
     */
    @Override
    public void removeItemToCart(Long cartId, Long productId) {

    }

    /**
     * @param cartId long
     * @param productId long
     * @param quantity long
     */
    @Override
    public void updateItemToCart(Long cartId, Long productId, int quantity) {

    }
}
