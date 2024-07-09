package com.springbootstudy.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootstudy.app.domain.Cart;
import com.springbootstudy.app.domain.Product;
import com.springbootstudy.app.dto.CartProductDTO;
import com.springbootstudy.app.mapper.ProductMapper;

import lombok.extern.slf4j.Slf4j;

// 상품판매 서비스
// 담당자 - 이현학
@Service
@Slf4j
public class ProductService {
	@Autowired
	private ProductMapper productMapper;
	
	private List<Product> findAll(){
		log.info("service : findAll()");
		return productMapper.findAll();
	}
	
	public Product getProductById(int productId) {
        log.info("service : getProductById(int productId)");
        Product product = productMapper.findById(productId);
        log.info("Retrieved product: " + product);
        return product;
    }
	
	//카테고리 별 분류 
	public List<Product> getProductByCategory(String category) {
		log.info("service : getProductByCategory(String category)");
		return productMapper.findByCategory(category);
	}
	
	//장바구니 추가
	public void addCart(int productId, int quantity) {
		 Cart existingCart = productMapper.getCartByProductId(productId);
	        if (existingCart != null) {
	            existingCart.setQuantity(existingCart.getQuantity() + quantity);
	            productMapper.updateCart(existingCart);
	        } else {
	            Cart cart = new Cart();
	            cart.setProductId(productId);
	            cart.setQuantity(quantity);
	            productMapper.insertCart(cart);
	        }
	}
	
	//장바구니 상세보기
	public List<CartProductDTO> getCartDetails() {
        return productMapper.getCartDetails();
    }
}
