package com.niangsa.dream_shop.mappers;

import com.niangsa.dream_shop.dto.CartItemDto;
import com.niangsa.dream_shop.entities.CartItem;
import org.mapstruct.Mapper;

@Mapper()
public interface CartItemMapper {
    CartItemDto toCartItemDto(CartItem cartItem);
    CartItem toCartItemEntity(CartItemDto cartItemDto);
}
