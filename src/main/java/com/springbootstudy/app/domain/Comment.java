package com.springbootstudy.app.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
	int commentId;
	int boardNo;
	String CommentContent;
	int CommentPoint;
	Timestamp createdAt;
}
