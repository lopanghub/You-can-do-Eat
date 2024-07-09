$(function() {
    let materialIndex = 0;
    let cookingIndex = 1;
    let cookMaterialIndex = 0;
    let materials = [];

    $("#addMaterial").on("click", addMaterial);
    $("#addCooking").on("click", addCooking);

    function addMaterial() {
        let materialName = $("#materialName").val();
        let mensuration = $("#mensuration").val();
        let typeMaterial = $("#typeMaterial").val();

        if (materialName && mensuration && typeMaterial) {
            materials.push({ materialName, mensuration, typeMaterial, index: materialIndex });

            const materialSection = $('#materialSection');
            const cookMaterial = $(".cookMetrialTextPlace");

            const newMaterial = `
                <div class="row g-3 align-items-center my-2" id="material-${materialIndex}">
                    <div class="col-md-3">
                        <span>${materialName}</span>
                        <input type="hidden" name="materialNames" value="${materialName}" />
                    </div>
                    <div class="col-md-3">
                        <span>${mensuration}</span>
                        <input type="hidden" name="mensurations" value="${mensuration}" />
                    </div>
                    <div class="col-md-3">
                        <span>${typeMaterial}</span>
                        <input type="hidden" name="typeMaterials" value="${typeMaterial}" />
                    </div>
                    <div class="col-md-3">
                        <i class="bi bi-trash3 deleteMaterialBtn" data-index="${materialIndex}"></i>
                    </div>
                </div>
            `;
            const newMaterialTrue = `
                <div class="row g-3 align-items-center my-2" id="material-true-${cookMaterialIndex}">
                    <div class="col-md-3">
                        <span>${materialName}</span>
                        <input type="hidden" name="cookMaterialNames" id="material-true-${cookMaterialIndex}.materialName" value="${materialName}"/>
                    </div>
                    <div class="col-md-3">
                        <span>${mensuration}</span>
                        <input type="hidden" name="cookMensuration" id="material-true-${cookMaterialIndex}.mensuration" value="${mensuration}"/>
                    </div>
                    <div class="col-md-3">
                        <span>${typeMaterial}</span>
                        <input type="hidden" name="cookTypeMaterial" id="material-true-${cookMaterialIndex}.typeMaterial" value="${typeMaterial}"/>
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-switch">
                            <input class="form-check-input material-check" type="checkbox" role="switch" id="Meterialcheck-${cookMaterialIndex}" name="materials${materialIndex}.checked" checked>
                        </div>
                    </div>
                </div>
                <div class="cookMing"></div>
            `;
            materialSection.append(newMaterial);
            cookMaterial.append(newMaterialTrue);
            cookMaterialIndex++;
            materialIndex++;
        } else {
            alert("모든 필드를 채워주세요.");
        }
    }

    $(document).on("click", ".deleteMaterialBtn", function() {
        const index = $(this).data('index');
        removeMaterial(index);
        removeMaterialFromCooking(index);
    });

    function removeMaterial(index) {
        $(`#material-${index}`).remove();
        $(`#material-true-${index}`).remove();
        materials = materials.filter(material => material.index !== index);
    }

    function removeMaterialFromCooking(materialIndex) {
        $(`.cookMetrialTextPlace #material-true-${materialIndex}`).remove();
    }

    function addCooking() {
        const cookingSection = $('#cookingSection');
        const newCooking = `
            <div class="cooking-item" id="cooking-${cookingIndex}">
                <div class="row my-2">
                    <div class="col my-2">
                        <div class="form-group row">
                            <label for="cookTitle" class="col-sm-3 col-form-label">요리제목</label>
                            <div class="col-sm-9">
                                <input type="text" id="cookings${cookingIndex}.cookTitle" name="cookTitles" class="form-control">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="cookMethods" class="col-sm-3 col-form-label">요리과정</label>
                            <div class="col-sm-9">
                                <input type="text" name="cookMethods" id="cookings${cookingIndex}.cookMethod" class="form-control">
                            </div>
                            <label for="recommendeds" class="col-sm-3 col-form-label">주의사항</label>
                            <div class="col-sm-9">
                                <input type="text" id="cookings${cookingIndex}.recommended" name="recommendeds" class="form-control">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="cookFile" class="col-sm-3 col-form-label">조리사진</label>
                            <div class="col-sm-9">
                                <input type="file" class="form-control" name="cookFiles" id="cookings${cookingIndex}.cookFile" onchange="previewFile(event)">
                            </div>
                            <div class="col-md-4 mb-3">
                                <i class="bi bi-trash3 deleteCookingBtn" data-index="${cookingIndex}"></i>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-5 cookMetrialTextPlace" id="cookMetrial-${cookingIndex}">
                        <h4>선택된 재료 목록</h4>
                    </div>
                    <div class="col-md-4 mb-3 preview-container">
                        <img class="preview" src="" alt="Image preview">
                    </div>
                </div>
            </div>
        `;
        cookingSection.append(newCooking);

        materials.forEach(material => {
            const newCookingMaterial = `
                <div class="row g-3 align-items-center my-2" id="material-true-${material.index}">
                    <div class="col-md-3">
                        <span>${material.materialName}</span>
                        <input type="hidden" id="" name="CookMaterialNames" value="${material.materialName}">
                    </div>
                    <div class="col-md-3">
                        <span>${material.mensuration}</span>
                        <input type="hidden" name="CookMensurations" value="${material.mensuration}">
                    </div>
                    <div class="col-md-3">
                        <span>${material.typeMaterial}</span>
                        <input type="hidden" name="CookTypeMaterials" value="${material.typeMaterial}">
                    </div>
                    <div class="col-md-3">
                        <div class="form-check form-switch">
                            <input class="form-check-input material-check" type="checkbox" role="switch" id="Meterialcheck-${material.index}" name="materials${material.index}.checked" checked>
                        </div>
                    </div>
                </div>
                <div class="cookMing"></div>
            `;

            $(`#cookMetrial-${cookingIndex}`).append(newCookingMaterial);
        });

        cookingIndex++;
        
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
            $(`#cooking-${cookingIndex} .cookMing`).append(newCookingMetrial);
        });
        
    }

    $(document).on("click", ".deleteCookingBtn", function() {
        const index = $(this).data('index');
        removeCooking(index);
    });

    function removeCooking(index) {
        $(`#cooking-${index}`).remove();
    }

    function previewFile(event) {
        const input = event.target;
        const preview = $(input).closest('.cooking-item').find('.preview');
        const file = input.files[0];
        const reader = new FileReader();

        reader.onload = function(e) {
            preview.attr('src', e.target.result).show();
        };

        if (file) {
            reader.readAsDataURL(file);
        } else {
            preview.hide();
        }
    }
    
   
    
    
    

    $(document).on("change", ".material-check", function() {
        const isChecked = $(this).prop('checked');
        const materialIndex = $(this).attr('id').split('-')[1]; // 체크박스의 ID에서 materialIndex 추출

        const hiddenInputs = $(`#material-true-${materialIndex} input[type=hidden]`);
        if (isChecked) {
            hiddenInputs.show(); // 체크됐을 때 보이기
        } else {
            hiddenInputs.hide(); // 체크 해제됐을 때 숨기기
        }
    });

});
