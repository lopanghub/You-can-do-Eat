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
        let materials = [];
         // 재료 객체 생성
		    let material = {
		        materialName: materialName,
		        mensuration: mensuration,
		        typeMaterial: typeMaterial
		    };
		    materials.push(material);
        const newMaterial = `
		
            <div class="row g-3 align-items-center my-2" id="material-${materialIndex}">
                <div class="col-md-3">
                    <span id="materials[${materialIndex}].materialName" >${materialName}</span>
                    <input type="hidden" name="materials[${materialIndex}].materialName" value="${materialName}" />
                </div>
                <div class="col-md-3">
                    <span id="materials[${materialIndex}].mensuration" >${mensuration}</span>
                    <input type="hidden" name="materials[${materialIndex}].mensuration" value="${mensuration}" />
                </div>
                <div class="col-md-3">
                    <span id="materials[${materialIndex}].typeMaterial">${typeMaterial}</span>
                    <input type="hidden" name="materials[${materialIndex}].typeMaterial" value="${typeMaterial}" />
                </div>
                <div class="col-md-3">
                    <i class="bi bi-trash3 deleteMaterialBtn" data-index="${materialIndex}"></i>
                </div>
            </div>
        `;
        const newMaterialTrue = `
            <div class="row g-3 align-items-center my-2" id="material-true-${materialIndex}">
                <div class="col-md-3">
                    <span id="isMaterials[${materialIndex}].materialName" name="isMaterials[${materialIndex}].materialName">${materialName}</span>
                </div>
                <div class="col-md-3">
                    <span id="isMaterials[${materialIndex}].mensuration" name="isMaterials[${materialIndex}].mensuration">${mensuration}</span>
                </div>
                <div class="col-md-3">
                    <span id="isMaterials[${materialIndex}].typeMaterial" name="isMaterials[${materialIndex}].typeMaterial">${typeMaterial}</span>
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
                    <span  id="cookTitle">${cookTitle}</span>
                    <input type="hidden" name="cookings[${cookingIndex}].cookTitle" value="${cookTitle}">
                </div>
                <div class="col-md-3 mb-3">
                    <span id="cookMethod" >${cookMethod}</span>
                    <input type="hidden" name="cookings[${cookingIndex}].cookMethod" value="${cookMethod}">
                </div>
                <div class="col-md-3 mb-3">
                    <span id="recommended">${recommended}</span>
                    <input type="hidden" name="cookings[${cookingIndex}].recommended" value="${recommended}">
                </div>
                <div class="col-md-3 mb-3">
                    <span id="cookFile" >${cookFile}</span>
                    <input type="hidden" name="cookings[${cookingIndex}].addCookFile" value="${cookFile}">
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
            let materialName = $(`span[name='isMaterials[${index}].materialName']`).text();
            let mensuration = $(`span[name='isMaterials[${index}].mensuration']`).text();
            let typeMaterial = $(`span[name='isMaterials[${index}].typeMaterial']`).text();
            const newCookingMetrial = `
                <div class="row my-2">
                    <div class="col-md-3 mb-3">
                        <span >${materialName}</span>
                        <input type="hidden" name="cookings[${cookingIndex}].cookMaterials[${index}].materialName" value="${materialName}">
                    </div>
                    <div class="col-md-3 mb-3">
                        <span >${mensuration}</span>
                        <input type="hidden" name="cookings[${cookingIndex}].cookMaterials[${index}].mensuration" value="${mensuration}">
                    </div>
                    <div class="col-md-3 mb-3">
                        <span >${typeMaterial}</span>
                        <input type="hidden" name="cookings[${cookingIndex}].cookMaterials[${index}].typeMaterial" value="${typeMaterial}">
                    </div>
                </div>
            `;
            index++;
                         console.log("cookMaterials["+index+"].materialName" +materialName);
                         console.log("cookMaterials["+index+"].mensuration" +mensuration);
                         console.log("cookMaterials["+index+"].typeMaterial" +typeMaterial);
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
