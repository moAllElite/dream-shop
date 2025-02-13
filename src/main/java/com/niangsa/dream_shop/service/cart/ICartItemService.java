package com.niangsa.dream_shop.service.cart;

import com.niangsa.dream_shop.dto.CartItemDto;

public interface ICartItemService {
    //ADD item to the cart
    void addItemToCart(Long cartId,Long productId,int quantity);
    //drop item
    void  removeItemToCart(Long cartId,Long productId);
    //update quantity of item 's cart
    void updateItemToCart(Long cartId,Long productId,int quantity);

    //get cart's item according to  product id & cart id
    CartItemDto getCartItem(Long cartId, Long productId);
}
