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
                <div class="col">
					<label for="material${materialIndex}.materialName">재료이름</label>
                    <input type="text" class="form-control" id="material${materialIndex}.materialName" name="materialNames">
                </div>
                <div class="col">
					<label for="material${materialIndex}.mensuration">재료 양</label>
                    <input type="text" class="form-control" id="material${materialIndex}.mensuration" name="mensurations">
                </div>
                <div class="col-3">
				<label for="material${materialIndex}.typeMaterial">구분</label>
                    <select class="form-select" id="material${materialIndex}.typeMaterial" name="typeMaterials">
                        <option value="재료">재료</option>
                        <option value="조미료">조미료</option>
                    </select>
                </div>
                <div class="col-auto d-flex align-items-end">
                   <button type="button" class="deleteMaterial" data-materialindex="${materialIndex}">X</button>
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
				<div class="col-md-9">
				    <div class="form-group row mb-3">
                        <label for="cookTitle" class="col-sm-3 col-form-label">요리제목</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="cookTitles" id="cookings${cookingIndex}.cookTitle">
                        </div>
                    </div>
                    <div class="form-group row mb-3">
                        <label for="cookMethod" class="col-sm-3 col-form-label">요리과정</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="cookMethods" id="cookings${cookingIndex}.cookMethod">
                        </div>
					<div class="form-group row mb-3">
					     <label for="cookFile" class="col-sm-3 col-form-label">조리사진</label>
				             <div class="col-sm-9">
				                 <input type="file" class="form-control" name="cookFiles" id="cookings${cookingIndex}.cookFile" onchange="previewFile(event, ${cookingIndex})">
				              </div>
				    </div>
					<div class="form-group row mb-3">
                        <label for="recommended" class="col-sm-3 col-form-label">주의사항</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="recommendeds" id="cookings${cookingIndex}.recommended">
                        </div>
                    </div>
					<div class="row">
					   <div class="col">
					      <input type="button" class="deleteCooking" data-cookingindex="${cookingIndex}" value="조리과정삭제">
					   </div>
					</div>                 
                </div>
                <div class="col-md-3">
                    <div class="preview-container mb-3">
                        <img class="preview" id="preview${cookingIndex}" src="" alt="Image preview" style="max-width: 100%; height: auto; display: none;">
                    </div>
                </div>
			</div>
               
        `;
        $('#cookingSections').append(newCookingHtml);
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
	
	$('#thumbnailname').change(function() {
		      var input = this;
		      if (input.files && input.files[0]) {
		          var reader = new FileReader();

		          reader.onload = function(e) {
		              $('#thumbnailPreview')
		                  .attr('src', e.target.result)
		                  .show();
		          };

		          reader.readAsDataURL(input.files[0]);
		      }
		  });
	
});
