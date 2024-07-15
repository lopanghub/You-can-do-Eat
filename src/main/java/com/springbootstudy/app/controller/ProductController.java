package com.springbootstudy.app.controller;


import com.springbootstudy.app.domain.MemberShip;
import com.springbootstudy.app.domain.Product;
import com.springbootstudy.app.dto.CartProductDTO;
import com.springbootstudy.app.service.ProductService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


//상품 컨트롤러
//담당자 - 이현학
@Controller
@RequiredArgsConstructor
@Slf4j
public class ProductController {
	
	private static final String DEFAULT_PATH = "src/main/resources/static/images/shop/";
	
	private final ProductService productService;
	
	//상품 수정 처리 
	@PostMapping("/update")
    public String updateProduct(@RequestParam("productImage") MultipartFile productImage,
                                @RequestParam("detailImageUpload") MultipartFile detailImageUpload,
                                @RequestParam("productName") String productName,
                                @RequestParam("price") int price,
                                @RequestParam("category") String category,
                                @RequestParam("ingredient") String ingredient,
                                @RequestParam("detailImage") String detailImage,
                                RedirectAttributes redirectAttributes) {

        Product product = new Product();
        product.setProductName(productName);
        product.setPrice(price);
        product.setCategory(category);
        product.setIngredient(ingredient);
        product.setDetailImage(detailImage);

        try {
            if (!productImage.isEmpty()) {
                String productImagePath = saveFile(productImage);
                product.setProductImage("/images/shop/" + productImagePath);
            }

            if (!detailImageUpload.isEmpty()) {
                String detailImagePath = saveFile(detailImageUpload);
                product.setDetailImage("/images/shop/" + detailImagePath);
            }

            // ProductService를 통해 데이터베이스에 저장
            productService.updateProduct(product);

            redirectAttributes.addFlashAttribute("message", "Product updated successfully");

        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Failed to update product");
        }

        return "redirect:/updateStatus";
    }

    private String saveFile(MultipartFile file) throws IOException {
        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path  path = Paths.get(DEFAULT_PATH + filename);
        Files .write(path, file.getBytes());
        return filename;
    }
	
	// 업데이트 페이지로 이동
	@PostMapping("/updateForm")
	public String productToUpdate(@RequestParam(name = "productId") int productId, Model model) {
	    // 상품 정보 가져오기
	    Product product = productService.getProductById(productId);
	    // 모델에 상품 정보 추가
	    model.addAttribute("product", product);
	    // 업데이트 페이지로 이동
	    return "views/shop/shopUpdate";
	}
	
	//상품 상세보기 페이지로 이동
	@GetMapping("/shopDetail")
	public String getProductByID(Model model, HttpSession session, @RequestParam(name = "productId") int productId, RedirectAttributes redirectAttributes) {
	    // 세션에서 member 객체 가져오기
	    MemberShip member = (MemberShip) session.getAttribute("member");

	    // member가 null이면 로그인 페이지로 리다이렉트
	    if (member == null) {
	        redirectAttributes.addFlashAttribute("message", "로그인 후 이용해주세요.");
	        return "redirect:/login";
	    }

	    // member가 null이 아니면 상품 정보 가져오기
	    Product product = productService.getProductById(productId);
	    model.addAttribute("product", product);
	    return "views/shop/shopDetail";
	}
	
	//shopDetails-장바구니 추가하기 버튼 누를시
	@PostMapping("/addCart")
	public String addCart(@RequestParam("productId") int productId,
				@RequestParam("quantity") int quantity, Model model, HttpSession session) {
		
		productService.addCart(productId, quantity);
		
		return "redirect:/shopDetail?productId="+productId;
	}
	
	//shopDetails-바로구매 버튼 누를시
	@PostMapping("/orderNow")
	public String orderNow(Model model, @RequestParam("productId") int productId,
			@RequestParam("quantity") int quantity, HttpSession session) {
		productService.addCart(productId, quantity);
		CartProductDTO cart = productService.getCartDetailsById(productId);
		session.setAttribute("cart", cart);
		session.removeAttribute("cartDetails"); 
		return "redirect:/shopOrder";
	}
	
	//장바구니 페이지로 이동
	@GetMapping("/shopCart")
    public String getCartDetails(Model model) {
        List<CartProductDTO> cartDetails = productService.getCartDetails();
        model.addAttribute("cartDetails", cartDetails);
        return "views/shop/shopCart";
    }
	
	//shopCart- 구매버튼 누를시 구매 페이지 이동 
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
