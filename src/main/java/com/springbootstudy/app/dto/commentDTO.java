package com.springbootstudy.app.dto;

import java.sql.Timestamp;
import java.util.List;

import com.springbootstudy.app.domain.Comment;

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
public class commentDTO {
	    private Comment comment;
	    private String stars;
}
