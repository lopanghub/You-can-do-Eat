package com.springbootstudy.app.ajax;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.springbootstudy.app.domain.Product;
import com.springbootstudy.app.service.ProductService;

@RestController
public class ProductAjax {
    
    @Autowired
    private ProductService productService;
    
    @GetMapping("/products/{category}")
    public List<Product> loadCategory(@PathVariable("category") String category) {
        List<Product> productBox = productService.getProductByCategory(category);
        return productBox;
    }
}
