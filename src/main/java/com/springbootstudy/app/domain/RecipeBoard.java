package com.springbootstudy.app.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeBoard {

    private int boardNo;     //게시판 고유값
    private String foodName; // 음식이름
    private String boardTitle; // 레시피 제목
    private String boardContent; // 레시피 간단한 소개
    private String foodGenre;// 음식 장르 (한중일양기)
    private int boardView;	 	//게시판 본수
    private Timestamp regDate; // 올린날짜
    private String thumbnail; // 섭내일 파일
    private int foodTime;    //조리시간
    private int numberEaters; // 인원수
    private int apoint;      // 평점
    private String memberId; // 아이디

    // 연관 관계는 제거하고, 필요시 별도의 서비스에서 조합하여 사용
    private List<Cooking> cookingList;  // 조리과정리스트
    private List<Material> materialList;// 요리 재료리스트

    // Getter, Setter, toString 등 필요한 메서드 추가
}
