package com.springbootstudy.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootstudy.app.domain.Product;
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
	
	public Product getProductById(String productId) {
		log.info("service : getProductById(String productId)");
		return productMapper.findById(productId);
	}
	
	//카테고리 별 분류 
	public List<Product> getProductByCategory(String category) {
		log.info("service : getProductByCategory(String category)");
		return productMapper.findByCategory(category);
	}
}
