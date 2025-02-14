package com.niangsa.dream_shop.service.order;

import com.niangsa.dream_shop.dto.OrderDto;

import java.util.List;

public interface IOrderService {

    OrderDto placeOrder(Long orderId);


    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);
}
