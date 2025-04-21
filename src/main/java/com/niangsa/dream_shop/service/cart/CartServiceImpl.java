package com.niangsa.dream_shop.service.cart;

import com.niangsa.dream_shop.dto.CartDto;
import com.niangsa.dream_shop.entities.Cart;
import com.niangsa.dream_shop.entities.CartItem;
import com.niangsa.dream_shop.entities.User;
import com.niangsa.dream_shop.mappers.CartMapper;
import com.niangsa.dream_shop.mappers.UserMapper;
import com.niangsa.dream_shop.repositories.CartItemRepository;
import com.niangsa.dream_shop.repositories.CartRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

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
                .orElseThrow(()-> new EntityNotFoundException("No cart were found  provided id:"+id));
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
        // 1. Récupérer le panier
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("No cart found with id: " + cartId));

        // 2. Vider les items
        cart.getItems().clear();

        // 3. Réinitialiser le total
        cart.setTotalAmount(BigDecimal.ZERO);

        // 4. Sauvegarder (orphanRemoval supprimera les CartItem de la DB)
        cartRepository.save(cart);
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
     */
    @Override
    public Cart initializeCart(User user){
        return   Optional.ofNullable(cartRepository.findCartByUserId(user.getId()))
               .orElseGet(()->{
                   Cart cart= new Cart();
                   cart.setUser(user);
                   return cartRepository.save(cart);
               });
    }

    /**
     * @param userId long
     * @return Cart Dto
     */
    @Override
    public CartDto getCartByUserId(Long userId) {
        return cartMapper.toCartDto(cartRepository.findCartByUserId(userId));
    }
}
