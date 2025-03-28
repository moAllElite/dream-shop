package com.niangsa.dream_shop.service.order;

import com.niangsa.dream_shop.dto.CartDto;
import com.niangsa.dream_shop.dto.OrderDto;
import com.niangsa.dream_shop.dto.OrderItemDto;
import com.niangsa.dream_shop.dto.ProductDto;
import com.niangsa.dream_shop.entities.Order;
import com.niangsa.dream_shop.entities.Product;
import com.niangsa.dream_shop.enums.OrderStatuts;
import com.niangsa.dream_shop.mappers.OrderItemMapper;
import com.niangsa.dream_shop.mappers.OrderMapper;
import com.niangsa.dream_shop.mappers.ProductMapper;
import com.niangsa.dream_shop.repositories.OrderItemRepository;
import com.niangsa.dream_shop.repositories.OrderRepository;
import com.niangsa.dream_shop.repositories.ProductRepository;
import com.niangsa.dream_shop.service.cart.ICartService;
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
    private final OrderItemMapper orderItemMapper;
    /**
     * Persist order on db
     *
     * @param userId long
     */
    @Override
    public OrderDto placeOrder(Long userId) {

        CartDto cartDto = cartService.getCartByUserId(userId);
        OrderDto orderDto = createOrder(cartDto);
        List<OrderItemDto> orderItemDtos = createOrderItems(orderDto,cartDto) ;
        orderDto.setOrderItems(new HashSet<>(orderItemDtos));
        orderDto.setTotalAmount(calculateToAmount(orderItemDtos));//update total amount
        Order savedOrder = orderRepository.save(orderMapper.toOrderEntity(orderDto));  //persist order
        cartService.clearCart(cartDto.getId());//clear the cart after order saved
        return orderMapper.toOrderDto(savedOrder);
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
     * @param orderDto from form
     * @param cartDto from form
     * @return Order
     */
    private List<OrderItemDto> createOrderItems(OrderDto orderDto, CartDto cartDto){
       return cartDto.getItems().stream()
               .map(cartItem-> {
                   Product product = productMapper.toProductEntity(cartItem.getProduct());
                   product.setInventory(product.getInventory() - cartItem.getQuantity());
                   ProductDto savedProduct = productMapper.toProductDto(productRepository.save(product));
                   return OrderItemDto.builder()
                           .product(savedProduct)
                           .quantity(cartItem.getQuantity())
                           .order(orderDto)
                           .build();
               } )
               .toList();


    }

    /**
     * create new order from cartdto
     * @param cartDto from form
     * @return orderDto
     */
    private OrderDto createOrder(CartDto cartDto){
        return  OrderDto.builder()
                .orderStatuts(OrderStatuts.PENDING)
                .orderDate(LocalDate.now())
                .user(cartDto.getUser())
                .build();
    }

    /***
     * calculate the total amout =  quantity * price
     * @param orderItemList list of item in order
     * @return total amount related to an user's order
     */
    public BigDecimal calculateToAmount(List<OrderItemDto> orderItemList){
        return   orderItemList.stream()
                .map(orderItemMapper::toOrderItemEntity)
                .map(item->item.getPrice().multiply(new BigDecimal (item.getOrder().getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    /**
     * get all orders by user
     * @param userId long
     * @return List of orders by user
     */
    @Override
    public List<OrderDto> getOrdersByUserId(Long userId){
        return  orderRepository.findByUserId(userId).stream()
                .map(orderMapper::toOrderDto)
                .toList();
    }
}
