package com.youcandoeat.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.youcandoeat.app.domain.Memo;

@Mapper
public interface MemoMapper {
	// 맵퍼 인터페이스는 애노테이션을 사용해 SQL 쿼리를 직접 맵핑 할 수 있다.
	@Select("Select * From memo")
	List <Memo> memoList();
	
	/* 맵퍼 인터페이스는 별도의 XML 맵퍼 파일에 SQL 쿼리를 정의해 놓고 그 쿼리를
	* 사용할 수 있는데 이 때 맵퍼 인터페이스는 XML 맵퍼에 정의한 SQL 쿼리 중에서
	* 인터페이스의 메서드와 id가 동일한 SQL 쿼리(맵핑 구문)를 찾아 실행한다.
	**/
	List<Memo> findAll();
	
	// no에 해당하는 메모를 테이블에서 읽어오는 메서드
	Memo findByNo(int no);
	
	// 메모를 테이블에 저장하는 메서드
	void addMemo(Memo memo);
	// no에 해당하는 메모를 테이블에서 수정하는 메서드
	void updateMemo(Memo memo);
	// no에 해당하는 메모를 테이블에서 삭제하는 메서드
	void deleteMemo(int no);
}
