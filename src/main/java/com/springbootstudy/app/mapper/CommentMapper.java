package com.springbootstudy.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.springbootstudy.app.domain.Comment;

@Mapper
public interface CommentMapper {
	List<Comment> commentList(int boardNo);
}
