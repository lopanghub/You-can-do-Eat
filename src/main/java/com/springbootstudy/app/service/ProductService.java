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

	private List<Product> findAll() {
		log.info("service : findAll()");
		return productMapper.findAll();
	}

	public Product getProductById(int productId) {
		log.info("service : getProductById(int productId)");
		Product product = productMapper.findById(productId);
		log.info("Retrieved product: " + product);
		return product;
	}

	// 카테고리 별 분류
	public List<Product> getProductByCategory(String category) {
		log.info("service : getProductByCategory(String category)");
		return productMapper.findByCategory(category);
	}
	
	public void updateProduct(Product product) {
        productMapper.updateProduct(product);
    }
	
	//업데이트 이미지 ajax시 db에 저장
	 public void updateProductImage(int productId, String productImage) {
	        productMapper.updateProductImage(productId, productImage);
	    }

	    public void updateDetailImage(int productId, String detailImage) {
	        productMapper.updateDetailImage(productId, detailImage);
	    }
	
	public void deleteProduct(int productId) {
		productMapper.deleteProduct(productId);
	}
	
	public void insertProduct(Product product) {
		productMapper.insertProduct(product);
	}

	// 장바구니 추가
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

	// 장바구니 상세보기
	public List<CartProductDTO> getCartDetails() {
		List<CartProductDTO> cartDetails = productMapper.getCartDetails();
        
        return cartDetails;

	}

	// 바로 구매시 단일 상품 정보보기
	public CartProductDTO getCartDetailsById(int productId) {
		return productMapper.getCartDetailsById(productId);
	}

	// 장바구니 수량 변동
	public void updateCartQuantity(int productId, int quantity) {
		Cart cart = productMapper.getCartByProductId(productId);
		if (cart != null) {
			cart.setQuantity(quantity);
			productMapper.updateCart(cart);
		}
	}

	// 장바구니 삭제
	public void deleteCart(int productId) {
		productMapper.deleteCartByProductId(productId);
	}
	
	//검색기능
	public List<Product> searchProducts(String query) {
        return productMapper.searchProducts(query);
    }
	
	public List<Product> searchProductsByCategory(String category) {
        return productMapper.searchProductsByCategory(category);
    }
}
