package com.niangsa.dream_shop.service.order;

import com.niangsa.dream_shop.dto.OrderDto;
import com.niangsa.dream_shop.entities.*;
import com.niangsa.dream_shop.enums.OrderStatuts;
import com.niangsa.dream_shop.mappers.*;
import com.niangsa.dream_shop.repositories.OrderItemRepository;
import com.niangsa.dream_shop.repositories.OrderRepository;
import com.niangsa.dream_shop.repositories.ProductRepository;
import com.niangsa.dream_shop.service.cart.ICartService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@AllArgsConstructor
@Service
public class OrderServiceImpl implements IOrderService {
    //inject repository ,service & mapper
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final ICartService cartService;
    private final UserMapper userMapper;

    private final CartMapper cartMapper;
    /**
     * Persist order on db
     *
     * @param userId long
     */
    @Override
    public void placeOrder(Long userId) {
        try {
            Cart cart= cartService.getCartByUserId(userId);
            Order order = createOrder(cart);
            List<OrderItem> orderItem = createOrderItems(order,cart) ;
            System.out.println(order.getOrderDate());
            order.setTotalAmount(calculateToAmount(orderItem));//update total amount
            order.setItems(new HashSet<>(orderItem));
            order.setUser(cart.getUser());
            orderRepository.save(order);  //persist order
            cartService.clearCart(cart.getId());//clear the cart
        } catch (Exception e) {
            throw new EntityExistsException(e.getMessage() );
        }
    }
    /**
     * get all orders by user
     *
     * @param userId long
     * @return List of orders by user
     */
    @Override
    public List<OrderDto> getUserOrders(Long userId){
        return orderRepository.findByUserId(userId)
                .stream().map(orderMapper::toOrderDto)
                .toList();
    }

    /**
     * @param orderId from form
     * @return  orderDto
     */
    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::toOrderDto)
                .orElseThrow(()-> new EntityNotFoundException("Order not found"));
    }

    /**
     *
     * @param order from form
     * @param cart from form
     * @return Order
     */
    private List<OrderItem> createOrderItems(Order order, Cart cart){
       return cart.getItems().stream()
               .map(cartItem-> {
                   Product product = cartItem.getProduct();
                   product.setInventory(product.getInventory() - cartItem.getQuantity());
                   productRepository.save(product);
                   return  new  OrderItem(product, order, cartItem.getQuantity(),cartItem.getUnitPrice());
               } )
               .toList();
    }

    /**
     * create new order from cartdto
     * @param cart from form
     * @return orderDto
     */
    private Order createOrder(Cart cart){
        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setOrderStatuts(OrderStatuts.PENDING);
        User user = cart.getUser();
        order.setUser(user);
        return  order;
    }

    public BigDecimal calculateToAmount(List<OrderItem> orderItemList){
        return   orderItemList.stream()
                .map(item->item.getPrice().multiply(new BigDecimal (item.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }


}
