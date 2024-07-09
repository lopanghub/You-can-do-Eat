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
	private int productId;
    private int price;
    private String productImage;
    private String seller;
    private String origin;
    private String size;
    private String category;
    private String ingredient;
    private String expirationDate;
    
    

    @Override
    public String toString() {
        return "Product{" +
                "product_id=" + productId +
                ", price=" + price +
                ", product_image='" + productImage + '\'' +
                ", seller='" + seller + '\'' +
                ", origin='" + origin + '\'' +
                ", size='" + size + '\'' +
                ", category='" + category + '\'' +
                ", ingredient='" + ingredient + '\'' +
                ", expiration_date='" + expirationDate + '\'' +
                '}';
    }

}


