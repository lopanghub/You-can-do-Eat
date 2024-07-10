package com.springbootstudy.app.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
	private int orderId;
	private Timestamp orderDate;
	private String payment;
	private String orderRequest;
}
