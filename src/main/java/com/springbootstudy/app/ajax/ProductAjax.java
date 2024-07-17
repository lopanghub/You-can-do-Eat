package com.springbootstudy.app.ajax;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springbootstudy.app.domain.Product;
import com.springbootstudy.app.dto.CartProductDTO;
import com.springbootstudy.app.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ProductAjax {

	@Autowired
	private ProductService productService;

	// shopmain페이지에서 카테고리 클릭시 해당 카테고리로 상품리스트 출력
	@GetMapping("/products/{category}")
	public List<Product> loadCategory(@PathVariable("category") String category) {

		List<Product> productBox = productService.getProductByCategory(category);
		productBox.forEach(product -> log.info(product.toString())); // 로그에 각 상품 정보를 출력
		return productBox;
	}
	

	// 수량변동시 테이블 수량도 변동
	@PostMapping("/updateCart")
	public ResponseEntity<Void> updateCart(@RequestBody CartProductDTO cartProductDTO) {
		productService.updateCartQuantity(cartProductDTO.getProductId(), cartProductDTO.getQuantity());
		return ResponseEntity.ok().build();
	}

	// 장바구니 항목 삭제
	@PostMapping("/deleteCart")
	public ResponseEntity<Void> deleteCart(@RequestBody CartProductDTO cartProductDTO) {
		productService.deleteCart(cartProductDTO.getProductId());
		return ResponseEntity.ok().build();
	}
	
	

	
	
	


}
