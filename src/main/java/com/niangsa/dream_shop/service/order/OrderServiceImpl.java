package com.niangsa.dream_shop.service.order;

import com.niangsa.dream_shop.dto.OrderDto;
import com.niangsa.dream_shop.entities.Cart;
import com.niangsa.dream_shop.entities.Order;
import com.niangsa.dream_shop.entities.OrderItem;
import com.niangsa.dream_shop.entities.Product;
import com.niangsa.dream_shop.enums.OrderStatuts;
import com.niangsa.dream_shop.exceptions.ApiRequestException;
import com.niangsa.dream_shop.mappers.CartMapper;
import com.niangsa.dream_shop.mappers.OrderItemMapper;
import com.niangsa.dream_shop.mappers.OrderMapper;
import com.niangsa.dream_shop.mappers.ProductMapper;
import com.niangsa.dream_shop.repositories.OrderItemRepository;
import com.niangsa.dream_shop.repositories.OrderRepository;
import com.niangsa.dream_shop.repositories.ProductRepository;
import com.niangsa.dream_shop.service.cart.ICartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final OrderItemMapper orderItemMapper;
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
            order.setOrderItems(new HashSet<>(orderItem));
            orderRepository.save(order);  //persist order
            cartService.clearCart(cart.getId());//clear the cart
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage() );
        }
    }

    /**
     * @param orderId from form
     * @return  orderDto
     */
    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::toOrderDto)
                .orElseThrow(()-> new ApiRequestException("Order not found"));
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
        order.setUser(cart.getUser());
        return  order;
    }

    public BigDecimal calculateToAmount(List<OrderItem> orderItemList){
        return   orderItemList.stream()
                .map(item->item.getPrice().multiply(new BigDecimal (item.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    /**
     * get all orders by user
     * @param userId long
     * @return List of orders by user
     */
    @Override
    public List<OrderDto> getUserOrders(Long userId){
        return  orderRepository.findByUserId(userId).stream().map(orderMapper::toOrderDto).toList();
    }
}
