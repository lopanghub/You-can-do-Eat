package com.springbootstudy.app.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.springbootstudy.app.domain.MemberShip;

@Mapper
public interface MemberMapper {
	void insertMember(MemberShip member);
	
	void updateMember(MemberShip member);

	MemberShip getMemberShip(String id);

	String memberPassCheck(String pass);
	
	void deleteUser(String id);
	
	//담당자 - 이현학
    String getMemberIdByAuthorName(String authorName);
}