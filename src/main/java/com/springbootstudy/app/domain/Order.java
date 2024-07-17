package com.springbootstudy.app.domain;

import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String orderId;
    private String memberId;
    private int totalAmount;
    private String status;
    private List<OrderItem> orderItems;

    // getters and setters
}


