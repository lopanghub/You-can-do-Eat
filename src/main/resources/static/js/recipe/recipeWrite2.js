$(document).ready(function() {
    let cookingIndex = 0;
    let materialIndex = 1;
	let addIndex=1;
    // 요리 섹션 추가
    $("#addCooking").on("click",  function() {
		
		let cookingHtml = `
		<div class="row my-2 cooking-item cookingSet" id="cooking${cookingIndex}" data-cookingindex="${cookingIndex}">
		                <div class="col-md-9">
		                    <div class="form-group row mb-3">
		                        <div class="col-sm-9">
		                        	<span  class="fs-1 fw-bold">Step ${addIndex}</div>
		                        </div>
		                    </div>
		                    <div class="form-group row mb-3">
		                        <label for="cookMethod" class="col-sm-3 col-form-label">요리과정</label>
		                        <div class="col-sm-9">
		                            <textarea class="form-control" name="cookMethods" id="cookings${cookingIndex}.cookMethod" rows="3"></textarea>
		                        </div>
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
        $("#cookingSection").append(cookingHtml); // 요리 섹션을 마지막으로 추가
        cookingIndex++; // cookingIndex 증가
        addIndex++
    });

    // 재료 추가
    $("#addMaterial").on("click",  function() {
        let materialHtml = `
		<div class="row mt-2" id="materialRow${materialIndex}">
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
        $("#material").append(materialHtml); // 해당 요리 섹션에 재료 추가
        materialIndex++; 
    });

     // 조리 섹션 삭제
    $(document).on("click", ".deleteCooking", function() {
        let cookingIndexToRemove = $(this).data("cookingindex");
        $(`#cooking${cookingIndexToRemove}`).remove(); // 요리 섹션 삭제

        // 재정렬
        $(".cooking-item").each(function(index) {
            $(this).attr("id", `cooking${index}`);
            $(this).attr("data-cookingindex", index);
            $(this).find("textarea").attr("id", `cookings${index}.cookMethod`);
            $(this).find("input[type='file']").attr("id", `cookings${index}.cookFile`);
            $(this).find("input[name='recommendeds']").attr("id", `cookings${index}.recommended`);
            $(this).find(".deleteCooking").attr("data-cookingindex", index);
            $(this).find(".fs-1.fw-bold").text(`Step ${index + 1}`);
        });

        addIndex--; // 스텝 번호 감소
        cookingIndex--; // 인덱스 감소
    });

   $(document).on("click", ".deleteMaterial", function() {
      var materialIndex = $(this).data('materialindex');
        $('#materialRow' + materialIndex).remove();
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
