package com.niangsa.dream_shop.service.cart;

import com.niangsa.dream_shop.dto.CartDto;
import com.niangsa.dream_shop.entities.Cart;
import com.niangsa.dream_shop.entities.CartItem;
import com.niangsa.dream_shop.exceptions.ApiRequestException;
import com.niangsa.dream_shop.mappers.CartMapper;
import com.niangsa.dream_shop.repositories.CartItemRepository;
import com.niangsa.dream_shop.repositories.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@AllArgsConstructor
@Service
public class CartServiceImpl implements ICartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cardIdGenerator = new AtomicLong(0);
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
     * drop cart content & remove all cart item related to the cart
     * @param cartId cart
     */
    @Transactional
    @Override
    public void clearCart(Long cartId) {
        //1- Get cart info
        Cart cart = cartMapper.toCartEntity(getCart(cartId));
        // clear cart that contain cart items info
        cartItemRepository.deleteAllByCartId(cartId);
        cart.getItems().clear();
        cartRepository.deleteById(cartId); //drop the current cart
    }

    /**
     * @param id cart long
     * @return total price = sum of each total price of cart item
     */
    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart =  cartMapper.toCartEntity(getCart(id));
        return cart.getItems().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }
    /**initialize the cart
     * @return cart id long
     */

    @Override
    public Long initializeCart(){
        Cart newCart = new Cart();
        Long cartId = cardIdGenerator.incrementAndGet();
        newCart.setId(cartId);
        return cartRepository.save(newCart).getId();
    }

    /**
     * @param userId  long
     * @return Cart Dto
     */
    @Override
    public CartDto getCartByUserId(Long userId) {
        return cartRepository.findCartByUserId(userId)
                .map(cartMapper::toCartDto)
                .orElseThrow(()->  new ApiRequestException("Cart not found"));
    }
}
