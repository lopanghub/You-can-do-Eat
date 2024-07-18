package com.springbootstudy.app.domain;

import java.util.List;

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
public class Cooking {

    private int cookingId;
    private int boardNo; // ManyToOne 관계가 있던 RecipeBoard 대신 boardNo 필드를 사용합니다.
    private String cookMethod;
    private String recommended;
    private String cookFile;
    private String encodedFileName;
    
    private List<CookMaterial> cookMaterials;

    // Getter, Setter, toString 등 필요한 메서드 추가
}
