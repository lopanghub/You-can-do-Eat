$(function() {
    let cookingIndex = $(".cookingSection").length; // 기존 요리 섹션 수를 초기 인덱스로 설정

    // 요리 섹션 추가
    $(document).on("click", "#addCooking", function() {
        let materialIndex = 0; // 요리 섹션마다 새로운 materialIndex 초기화
        let cookingHtml = `
            <div class="row my-2 cooking-item cookingSection" id="cooking${cookingIndex}">
                <div class="col my-2">
                    <div class="form-group row">
                        <label for="cookTitle" class="col-sm-3 col-form-label">요리제목</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="cookTitles" id="cookings${cookingIndex}.cookTitle">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="cookMethod" class="col-sm-3 col-form-label">요리과정</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="cookMethods" id="cookings${cookingIndex}.cookMethod">
                        </div>
                        <label for="recommended" class="col-sm-3 col-form-label">주의사항</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="recommendeds" id="cookings${cookingIndex}.recommended">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="cookFile" class="col-sm-3 col-form-label">조리사진</label>
                        <div class="col-sm-9">
                            <input type="file" class="form-control" name="cookFiles" id="cookings${cookingIndex}.cookFile" onchange="previewFile(event)">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4 mb-3 preview-container">
                        <img class="preview" src="" alt="Image preview">
                    </div>
                </div>
                <div class="row mt-2 cookMaterialSession" id="materials${cookingIndex}">
                </div>
                <div class="row">
                    <div class="col">
                        <input type="button" class="btn btn-primary addMaterial" data-materialindex="0" data-cookingindex="${cookingIndex}" value="재료추가">
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <input type="button" class="btn btn-danger deleteCooking" data-cookingindex="${cookingIndex}" value="조리과정삭제">
                    </div>
                </div>
            </div>
        `;
        $(".cookingSection").last().after(cookingHtml); // 요리 섹션을 마지막으로 추가
        cookingIndex++; // cookingIndex 증가
    });

    // 재료 추가
    $(document).on("click", ".addMaterial", function() {
        let cookingIndex = $(this).data("cookingindex");
        let materialIndex = $(this).data("materialindex");

        // 재료 추가 시, 각 요리 섹션 내에서 사용된 인덱스 중 최대값을 추출하여 새로운 인덱스로 설정
        materialIndex = Math.max(...$(`#materials${cookingIndex} .deleteMaterial`).map(function() {
            return $(this).data("materialindex");
        }).get(), 0) + 1;

        let materialHtml = `
        <div>
            <div class="row mt-2" id="materialRow${materialIndex}">
                <div class="col">재료이름</div>
                <div class="col">
                    <input type="text" class="form-control" id="cooking${cookingIndex}.material${materialIndex}.materialName" name="cookings[${cookingIndex}].cookMaterials[${materialIndex}].materialName">
                </div>
                <div class="col">재료 양</div>
                <div class="col">
                    <input type="text" class="form-control" id="cooking${cookingIndex}.material${materialIndex}.mensuration" name="cookings[${cookingIndex}].cookMaterials[${materialIndex}].mensuration">
                </div>
                <div class="col-3">
                    <select class="form-select" id="cooking${cookingIndex}.material${materialIndex}.typeMaterial" name="cookings[${cookingIndex}].cookMaterials[${materialIndex}].typeMaterial">
                        <option value="재료">재료</option>
                        <option value="조미료">조미료</option>
                    </select>
                </div>
                <div class="col">
                    <input type="button" class="btn btn-danger deleteMaterial" data-materialindex="${materialIndex}" data-cookingindex="${cookingIndex}" value="재료삭제">
                </div>
                <div class="col">
                </div>
            </div>
            <div>
        `;

        $(`#materials${cookingIndex}`).append(materialHtml); // 해당 요리 섹션에 재료 추가
        $(this).data("materialindex", materialIndex + 1); // 다음 재료 인덱스로 증가
    });

    // 요리 섹션 삭제
   $(document).on("click", ".deleteCooking", function() {
    // 조리 섹션의 총 개수 확인
    if ($(".cookingSection").length > 1) {
        let cookingIndexToRemove = $(this).data("cookingindex");
        $(`#cooking${cookingIndexToRemove}`).remove(); // 요리 섹션 삭제

        // 삭제한 요리 섹션 다음 섹션들의 인덱스 조정
        $(".cookingSection").each(function(index) {
            $(this).attr("id", `cooking${index}`); // id 재설정
            $(this).find(".deleteCooking").data("cookingindex", index); // 삭제 버튼 데이터 재설정
            $(this).find(".deleteMaterial").data("cookingindex", index); // 재료 삭제 버튼 데이터 재설정
            $(this).find(".addMaterial").data("cookingindex", index); // 재료 추가 버튼 데이터 재설정
            $(this).find(".cookMaterialSession").attr("id", `materials${index}`); // 재료 섹션 id 재설정

            // 요리 섹션 내의 재료 인덱스 재설정
            $(this).find(".addMaterial").data("materialindex", $(this).find(".row.mt-2").length); // 재료 인덱스 초기화
            $(this).find(".row.mt-2").each(function(materialIndex) {
                $(this).attr("id", `materialRow${materialIndex}`); // 재료 id 재설정
                $(this).find(".deleteMaterial").data("materialindex", materialIndex); // 재료 삭제 버튼 데이터 재설정
                $(this).find("input, select").each(function() {
                    let name = $(this).attr("name");
                    if (name) {
                        $(this).attr("name", name.replace(/\[\d+\]\.cookMaterials\[\d+\]/, `[${index}].cookMaterials[${materialIndex}]`));
                    }
                    let id = $(this).attr("id");
                    if (id) {
                        $(this).attr("id", id.replace(/cooking\d+\.material\d+/, `cooking${index}.material${materialIndex}`));
                    }
                });
            });
        });

        cookingIndex--; // cookingIndex 감소
    } else {
        alert("최소 한 개의 요리 과정이 필요합니다.");
    }
});

    // 재료 삭제
    $(document).on("click", ".deleteMaterial", function() {
        let cookingIndex = $(this).data("cookingindex");
        let materialIndexToRemove = $(this).data("materialindex");
        $(`#materials${cookingIndex} #materialRow${materialIndexToRemove}`).remove(); // 해당 요리 섹션의 재료 삭제

        // 삭제한 재료 다음 섹션들의 인덱스 조정
        $(`#materials${cookingIndex} .row.mt-2`).each(function(materialIndex) {
            $(this).attr("id", `materialRow${materialIndex}`); // 재료 id 재설정
            $(this).find(".deleteMaterial").data("materialindex", materialIndex); // 재료 삭제 버튼 데이터 재설정
            $(this).find("input, select").each(function() {
                let name = $(this).attr("name");
                if (name) {
                    $(this).attr("name", name.replace(/cookMaterials\[\d+\]/, `cookMaterials[${materialIndex}]`));
                }
                let id = $(this).attr("id");
                if (id) {
                    $(this).attr("id", id.replace(/material\d+/, `material${materialIndex}`));
                }
            });
        });

        // 재료 추가 버튼의 materialIndex 데이터 재설정
        $(`#cooking${cookingIndex} .addMaterial`).data("materialindex", $(`#materials${cookingIndex} .row.mt-2`).length);
    });

    // 이미지 미리보기
    window.previewFile = function(event) {
        let preview = $(event.target).closest('.cooking-item').find('.preview');
        let file = event.target.files[0];
        let reader = new FileReader();

        reader.onloadend = function() {
            preview.attr("src", reader.result);
            preview.css("display", "block");
        };

        if (file) {
            reader.readAsDataURL(file);
        } else {
            preview.attr("src", "");
            preview.css("display", "none");
        }
    }
});
