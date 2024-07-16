package com.springbootstudy.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootstudy.app.domain.ShopReview;
import com.springbootstudy.app.mapper.ShopReviewMapper;

@Service
public class ShopReviewService {

    @Autowired
    private ShopReviewMapper reviewMapper;

    public List<ShopReview> getReviewsByProductId(int productId) {
        return reviewMapper.getReviewsByProductId(productId);
    }

    public void addReview(ShopReview review) {
        reviewMapper.insertReview(review);
    }
    
    public void deleteReviewById(int reviewId) {
        reviewMapper.deleteReviewById(reviewId);
    }
}
