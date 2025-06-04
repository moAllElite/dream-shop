package com.niangsa.dream_shop.service.cart;

import com.niangsa.dream_shop.dto.CartDto;
import com.niangsa.dream_shop.dto.CartItemDto;
import com.niangsa.dream_shop.entities.Cart;
import com.niangsa.dream_shop.entities.CartItem;
import com.niangsa.dream_shop.entities.Product;
import com.niangsa.dream_shop.entities.User;
import com.niangsa.dream_shop.mappers.CartMapper;
import com.niangsa.dream_shop.mappers.ProductMapper;
import com.niangsa.dream_shop.mappers.UserMapper;
import com.niangsa.dream_shop.repositories.CartItemRepository;
import com.niangsa.dream_shop.repositories.CartRepository;
import com.niangsa.dream_shop.service.product.IProductService;
import com.niangsa.dream_shop.service.user.IUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CartItemServiceImpl implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final ICartService cartService;
    private final CartMapper cartMapper;
    private final ProductMapper productMapper;
    private final CartRepository cartRepository;
    private final IProductService productService;
    private final IUserService userService;

    /**
     *
     *  //1. Get the cart from user Id
     *         //2. Get the product
     *         //3. check if the product already in the cart
     *         // 4. If Yes, increase the quantity with reequested quantity
     *         //5. If No, initiate a new cartItem entry
     */
    private final UserMapper userMapper;
    @Override
    public void addItemToCart( Long productId,Long userId, int quantity) {
        // Récupérer l'utilisateur
        User user= userMapper.userDtotoUser(userService.getById(userId));

        // Récupérer ou initialiser le panier de l'utilisateur
        Cart   cart = cartService.initializeCart(user);
        // Récupérer le produit
        Product product = productMapper.toProductEntity( productService.getById(productId));

        // Rechercher l'item existant dans le cart
        Optional<CartItem> existingItemOpt = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
        CartItem cartItem;

        if(existingItemOpt.isEmpty()){
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
            cartItem.setCart(cart);
            cart.getItems().add(cartItem);
        } else {
            // Si l'item existe, mettre à jour la quantité
            cartItem = existingItemOpt.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();//update the total price which is equal to qte * unit price
        CartItem savedCartItem = cartItemRepository.save(cartItem);// persist on db

        cart.addItem(savedCartItem);
        cartRepository.save(cart);// persist on db
    }



    // Supprimer un item du panier
    @Transactional
    @Override
    public void removeItemFromCart(Long cartId, Long itemId) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));

        if (!item.getCart().getId().equals(cartId)) {
            throw new IllegalArgumentException("Item does not belong to the given cart");
        }
        item.getCart().removeItem(item);
        cartItemRepository.delete(item);

    }

    /**
     * @param cartId long
     * @param productId long
     * @param quantity long
     */
    @Override
    public void updateItemToCart(Long cartId, Long productId, int quantity) {
        //get the current cart
        Cart cart = cartMapper.toCartEntity( cartService.getCart(cartId));
        //update quantity from form
        cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresentOrElse(
                        item -> {
                            item.setQuantity(quantity);
                            item.setUnitPrice(productService.getById(productId).getPrice());
                            item.setTotalPrice();
                        },
                        ()-> { throw new EntityNotFoundException("product not found"); }
                );
        //get calculed totalamount after update quantity
        BigDecimal totalAmount = cart.getTotalAmount();
        //assign the updated total amount
        cart.setTotalAmount(totalAmount);
        //save cart
        cartRepository.save(cart);
    }

    //get cart's item according to  product id & cart id
    @Override
    public CartItemDto getCartItem(Long cartId, Long productId)  {
        CartDto cartDto =  cartService.getCart(cartId);
        return cartDto.getItems()
                .stream()
                .filter(item-> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(()-> new EntityNotFoundException(String.format("item  not found with provided product ID  %s and cart ID:%S" , productId,cartId)));
    }
}
