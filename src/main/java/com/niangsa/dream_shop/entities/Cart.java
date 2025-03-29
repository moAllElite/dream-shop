package com.niangsa.dream_shop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private   Long id;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    @OneToMany(mappedBy = "cart",cascade = {CascadeType.DETACH,CascadeType.REMOVE,CascadeType.PERSIST},orphanRemoval = true)
    private  Set<CartItem> items =  new HashSet<>();
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void addItem(CartItem item){
        this.items.add(item);
        item.setCart(this);
        updateCartTotalAmount();
    }
    public void  removeItem(CartItem item){
        this.items.remove(item);
        item.setCart(null);
        updateCartTotalAmount();
    }

    /**
     * get total Amount which is the sum of  total price present in car
     * totalPrice = quantity * unitPrice
     *  totalAmount + = totalPrice
     */
    public void updateCartTotalAmount() {
        this.totalAmount =  items.stream()
                .map(item -> {
                    BigDecimal unitPrice = item.getUnitPrice();
                    if(unitPrice == null){
                        return  BigDecimal.ZERO;
                    }
                    return  unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));

                }).reduce(BigDecimal.ZERO,BigDecimal::add);

    }
}
