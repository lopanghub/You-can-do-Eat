package com.springbootstudy.app.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopReview {
	private int reviewId;
	private String id; // 외래키: Member의 ID
	private int productId;		// 외래키: Product의 ID
	private String reviewComment;
	private double rating;
	private Timestamp regDate;
	
}
