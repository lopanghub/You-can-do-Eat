package com.springbootstudy.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.springbootstudy.app.domain.ShopReview;

@Mapper
public interface ShopReviewMapper {

    List<ShopReview> getReviewsByProductId(@Param("productId") int productId);

    void insertReview(ShopReview review);
    
    void deleteReviewById(int reviewId);
}
