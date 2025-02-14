package com.niangsa.dream_shop.controllers;

import java.util.List;
import com.niangsa.dream_shop.dto.OrderDto;
import com.niangsa.dream_shop.response.ApiResponse;
import com.niangsa.dream_shop.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/orders/")
@RestController
public class OrderController {
    private final IOrderService orderService;
    private static final HttpStatus CREATED =HttpStatus.CREATED;

    @GetMapping("/{orderId}/order")
    public ResponseEntity<OrderDto> showOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @GetMapping("")
    public ResponseEntity<List<OrderDto>> showOrderByUserId(@RequestParam Long userId) {
        return ResponseEntity.ok(orderService.getUserOrders(userId));
    }


    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse> createOrder(@PathVariable Long userId) {
        orderService.placeOrder(userId);
        return ResponseEntity.status(CREATED).body(new ApiResponse("Order save success !",null));
    }
}
