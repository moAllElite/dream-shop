package com.niangsa.dream_shop.service;

import com.niangsa.dream_shop.dto.CartDto;
import com.niangsa.dream_shop.mappers.CartMapper;
import com.niangsa.dream_shop.repository.CartItemRepository;
import com.niangsa.dream_shop.repository.CartRepository;
import com.niangsa.dream_shop.service.interfaces.ICartService;
import jakarta.persistence.EntityNotFoundException;
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
        return null;
    }

    /**
     * @param id cart
     */
    @Override
    public void clearCart(Long id) {
        //1- Get cart info
        CartDto cartDto = cartRepository.findById(id)
                .map(cartMapper::toCartDto)
                .orElseThrow(()-> new EntityNotFoundException("No Cart were found on cart"));
        // clear cart item info
        cartDto.getCartItems().clear();
        cartItemRepository.deleteAllById(id);
        cartRepository.deleteById(id);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public BigDecimal getTotalPrice(Long id) {
        return null;
    }
}
