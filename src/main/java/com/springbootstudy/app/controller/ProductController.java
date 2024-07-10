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

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
		if (session == null) {
	        log.error("세션이 null입니다.");
	        return "redirect:/login"; // 세션이 null이면 로그인 페이지로 리디렉션
	    }

	    log.info("세션 ID={}", session.getId());
	    CartProductDTO cart = (CartProductDTO) session.getAttribute("cart");
	    List<CartProductDTO> cartDetails = (List<CartProductDTO>) session.getAttribute("cartDetails");
        MemberShip member = (MemberShip) session.getAttribute("member");
        
     // 로그로 세션에 있는 회원 정보 확인
        if (member != null) {
            log.info("회원 정보: 이름={}, 이메일={}, 전화번호={}, 주소={}", member.getName(), member.getEmail(), member.getMobile(), member.getAddress1());
        } else {
            log.info("세션에 회원 정보가 없습니다.");
        }

	    model.addAttribute("member", member);
	    model.addAttribute("cart", cart);
	    model.addAttribute("cartDetails", cartDetails);
	    return "views/shop/shopOrder";
	}

}
