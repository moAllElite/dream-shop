package com.niangsa.dream_shop.controllers;


import com.niangsa.dream_shop.dto.CartItemDto;
import com.niangsa.dream_shop.exceptions.ApiRequestException;
import com.niangsa.dream_shop.service.interfaces.ICartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/cartItems")
@RestController
public class CartItemController {
    private static final HttpStatus CREATED = HttpStatus.CREATED;
    private final ICartItemService cartItemService;

    @PostMapping("/add")
    public ResponseEntity<ApiRequestException> addItemToCart(@RequestParam Long cartId, @RequestParam Long productId,@RequestParam int quantity){
        cartItemService.addItemToCart(cartId,productId,quantity);
        return  ResponseEntity.status(CREATED).body(new ApiRequestException("Item successfully added!",null));
    }

    @GetMapping("")
    public  ResponseEntity<CartItemDto> getCartItem(@RequestParam Long cartId, @RequestParam Long productId){
        return ResponseEntity.ok(cartItemService.getCartItem(cartId, productId));
    }

    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public  ResponseEntity<ApiRequestException> updateCartItem(@PathVariable Long cartId, @PathVariable Long itemId, @RequestParam int quantity){
        cartItemService.updateItemToCart(cartId,itemId,quantity);
        return ResponseEntity.ok().body(new ApiRequestException("Cart item update success! ",null));
    }

    @DeleteMapping("/{cartId}/item/{productId}/product/remove")
    public  ResponseEntity<ApiRequestException> removeItemFrom(@PathVariable Long cartId, @RequestParam Long productId){
        cartItemService.removeItemToCart(cartId,productId);
        return ResponseEntity.ok().body(new ApiRequestException("Cart item delete !",null));
    }
}
