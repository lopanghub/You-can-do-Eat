package com.springbootstudy.app.domain;

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
public class CookMaterial {

    private int materialId;
    private int cookingId;
    private int boardNo; // ManyToOne 관계가 있던 RecipeBoard 대신 boardNo 필드를 사용합니다.

    // Getter, Setter, toString 등 필요한 메서드 추가
}
