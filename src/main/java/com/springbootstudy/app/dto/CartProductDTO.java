package com.springbootstudy.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartProductDTO {
    private int cartId;
    private int productId;
    private String productName;
    private String Ingredient; 
    private String productImage;
    private int quantity; 
    private int price; 
}

