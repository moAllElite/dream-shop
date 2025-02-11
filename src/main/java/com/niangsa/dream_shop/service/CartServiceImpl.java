package com.niangsa.dream_shop.service;

import com.niangsa.dream_shop.dto.CartDto;
import com.niangsa.dream_shop.dto.CartItemDto;
import com.niangsa.dream_shop.entities.Cart;
import com.niangsa.dream_shop.exceptions.ApiRequestException;
import com.niangsa.dream_shop.mappers.CartMapper;
import com.niangsa.dream_shop.repository.CartItemRepository;
import com.niangsa.dream_shop.repository.CartRepository;
import com.niangsa.dream_shop.service.interfaces.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements ICartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final CartItemRepository cartItemRepository;

    /**
     * @param id cart Long
     * @return CartDto
     */
    @Override
    public CartDto getCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(()-> new ApiRequestException("No cart were found id"+id));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartMapper.toCartDto(cartRepository.save(cart));
    }

    /**
     * drop cart
     * @param id cart
     */
    @Override
    public void clearCart(Long id) {
        //1- Get cart info
        CartDto cartDto = getCart(id);
        // clear cart that contain items info
        cartDto.getItems().clear();
        cartItemRepository.deleteAllById(id);
        cartRepository.deleteById(id);
    }

    /**
     * @param id cart long
     * @return total price = addition of each total price of cart item
     */
    @Override
    public BigDecimal getTotalPrice(Long id) {
        CartDto cartDto = cartRepository.findById(id).map(cartMapper::toCartDto)
                .orElseThrow(()-> new ApiRequestException("No cart was found id:" + id));
        return cartDto.getItems().stream()
                .map(CartItemDto::getTotalPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }


}
