package com.springbootstudy.app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
	private int category_id;
}


