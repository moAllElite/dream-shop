package com.niangsa.dream_shop.mappers;

import com.niangsa.dream_shop.dto.CartDto;
import com.niangsa.dream_shop.entities.Cart;
import org.mapstruct.Mapper;

@Mapper()
public interface CartMapper {
    Cart toCartEntity(CartDto cartDto);
    CartDto toCartDto(Cart cart);
}
