package com.niangsa.dream_shop.service.cart;

import com.niangsa.dream_shop.dto.CartDto;
import com.niangsa.dream_shop.dto.CartItemDto;
import com.niangsa.dream_shop.entities.Cart;
import com.niangsa.dream_shop.entities.CartItem;
import com.niangsa.dream_shop.entities.Product;
import com.niangsa.dream_shop.entities.User;
import com.niangsa.dream_shop.mappers.CartItemMapper;
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

import java.math.BigDecimal;

@AllArgsConstructor
@Service
public class CartItemServiceImpl implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final ICartService cartService;
    private final CartItemMapper cartItemMapper;
    private final CartMapper cartMapper;
    private final ProductMapper productMapper;
    private final CartRepository cartRepository;
    private final IProductService productService;
    private final IUserService userService;

    /**
     * @param productId long
     * @param quantity int
     *  //1. Get the cart from user Id
     *         //2. Get the product
     *         //3. check if the product already in the cart
     *         // 4. If Yes, increase the quantity with reequested quantity
     *         //5. If No, initiate a new cartItem entry
     */
    private final UserMapper userMapper;
    @Override
    public void addItemToCart( Long productId,Long userId, int quantity) {
        User user= userMapper.userDtotoUser(userService.getById(userId));
        Long    cartId = cartService.initializeCart(user).getId();
        CartDto cartDto = cartService.getCart(cartId);
        Cart cart = cartMapper.toCartEntity(cartDto);

        Product product = productMapper.toProductEntity( productService.getById(productId));
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());

        if(cartItem.getId() == null){
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        } else {
            cartItem.setQuantity(quantity + cartItem.getQuantity() );
        }

        cartItem.setTotalPrice();//update the total price which is equal to qte * unit price
        cart.addItem(cartItem);
        cart.setUser(user); //assign to user
        cartItemRepository.save(cartItem); // persist on db
        cartRepository.save(cart);// persist on db

    }

    /**
     * Remove cart item to the cart
     * @param cartId long
     * @param productId long
     */
    @Override
    public void removeItemToCart(Long cartId, Long productId) {
        Cart cart = cartMapper.toCartEntity(cartService.getCart(cartId)); //get the current cart
        CartItem itemToRemove =  cartItemMapper.toCartItemEntity(getCartItem(cartId, productId));//get cart's item
        //   Cart cart = cartMapper.toCartEntity(cartDto);// convert to Entity
        cart.removeItem(itemToRemove); //drop item from cart
        cartRepository.save(cart);//persist cart on db
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
