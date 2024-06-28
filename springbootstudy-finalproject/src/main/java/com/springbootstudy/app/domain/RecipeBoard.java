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

    private int boardNo;
    private String foodName;
    private String boardTitle;
    private String boardContent;
    private String foodGenre;
    private int boardCommend;
    private int boardView;
    private Timestamp regDate;
    private String thumbnail;
    private int foodTime;
    private int numberEaters;
    private int apoint;
    private String memberId;

    // 연관 관계는 제거하고, 필요시 별도의 서비스에서 조합하여 사용
    private List<Cooking> cookingList;
    private List<Material> materialList;

    // Getter, Setter, toString 등 필요한 메서드 추가
}
