package com.niangsa.dream_shop.controllers;

import com.niangsa.dream_shop.dto.OrderDto;
import com.niangsa.dream_shop.response.ApiResponse;
import com.niangsa.dream_shop.service.order.IOrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/orders")
@RestController
public class OrderController {
    // inject services
    private final IOrderService orderService;
    private static final HttpStatus CREATED =  HttpStatus.CREATED;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createOrder(@PathVariable Long orderId){
        orderService.placeOrder(orderId);
        return  ResponseEntity.status(CREATED).body(new ApiResponse("Ordre create Success",null));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto>  getOrderCartById(@PathVariable Long orderId){
        return  ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> getOrderByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getUserOrders(userId));
    }


}
