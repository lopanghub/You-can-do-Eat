$(function() {
	let cookingId = $("#cookingId").val();
	
	//레시피 보기버튼을 클릭했을때 .rList에 뿌려줌
	$("#listBtn").on("click", function() {
		$(".rList").empty();
		const bookContent = `
                    <div class="col-1"></div>
					<div class="col my-2">
						<div class="row my-2 bg-secondary">
							<div class="col-3"></div>
							<div class="col-6 ">
								<h3 class="text-center text-white my-3 fw-bold">레시피 모아보기</h3>
							</div>
							<div class="col-3 text-end text-white d-flex  justify-content-end align-items-center">
								<button type="button" class="btn btn-success " id="recipeBtn">레시피 접기</button>
							</div>
						</div>
						<div class="list">
						
						</div>
					</div>
					<div class="col-1"></div>
                    `;

		$(".rList").append(bookContent);
		loadRecipeList(cookingId);
		
	})
	//레시피 접기 버튼을 클릭했을때 .rList 을 없앰
	$(document).on("click", "#recipeBtn", function() {
		$(".rList").empty();
	})

	//cookingId 의 재료들 함수
	function loadRecipeMList(cookingId) {
		let boardNo = $("#boardNo").val();
		console.log("loadRecipeMList의 cookingId 입니다 증가하는지 확인 cookingId :" + cookingId)
		$.ajax({
			url: "/ajax/recipeMList",
			type: "GET",
			dataType: 'json',
			data: { boardNo: boardNo, cookingId: cookingId },
			success: function(recipe) {
				console.log("recipe : \n", recipe);

				 const materialsHTML = recipe.map(material => `
                <div class="col">
                    <div class="row">
                        <div class="col">
                            ${material.materialName ? `<span>${material.materialName}</span>` : ''}
                        </div>
                        <div class="col">
                            ${material.mensuration ? `<span>${material.mensuration}</span>` : ''}
                        </div>
                    </div>
                </div>
            `).join('') ;

            $(`.listM${cookingId}`).append(materialsHTML);
			},
			error: function(error) {
				console.error("Error fetching book details:", error);
				const materialsHTML=`현 조리과정에는 재료가 포함되어 있지 않습니다.`
				$(`.listM${cookingId}`).append(materialsHTML);
			}
		});
	}

	// boardNo의 레시피과정
	function loadRecipeList(cookingId) {
		let boardNo = $("#boardNo").val();
		
		$(".list").empty();
		$.ajax({
			url: "/ajax/recipeList",
			type: "GET",
			dataType: 'json',
			data: {
				boardNo: boardNo,
				cookingId: cookingId
			},
			success: function(recipeList) {
				recipeNo = 0;
				recipeList.forEach(recipe => {
					console.log("loadCookList 입니다. 증가하는 지 확인 cookingId:" + cookingId);
					recipeNo++;
					const bookContent = `
						<!-- 여기서 부터 반복문돌리기  -->
						<div class="row border border-danger my-2">
							<div class="col">
								<div class="row my-2 border border-dark" style="height: 300px;">
							<!-- 조리이미지 들어갈부분  -->
							<div class="col-5">
								<div class="row ">
									<div class="col mx-4">
										<img src="https://via.placeholder.com/300" alt="샘플이미지">
									</div>
								</div>
							</div>
							<div class="col">
								<div class="row my-2">
									<div class="col">
										<h4 class="fw-bold">${recipeNo}.${recipe.cookTitle}</h4>
									</div>
								</div>
								<div class="row my-1">
									<div class="col">
										<h4>조리방법</h4>
									</div>
								</div>
								<div class="row my-1">
									<div class="col my-2">
										<span>${recipe.cookMethod}</span>
									</div>
								</div>
								${recipe.recommended === null ? `` : `
								<div class="row my-1">
									<div class="col my-2">
										<span>주의할점</span>
									</div>
								</div>
								<div class="row my-1">
									<div class="col my-2">
										<span>${recipe.recommended}</span>
									</div>
								</div>
								`}
							</div>
						</div>
							<div class="row border mx-4">
							<div class="col ">
								<h4 class="fw-bold">요리재료</h4>
							</div>
						</div>
						<!--요리재료 반복문  -->
						<div class="row my-1  mx-4 listM${cookingId} border row-cols-2">
						</div>
                    `;

					// 새로운 콘텐츠를 타겟 div에 추가
					
					$(".list").append(bookContent);
					loadRecipeMList(cookingId);
					cookingId++
				});
					
			},
			error: function(error) {
				console.error("Error fetching book details:", error);
			}

		});

	}

});
