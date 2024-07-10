package com.springbootstudy.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import com.springbootstudy.app.domain.Cart;
import com.springbootstudy.app.domain.Order;
import com.springbootstudy.app.domain.Product;
import com.springbootstudy.app.dto.CartProductDTO;

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
   
   //장바구니 중복검사
   Cart getCartByProductId(@Param("product_id") int productId);
   
   //장바구니 수량 변경
   void updateCart(Cart cart);

   //장바구니 삭제
   void deleteCartByProductId(@Param("productId") int productId);
   
   //장바구니-상품 결합 리스트
   List<CartProductDTO> getCartDetails();
   
   //바로 구매시 해당 상품정보
   CartProductDTO getCartDetailsById(@Param("product_id") int productId);
   
   //결제시 주문 번호 추가
   void insertOrder(Order order);
   
  

}
