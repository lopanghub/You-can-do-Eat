package com.springbootstudy.app.controller;

import com.springbootstudy.app.domain.MemberShip;
import com.springbootstudy.app.domain.Product;
import com.springbootstudy.app.dto.CartProductDTO;
import com.springbootstudy.app.service.ProductService;
import com.springbootstudy.app.service.S3Service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	private final S3Service s3Service;
	
	//상품 등록
	 // 상품 등록
    @PostMapping("/insertProduct")
    public String insertProduct(
            @RequestParam("productName") String productName,
            @RequestParam("price") int price,
            @RequestParam("category") String category,
            @RequestParam("ingredient") String ingredient,
            @RequestParam("productImage") MultipartFile productImage,
            @RequestParam("detailImage") MultipartFile detailImage,
            Model model) {

        Product product = new Product();
        product.setProductName(productName);
        product.setPrice(price);
        product.setCategory(category);
        product.setIngredient(ingredient);

        try {
            if (!productImage.isEmpty()) {
                String productImageName = saveFile(productImage);
                product.setProductImage(productImageName);
            }
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Product image upload failed");
        }

        try {
            if (!detailImage.isEmpty()) {
                String detailImageName = saveFile(detailImage);
                product.setDetailImage(detailImageName);
            }
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Detail image upload failed");
        }

        productService.insertProduct(product);

        return "redirect:/shopMain";
    }

    private String saveFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String saveDirPath = Paths.get(System.getProperty("user.dir"), DEFAULT_PATH).toString();
        File saveDir = new File(saveDirPath);

        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }

        File saveFile = new File(saveDir, fileName);
        file.transferTo(saveFile);
        return fileName;
    }
	// 상품 삭제 처리
	@PostMapping("/deleteProduct")
    public String deleteProduct(@RequestParam("productId") int productId) {
        productService.deleteProduct(productId);
        return "redirect:/shopMain";  // 삭제 후 상품 목록 페이지로 리디렉션
    }
	

   

    @PostMapping("/updateForm")
    public String productToUpdate(@RequestParam(name = "productId") int productId, Model model) {
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);
        return "views/shop/shopUpdate";
    }

    @GetMapping("/shopDetail")
    public String getProductByID(Model model, HttpSession session, @RequestParam(name = "productId") int productId,
                                 RedirectAttributes redirectAttributes) {
        MemberShip member = (MemberShip) session.getAttribute("member");

        if (member == null) {
            redirectAttributes.addFlashAttribute("message", "로그인 후 이용해주세요.");
            return "redirect:/login";
        }

        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);
        return "views/shop/shopDetail";
    }

	// shopDetails-장바구니 추가하기 버튼 누를시
	@PostMapping("/addCart")
	public String addCart(@RequestParam("productId") int productId, @RequestParam("quantity") int quantity, Model model,
			HttpSession session) {

		productService.addCart(productId, quantity);

		return "redirect:/shopDetail?productId=" + productId;
	}

	// shopDetails-바로구매 버튼 누를시
	@PostMapping("/orderNow")
	public String orderNow(Model model, @RequestParam("productId") int productId,
			@RequestParam("quantity") int quantity, HttpSession session) {
		productService.addCart(productId, quantity);
		CartProductDTO cart = productService.getCartDetailsById(productId);
		int totalAmount = 0;
		if (cart!= null) {
			totalAmount = cart.getQuantity() * cart.getPrice();
		}	
		session.setAttribute("cart", cart);
		session.removeAttribute("cartDetails");
		session.setAttribute("totalAmount", totalAmount);
		return "redirect:/shopOrder";
	}

	// 장바구니 페이지로 이동
	@GetMapping("/shopCart")
	public String getCartDetails(Model model) {
		List<CartProductDTO> cartDetails = productService.getCartDetails();
		model.addAttribute("cartDetails", cartDetails);
		return "views/shop/shopCart";
	}

	// shopCart- 구매버튼 누를시 구매 페이지 이동
	@PostMapping("/cartToOrder")
	public String cartToOrder(HttpSession session) {
		List<CartProductDTO> cartDetails = productService.getCartDetails();
		for(CartProductDTO product : cartDetails) {
			System.out.println(product);
		}
		
		session.setAttribute("cartDetails", cartDetails);
		session.removeAttribute("cart");
		return "redirect:/shopOrder";

	}

	// 구매 페이지 진입시
	@GetMapping("/shopOrder")
	public String showOrderPage(HttpSession session, Model model) {
	    CartProductDTO cart = (CartProductDTO) session.getAttribute("cart");
	    List<CartProductDTO> cartDetails = (List<CartProductDTO>) session.getAttribute("cartDetails");
	    MemberShip member = (MemberShip) session.getAttribute("member");
	    Integer totalAmount = (Integer) session.getAttribute("totalAmount"); // 세션에서 totalAmount 가져오기

	    if (totalAmount == null) {
	        totalAmount = 0;
	        if (cartDetails != null) {
	            totalAmount = cartDetails.stream().mapToInt(item -> item.getQuantity() * item.getPrice()).sum();
	        }
	    }

	    model.addAttribute("member", member);
	    model.addAttribute("cart", cart);
	    model.addAttribute("cartDetails", cartDetails);
	    model.addAttribute("totalAmount", totalAmount); // 모델에 totalAmount 설정

	    return "views/shop/shopOrder";
	}

	

}
