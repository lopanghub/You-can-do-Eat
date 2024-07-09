package com.springbootstudy.app.ajax;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.springbootstudy.app.domain.Product;
import com.springbootstudy.app.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ProductAjax {
    
    @Autowired
    private ProductService productService;
    
    @GetMapping("/products/{category}")
    public List<Product> loadCategory(@PathVariable("category") String category) {
        log.info("Loading products for category: " + category);
        List<Product> productBox = productService.getProductByCategory(category);
        productBox.forEach(product -> log.info(product.toString()));  // 로그에 각 상품 정보를 출력
        return productBox;
    }
    

}
