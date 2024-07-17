package com.springbootstudy.app.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private Long id;
    private String orderId;
    private int productId;
    private String productName;
    private String productImage;
    private int price;
    private int quantity;

    // getters and setters
}