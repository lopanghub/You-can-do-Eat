package com.springbootstudy.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.springbootstudy.app.domain.MemberShip;

@Mapper
public interface UserMapper {
	List<MemberShip> getAllUsers();
}