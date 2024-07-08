package com.springbootstudy.app.controller;

import org.springframework.web.bind.annotation.RestController;

import com.springbootstudy.app.domain.Product;
import com.springbootstudy.app.service.ProductService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	
	@GetMapping("/shopDetail")
    public String getProductByID(Model model, @RequestParam("id") int id) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "views/shopDetail";
    }
	
}
