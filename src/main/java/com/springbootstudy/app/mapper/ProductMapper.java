package com.springbootstudy.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import com.springbootstudy.app.domain.Cart;
import com.springbootstudy.app.domain.Product;

// 쇼핑몰 상품 매퍼 파일
// 담당자 이현학
@Mapper
public interface ProductMapper {
	
	List<Product> findAll();
	
	//기본키로 특정  상품 확인
	Product findById(@Param("product_id") int productId);
	
	//카테고리 별 분류
    List<Product> findByCategory(@Param("category") String category);
    
    //장바구니 버튼 클릭시 장바구니 추가
   void insertCart(Cart cart);
}
