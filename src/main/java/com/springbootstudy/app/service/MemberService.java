package com.springbootstudy.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springbootstudy.app.domain.MemberShip;
import com.springbootstudy.app.mapper.MemberMapper;

@Service
public class MemberService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MemberMapper memberMapper;

	// DB에서 id 있는지 확인
	public boolean isIdcheck(String id) {

		MemberShip member = memberMapper.getMemberShip(id);

		if (member == null) {
			return false;
		}
		return true;
	}
	
	public MemberShip getMemberById(String id) {
        return memberMapper.getMemberShip(id);
    }

	// DB에서 pass 있는지 확인
	public boolean isPasscheck(String id, String pass) {

		String dbPass = memberMapper.memberPassCheck(id);

		boolean result = false;

		if (passwordEncoder.matches(pass, dbPass)) {
			result = true;
		}
		return result;
	}

	// 회원정보를 DB에 넣기
	public void addMember(MemberShip member) {
		member.setPass(passwordEncoder.encode(member.getPass()));
		memberMapper.insertMember(member);
	}
	
	// 회원정보를 DB에 update 하기
	public void updateMember(MemberShip member) {
		//member.setPass(passwordEncoder.encode(member.getPass()));
		memberMapper.updateMember(member);
	}

	// login 처리 메서드
	public int login(String id, String pass) {

		MemberShip m = memberMapper.getMemberShip(id);

		int result = -1;

		if (m == null) {
			return result;
		}

		if (passwordEncoder.matches(pass, m.getPass())) {
			result = 1;
		} else {
			result = 0;
		}

		return result;
	}

	public MemberShip getMember(String id) {
		return memberMapper.getMemberShip(id);
	}
}
