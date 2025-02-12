package com.niangsa.dream_shop.service;

import com.niangsa.dream_shop.dto.CartDto;
import com.niangsa.dream_shop.dto.CartItemDto;
import com.niangsa.dream_shop.dto.ProductDto;
import com.niangsa.dream_shop.entities.Cart;
import com.niangsa.dream_shop.entities.CartItem;
import com.niangsa.dream_shop.exceptions.ApiRequestException;
import com.niangsa.dream_shop.mappers.CartItemMapper;
import com.niangsa.dream_shop.mappers.CartMapper;
import com.niangsa.dream_shop.repository.CartItemRepository;
import com.niangsa.dream_shop.repository.CartRepository;
import com.niangsa.dream_shop.service.interfaces.ICartItemService;
import com.niangsa.dream_shop.service.interfaces.ICartService;
import com.niangsa.dream_shop.service.interfaces.IProductService;
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
    private final CartRepository cartRepository;
    private final IProductService productService;
    /**
     * @param cartId  long
     * @param productId long
     * @param quantity int
     */
    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        //1. Get the cart
        //2. Get the product
        //3. check if the product already in the cart
        // 4. If Yes, increase the quantity with reequested quantity
        //5. If No, initiate a new cartItem entry
        CartDto cartDto = cartService.getCart(cartId);
        ProductDto productDto = productService.getById(productId);
        CartItemDto cartItemDto = cartDto.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItemDto());

        if(cartItemDto.getId() == null){
            cartItemDto.setCart(cartDto);
            cartItemDto.setProduct(productDto);
            cartItemDto.setQuantity(quantity);
            cartItemDto.setUnitPrice(productDto.getPrice());
        } else {
            cartItemDto.setQuantity(quantity + cartItemDto.getQuantity() );
        }

        CartItem cartItem =  cartItemMapper.toCartItemEntity(cartItemDto); // convert from dto to entity
        cartItem.setTotalPrice();//update the total price which is equal to qte * unit price
        Cart cart = cartMapper.toCartEntity(cartDto);// convert from dto to entity
        cart.addItem(cartItem);
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
        CartDto cartDto = cartService.getCart(cartId); //get the current cart
        CartItem cartItem = cartItemMapper.toCartItemEntity(getCartItem(cartId, productId));//get cart's item
       Cart cart = cartMapper.toCartEntity(cartDto);// convert to Entity
       cart.removeItem(cartItem); //drop item from cart
    }

    /**
     * @param cartId long
     * @param productId long
     * @param quantity long
     */
    @Override
    public void updateItemToCart(Long cartId, Long productId, int quantity) {
        //get the current cart
        CartDto cartDto = cartService.getCart(cartId);
        Cart cart = cartMapper.toCartEntity(cartDto);
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
                         ()-> { throw new ApiRequestException("product not found"); }
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
    public CartItemDto getCartItem(Long cartId, Long productId) {
        CartDto cartDto = cartService.getCart(cartId);
        return cartDto.getItems()
                .stream()
                .filter(item-> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(()-> new ApiRequestException("item  not found"));
    }
}
