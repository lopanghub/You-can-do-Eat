package com.springbootstudy.app.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


// 쇼핑몰 상품 테이블 
// 담당 -이현학

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	private int productId;
    private int price;
    private String productName;
    private String productImage;
    private String size;
    private double rating;
    private String category;
    private String ingredient;
    private String expirationDate;


}


