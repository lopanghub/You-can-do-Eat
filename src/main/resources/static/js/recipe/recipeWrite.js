$(document).ready(function() {
    // 전역 변수로 materialIndex를 선언하고 초기화
    var materialIndex = $('#material .row.mt-2').length;
// 전역 변수로 cookingIndex를 선언하고 초기화
var cookingIndex = $('#cookingSections .cooking-item').length;
    // 페이지 로드 시점에 이미 존재하는 조리 과정 요소들의 인덱스를 확인하여 cookingIndex 설정
    $('#cooking0 .cookingSection').each(function() {
        var currentIndex = parseInt($(this).attr('id').replace('cooking', ''));
        if (currentIndex >= cookingIndex) {
            cookingIndex = currentIndex + 1;
        }
    });
    // 재료 추가
    $('#addMaterial').on('click', function() {
        var newMaterialHtml = `
            <div class="row mt-2 materialset" id="materialRow${materialIndex}">
                <div class="col">재료이름</div>
                <div class="col">
                    <input type="text" class="form-control" id="material${materialIndex}.materialName" name="materialNames">
                </div>
                <div class="col">재료 양</div>
                <div class="col">
                    <input type="text" class="form-control" id="material${materialIndex}.mensuration" name="mensurations">
                </div>
                <div class="col-3">
                    <select class="form-select" id="material${materialIndex}.typeMaterial" name="typeMaterials">
                        <option value="재료">재료</option>
                        <option value="조미료">조미료</option>
                    </select>
                </div>
                <div class="col">
                    <input type="button" class="btn btn-danger deleteMaterial" data-materialindex="${materialIndex}" value="재료삭제">
                </div>
            </div>
        `;
        $('#material').append(newMaterialHtml);
        materialIndex++; // materialIndex 값을 증가시킴
    });

    // 재료 삭제
    $(document).on('click', '.deleteMaterial', function() {
        var materialIndex = $(this).data('materialindex');
        $('#materialRow' + materialIndex).remove();
    });
	
   // 조리 과정 추가
    $('#addCooking').on('click', function() {
		console.log("클릭완료");
		
        var newCookingHtml = `
            <div class="row my-2 cooking-item cookingSet" id="cooking${cookingIndex}" data-cookingindex="${cookingIndex}">
                <div class="col my-2">
                    <div class="form-group row">
                        <label for="cookTitle" class="col-sm-3 col-form-label">요리제목</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="cookTitles" id="cookings${cookingIndex}.cookTitle">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="cookMethod" class="col-sm-3 col-form-label">요리설명</label>
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
                <div class="row">
                    <div class="col">
                        <input type="button" class="btn btn-danger deleteCooking" data-cookingindex="${cookingIndex}" value="조리과정삭제">
                    </div>
                </div>
            </div>
        `;
        console.log("newCookingHtml :" +newCookingHtml);
        $('#cookingSection').append(newCookingHtml);
        cookingIndex++; // cookingIndex 값을 증가시킴
    });

    // 조리 과정 삭제
    $(document).on('click', '.deleteCooking', function() {
        var cookingIndex = $(this).data('cookingindex');
        $('#cooking' + cookingIndex).remove();
    });

    // 이미지 미리보기
    window.previewFile = function(event) {
        var input = event.target;
        var reader = new FileReader();
        reader.onload = function() {
            var preview = $(input).closest('.cookingSet').find('.preview');
            preview.attr('src', reader.result);
            preview.show();
        };
        reader.readAsDataURL(input.files[0]);
    };
     
});
