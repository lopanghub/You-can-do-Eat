package com.youcandoeat.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youcandoeat.app.domain.Memo;
import com.youcandoeat.app.mapper.MemoMapper;

import lombok.extern.slf4j.Slf4j;

//MemoService 클래스가 서비스 계층의 스프링 빈(Bean) 임을 정의
@Service
@Slf4j
public class MemoService {
	
	@Autowired	
	private MemoMapper memoMapper;
	
	public List<Memo> memoList(){
		log.info("service: memoList()");
		return memoMapper.memoList();
		//return memoMapper.findAll();
	}
	
	public Memo getMemo(int no) {
		log.info("serivce: getMemo(int no)");
		return memoMapper.findByNo(no);
	}
	
	public void addMemo(Memo memo) {
		log.info("service: addMemo(Memo memo)");
		memoMapper.addMemo(memo);
	}
	
	public void updateMemo(Memo memo) {
		log.info("service: updateMemo(Memo memo)");
		memoMapper.updateMemo(memo);
	}
	public void deleteMemo(int no) {
		log.info("service: deleteMemo(int no)");
		memoMapper.deleteMemo(no);
	}
}
