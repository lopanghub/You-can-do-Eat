package com.springbootstudy.app.controller;

import org.springframework.web.bind.annotation.RestController;

import com.springbootstudy.app.domain.Product;
import com.springbootstudy.app.dto.CartProductDTO;
import com.springbootstudy.app.service.ProductService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


//상품 컨트롤러
//담당자 - 이현학
@Controller
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductService productService;
	
//	@GetMapping("/shopMain")
//	public List<Product> getProductByCategory(@RequestParam(value="category", required=false) String category) {
//		return productService.getProductByCategory(category);
//	}
	
	//상품 상세보기 페이지
	@GetMapping("/shopDetail")
    public String getProductByID(Model model, @RequestParam("productId") int productId) {
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);
        return "views/shopDetail";
    }
	
	//장바구니 추가하기 버튼 누를시
	@PostMapping("/addCart")
	public String addCart(@RequestParam("productId") int productId,
				@RequestParam("quantity") int quantity, Model model) {
		productService.addCart(productId, quantity);
		
		return "redirect:/shopDetail?productId="+productId;
	}
	
	//장바구니 페이지로 이동
	@GetMapping("/shopCart")
    public String getCartDetails(Model model) {
        List<CartProductDTO> cartDetails = productService.getCartDetails();
        model.addAttribute("cartDetails", cartDetails);
        return "views/shopCart";
    }
	
	//바로구매 버튼 누를시
	@PostMapping("/orderNow")
	public String orderNow(Model model, @RequestParam("productId") int productId,
			@RequestParam("quantity") int quantity) {
		productService.addCart(productId, quantity);
		CartProductDTO cart = productService.getCartDetailsById(productId);
		model.addAttribute("cart", cart);
		
		return "views/shopOrder";
	}
}
