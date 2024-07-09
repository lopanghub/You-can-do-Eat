package com.springbootstudy.app.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.springbootstudy.app.domain.MemberShip;

@Mapper
public interface MemberMapper {
	void insertMember(MemberShip member);

	MemberShip getMemberShip(String id);
}
