package com.springbootstudy.app.ajax;

import com.springbootstudy.app.domain.ShopReview;
import com.springbootstudy.app.service.ShopReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ajax/reviews")
public class ShopReviewController {

    @Autowired
    private ShopReviewService shopReviewService;

    @GetMapping("/product/{productId}")
    public List<ShopReview> getReviewsByProductId(@PathVariable("productId") int productId) {
        return shopReviewService.getReviewsByProductId(productId);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addReview(@RequestBody ShopReview review) {
        shopReviewService.addReview(review);
        return new ResponseEntity<>("Review added successfully", HttpStatus.CREATED);
    }
    
    //리뷰 삭제 메서드
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable("reviewId") int reviewId) {
        shopReviewService.deleteReviewById(reviewId);
        return ResponseEntity.ok().build();
    }
}
