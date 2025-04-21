package com.niangsa.dream_shop.service.cart;

import com.niangsa.dream_shop.dto.CartItemDto;
import org.springframework.transaction.annotation.Transactional;

public interface ICartItemService {
    //ADD item to the cart
    void addItemToCart(Long productId,Long userId,int quantity);


    // Supprimer un item du panier
    @Transactional
    void removeItemFromCart(Long cartId, Long itemId);

    //update quantity of item 's cart
    void updateItemToCart(Long cartId,Long productId,int quantity);

    //get cart's item according to  product id & cart id
    CartItemDto getCartItem(Long cartId, Long productId);
}
