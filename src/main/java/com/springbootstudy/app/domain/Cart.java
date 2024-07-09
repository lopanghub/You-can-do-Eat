package com.springbootstudy.app.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//쇼핑몰 장바구니 담당자 - 이현학

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
	private int cartId;
	private int quantity;
	private int productId;
}
