$(function() {
    let materialIndex = 0;
    let cookingIndex = 0;
    let isTextaddM = true; // addMaterial를 눌렀을때
    let isTextaddC = true; // addCooking를 눌렀을때

    $("#addMaterial").on("click", addMaterial);

    function addMaterial() {
        const materialSection = $('#materialSection');
        const cookMetrial = $(".cookMetrialTextPlace");
        if (isTextaddM) {
            isTextaddM = false;
            const newHeadMaterial = `
                <div class="row g-3 align-items-center my-2">
                    <div class="col-md-3">
                        <span>재료</span>
                    </div>
                    <div class="col-md-3">
                        <span>수량</span>
                    </div>
                    <div class="col-md-3">
                        <span>분류</span>
                    </div>
                    <div class="col-md-3">
                    </div>
                </div>
            `;
            materialSection.append(newHeadMaterial);
        }

        let materialName = $("#materialName").val();
        let mensuration = $("#mensuration").val();
        let typeMaterial = $("#typeMaterial").val();
        const newMaterial = `
            <div class="row g-3 align-items-center my-2" id="material-${materialIndex}">
                <div class="col-md-3">
                    <span id="materials[${materialIndex}].materialName" name="materials[${materialIndex}].materialName" value="${materialName}">${materialName}</span>
                </div>
                <div class="col-md-3">
                    <span id="materials[${materialIndex}].mensuration" name="materials[${materialIndex}].mensuration">${mensuration}</span>
                </div>
                <div class="col-md-3">
                    <span id="materials[${materialIndex}].typeMaterial" name="materials[${materialIndex}].typeMaterial">${typeMaterial}</span>
                </div>
                <div class="col-md-3">
                    <i class="bi bi-trash3 deleteMaterialBtn" data-index="${materialIndex}"></i>
                </div>
            </div>
        `;
        const newMaterialTrue = `
            <div class="row g-3 align-items-center my-2" id="material-true-${materialIndex}">
                <div class="col-md-3">
                    <span id="cookMaterials[${materialIndex}].materialName" name="cookMaterials[${materialIndex}].materialName" value="cookMaterials[${materialIndex}].materialName">${materialName}</span>
                </div>
                <div class="col-md-3">
                    <span id="cookMaterials[${materialIndex}].mensuration" name="cookMaterials[${materialIndex}].mensuration">${mensuration}</span>
                </div>
                <div class="col-md-3">
                    <span id="cookMaterials[${materialIndex}].typeMaterial" name="cookMaterials[${materialIndex}].typeMaterial">${typeMaterial}</span>
                </div>
                <div class="col-md-3">
                    <div class="form-check form-switch">
                        <input class="form-check-input material-check" type="checkbox" role="switch" id="Meterialcheck-${materialIndex}" name="materials[${materialIndex}].checked" checked>
                        <label class="form-check-label" for="Meterialcheck-${materialIndex}">등록하기</label>
                    </div>
                </div>
            </div>
        `;
		console.log(newMaterialTrue);
        materialSection.append(newMaterial);
        cookMetrial.append(newMaterialTrue);
        materialIndex++;
    }

    // 재료 제거
    $(document).on("click", ".deleteMaterialBtn", function() {
        const index = $(this).data('index');
        removeMaterial(index);
    });

    function removeMaterial(index) {
        const materialElement = document.getElementById(`material-${index}`);
        const cookMaterialElement = document.querySelector(`.cookMetrialTextPlace #material-true-${index}`);
        if (materialElement) {
            materialElement.remove();
        }
        if (cookMaterialElement) {
            cookMaterialElement.remove();
        }
    }

    // 조리과정추가
    $("#addCooking").on("click", addCooking);

    function addCooking() {
        let cookTitle = $("#cookTitle").val();
        let cookMethod = $("#cookMethod").val();
        let recommended = $("#recommended").val();
        let cookFile = $("#cookFile").val();

        const cookingSection = $('#cookingSection');
        if (isTextaddC) {
            isTextaddC = false;
            const newHeadCooking = `
                <div class="row my-2">
                    <div class="col text-center">
                        <h3>요리과정 추가</h3>
                    </div>
                </div>
                <div class="row my-2">
                    <div class="col-md-3">
                        <span>요리 제목</span>
                    </div>
                    <div class="col-md-3">
                        <span>요리 설명</span>
                    </div>
                    <div class="col-md-3">
                        <span>주의할점</span>
                    </div>
                    <div class="col-md-3">
                        <span>조리과정사진</span>
                    </div>
                </div>
            `;
            cookingSection.append(newHeadCooking);
        }

         const newCooking = `
            <div class="row my-2" id="cooking-${cookingIndex}">
                <div class="col-md-3 mb-3">
                    <span name="cookings[${cookingIndex}].cookTitle" id="cookTitle">${cookTitle}</span>
                </div>
                <div class="col-md-3 mb-3">
                    <span id="cookMethod" name="cookings[${cookingIndex}].cookMethod">${cookMethod}</span>
                </div>
                <div class="col-md-3 mb-3">
                    <span id="recommended" name="cookings[${cookingIndex}].recommended">${recommended}</span>
                </div>
                <div class="col-md-3 mb-3">
                    <span id="cookFile" name="cookings[${cookingIndex}].cookFile">${cookFile}</span>
                </div>
                <div class="col-md-3 mb-3">
                    <i class="bi bi-trash3 deleteCookingBtn" data-index="${cookingIndex}"></i>
                </div>
                <div class="cookMing"></div>
            </div>
        `;
        cookingSection.append(newCooking);

        $(".material-check:checked").each(function() {
            let index = $(this).attr('id').split('-')[1];
            let IndexMaterialName = $(`span[name='cookMaterials[${index}].materialName']`).text();
            let IndexMensuration = $(`span[name='cookMaterials[${index}].mensuration']`).text();
            let IndexTypeMaterial = $(`span[name='cookMaterials[${index}].typeMaterial']`).text();
            console.log("IndexMensuration 제발 두번 출렷x "+IndexMensuration);	
            const newCookingMetrial = `
                <div class="row my-2">
                    <div class="col-md-3 mb-3">
                        <span name="cookings[${index}].materialName">${IndexMaterialName}</span>
                    </div>
                    <div class="col-md-3 mb-3">
                        <span name="cookings[${index}].mensuration">${IndexMensuration}</span>
                    </div>
                    <div class="col-md-3 mb-3">
                        <span name="cookings[${index}].typeMaterial">${IndexTypeMaterial}</span>
                    </div>
                </div>
            `;
            $(`#cooking-${cookingIndex} .cookMing`).append(newCookingMetrial);
        });
        cookingIndex++;
    }

    // 요리 과정 제거
    $(document).on("click", ".deleteCookingBtn", function() {
        const index = $(this).data('index');
        removeCooking(index);
    });

    function removeCooking(index) {
        const cookingElement = document.getElementById(`cooking-${index}`);
        if (cookingElement) {
            cookingElement.remove();
        }
    }
});
