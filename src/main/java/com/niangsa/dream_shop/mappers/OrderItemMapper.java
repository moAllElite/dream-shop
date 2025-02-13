package com.niangsa.dream_shop.mappers;

import com.niangsa.dream_shop.dto.OrderItemDto;
import com.niangsa.dream_shop.entities.OrderItem;
import org.mapstruct.Mapper;

@Mapper
public interface OrderItemMapper {
    OrderItem toOrderItemEntity(OrderItemDto orderItemDto);
    OrderItemDto toOrderItemDto(OrderItem orderItem);
}
