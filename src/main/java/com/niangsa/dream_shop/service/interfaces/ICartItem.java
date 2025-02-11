package com.niangsa.dream_shop.service.interfaces;

public interface ICartItem {
    void addItemToCart(Long cartId,Long productId,int quantity);
    void  removeItemToCart(Long cartId,Long productId);
    void updateItemToCart(Long cartId,Long productId,int quantity);
}
