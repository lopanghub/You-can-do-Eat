package com.springbootstudy.app.controller;

import org.springframework.web.bind.annotation.RestController;

import com.springbootstudy.app.domain.MemberShip;
import com.springbootstudy.app.domain.Product;
import com.springbootstudy.app.dto.CartProductDTO;
import com.springbootstudy.app.service.ProductService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


//상품 컨트롤러
//담당자 - 이현학
@Controller
@RequiredArgsConstructor
@Slf4j
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
        return "views/shop/shopDetail";
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
        return "views/shop/shopCart";
    }
	
	//바로구매 버튼 누를시
	@PostMapping("/orderNow")
	public String orderNow(Model model, @RequestParam("productId") int productId,
			@RequestParam("quantity") int quantity, HttpSession session) {
		productService.addCart(productId, quantity);
		CartProductDTO cart = productService.getCartDetailsById(productId);
		session.setAttribute("cart", cart);
		session.removeAttribute("cartDetails"); 
		return "redirect:/shopOrder";
	}
	
	//장바구니에서 구매버튼 누를시 구매 페이지 이동 
	@PostMapping("/cartToOrder")
	public String cartToOrder(HttpSession session){
		List<CartProductDTO> cartDetails = productService.getCartDetails();
	    session.setAttribute("cartDetails", cartDetails);
	    session.removeAttribute("cart");
	    return "redirect:/shopOrder";

	}
	
	//구매 페이지 진입시
	@GetMapping("/shopOrder")
	public String showOrderPage(HttpSession session, Model model) {
		
	    CartProductDTO cart = (CartProductDTO) session.getAttribute("cart");
	    List<CartProductDTO> cartDetails = (List<CartProductDTO>) session.getAttribute("cartDetails");
        MemberShip member = (MemberShip) session.getAttribute("member");
        
        
        //결제창 총액 계산 
        int totalAmount = 0;
        int totalDiscount = 0;
        int totalShipping = 0;
        if (cartDetails != null) {
            totalAmount = cartDetails.stream().mapToInt(item -> item.getQuantity() * item.getPrice()).sum();
//            totalDiscount = cartDetails.stream().mapToDouble(CartProductDTO::getDiscount).sum();
//            totalShipping = cartDetails.stream().mapToDouble(CartProductDTO::getShipping).sum();
        }
        int finalAmount = totalAmount - totalDiscount + totalShipping;
        
	    model.addAttribute("member", member);
	    model.addAttribute("cart", cart);
	    model.addAttribute("cartDetails", cartDetails);
	    model.addAttribute("totalAmount", totalAmount);
	    model.addAttribute("totalDiscount", totalDiscount);
	    model.addAttribute("totalShipping", totalShipping);
	    model.addAttribute("finalAmount", finalAmount);
	    
	    return "views/shop/shopOrder";
	}
	
	
}
