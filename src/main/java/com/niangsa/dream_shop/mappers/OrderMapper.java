package com.niangsa.dream_shop.mappers;

import com.niangsa.dream_shop.dto.OrderDto;
import com.niangsa.dream_shop.entities.Order;
import org.mapstruct.Mapper;

@Mapper
public interface OrderMapper {
    Order toOrderEntity(OrderDto orderDto);
    OrderDto toOrderDto(Order order);
}
