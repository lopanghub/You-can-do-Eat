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
                    <span name="materials[${materialIndex}].materialName" value="${materialName}">${materialName}</span>
                </div>
                <div class="col-md-3">
                    <span id="mensuration" name="materials[${materialIndex}].mensuration">${mensuration}</span>
                </div>
                <div class="col-md-3">
                    <span id="typeMaterial" name="materials[${materialIndex}].typeMaterial">${typeMaterial}</span>
                </div>
                <div class="col-md-3">
                    <i class="bi bi-trash3 deleteMaterialBtn" data-index="${materialIndex}"></i>
                </div>
            </div>
        `;
        const newMaterialTrue = `
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
                    <div class="form-check form-switch">
                        <input class="form-check-input material-check" type="checkbox" role="switch" id="Meterialcheck-${materialIndex}" name="materials[${materialIndex}].checked" checked>
                        <label class="form-check-label" for="Meterialcheck-${materialIndex}">등록하기</label>
                    </div>
                </div>
            </div>
        `;

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
        const cookMaterialElement = document.querySelector(`.cookMetrialTextPlace #material-${index}`);
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
            <div  id="cooking-${cookingIndex}">
            <div class="row my-2">
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
                  </div>
                  <div class="cookMing"></div
                  
            </div>
        `;
        cookingSection.append(newCooking);

        $(".material-check:checked").each(function() {
            let index = $(this).attr('id').split('-')[1];
            let materialName = $(`span[name='materials[${index}].materialName']`).text();
            let mensuration = $(`span[name='materials[${index}].mensuration']`).text();
            let typeMaterial = $(`span[name='materials[${index}].typeMaterial']`).text();
            let cookMing = $(".cookMing");
            const newCookingMetrial = `
                <div class="row my-2">
                    <div class="col-md-3 mb-3">
                        <span name="cookings[${cookingIndex}].materialName">${materialName}</span>
                    </div>
                    <div class="col-md-3 mb-3">
                        <span name="cookings[${cookingIndex}].mensuration">${mensuration}</span>
                    </div>
                    <div class="col-md-3 mb-3">
                        <span name="cookings[${cookingIndex}].typeMaterial">${typeMaterial}</span>
                    </div>
                   
                </div>
            `;
            cookMing.append(newCookingMetrial);
            
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
