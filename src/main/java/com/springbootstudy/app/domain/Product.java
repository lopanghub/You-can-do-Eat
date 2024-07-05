package com.springbootstudy.app.domain;


import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


// 쇼핑몰 상품 테이블 
// 담당 -이현학

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	private int product_id;
    private int price;
    private String product_image;
    private String seller;
    private String origin;
    private String size;
    private String category;
    private String ingredient;
    private String expiration_date;
}


