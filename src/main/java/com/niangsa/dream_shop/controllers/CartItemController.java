package com.niangsa.dream_shop.controllers;


import com.niangsa.dream_shop.dto.CartItemDto;
import com.niangsa.dream_shop.response.ApiResponse;
import com.niangsa.dream_shop.service.cart.ICartItemService;
import com.niangsa.dream_shop.service.cart.ICartService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/cartItems")
@RestController
public class CartItemController {
    private static final HttpStatus CREATED = HttpStatus.CREATED;
    private final ICartItemService cartItemService;
    private final ICartService cartService;
    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(
            @RequestParam Long productId,
            @RequestParam Long userId,
            @RequestParam int quantity
    ){

        cartItemService.addItemToCart(productId,userId,quantity);
        return  ResponseEntity.status(CREATED).body(new ApiResponse("Item successfully added!",null));
    }

    @GetMapping()
    public  ResponseEntity<CartItemDto> getCartItem(@RequestParam Long cartId, @RequestParam Long productId){
        return ResponseEntity.ok(cartItemService.getCartItem(cartId, productId));
    }

    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public  ResponseEntity<ApiResponse> updateCartItem(@PathVariable Long cartId, @PathVariable Long itemId, @RequestParam int quantity){
        cartItemService.updateItemToCart(cartId,itemId,quantity);
        return ResponseEntity.ok().body(new ApiResponse("Cart item update success! ",null));
    }

    @DeleteMapping("/{cartId}/item/{productId}/product/remove")
    public  ResponseEntity<ApiResponse> removeItemFrom(@PathVariable Long cartId, @PathVariable Long productId){
        cartItemService.removeItemToCart(cartId,productId);
        return ResponseEntity.ok().body(new ApiResponse("Item delete success !",null));
    }
}
