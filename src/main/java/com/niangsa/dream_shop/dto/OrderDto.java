package com.niangsa.dream_shop.dto;

import com.niangsa.dream_shop.enums.OrderStatuts;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private LocalDate orderDate;
    private OrderStatuts orderStatuts;
    private BigDecimal totalAmount;
    private Set<OrderItemDto> orderItems ;
    private UserDto user;
}
