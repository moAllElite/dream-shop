package com.niangsa.dream_shop.controllers;


import com.niangsa.dream_shop.dto.CartDto;
import com.niangsa.dream_shop.response.ApiResponse;
import com.niangsa.dream_shop.service.cart.ICartService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RequestMapping("/carts")
@RestController
public class CartController {
    // inject the cart's services
    private final ICartService cartService;

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable Long cartId){
        return  ResponseEntity.ok(cartService.getCart(cartId));
    }

    @GetMapping("/{cartId}/total-price")
    public  ResponseEntity<ApiResponse> updateCart(@PathVariable Long cartId){
        return ResponseEntity.ok().body(new ApiResponse("Total price",cartService.getTotalPrice(cartId)));
    }

    @DeleteMapping("/{cartId}/clear")
    public  ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId){
        cartService.clearCart(cartId);
        return ResponseEntity.ok().body(new ApiResponse("Clear cart success !",null));
    }




}
