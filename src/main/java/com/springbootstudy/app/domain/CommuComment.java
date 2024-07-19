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
public class CommuComment {
	int commuCommentId;
	int no;
	String commuCommentContent;
	Timestamp createdAt;
	String memberId;
}
