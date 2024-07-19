$(function() {


		
$(document).on("click", "#scrollButton", function() {
    document.getElementById("sectionComment").scrollIntoView({ behavior: 'smooth' });
});

$(document).on("click", "#commentBtn", function() {
    window.scrollTo({ top: 0, behavior: 'smooth' });
});


	$(document).on("click","#updateDetailBtn",function() {

		$("#recipeDetailForm").attr("action", "updateRecipeForm");
		$("#recipeDetailForm").attr("method", "post");
		$("#recipeDetailForm").submit();
	});
	$(document).on("click","#deleteDetailBtn", function() {
		if (confirm('정말로 이 댓글을 삭제하시겠습니까?')) {


			$("#recipeDetailForm").attr("action", "deleteRecipe");
			$("#recipeDetailForm").attr("method", "post");
			$("#recipeDetailForm").submit();
		}
	});


	let cookingId = $("#cookingId").val();
	//레시피 보기버튼을 클릭했을때 .rList에 뿌려줌
	$(document).on("click","#recipeBtn", function() {
		$(".rList").empty();
		let searchOption = $("#searchOption").val();
		let type = $("#type").val();
		let keyword = $("#keyword").val();
		console.log("searchOption" + searchOption)
		let sessionId = $("#loginId").val();
		console.log("sessionId : " + sessionId);
		if (sessionId !== undefined && sessionId !== '') {
			isSessionId = `
            <div class="col-2">
                <input class="detail-edit-button btn-sm" type="button" id="updateDetailBtn" value="수정하기" />
            </div>
            <div class="col-2">
                <input class="detail-delete-button btn-sm" type="button" id="deleteDetailBtn" value="삭제하기" />
            </div>`;
			console.log(isSessionId);
		} else {
			isSessionId = `
            `;
			console.log(isSessionId);
		}
		let pageNum = $("#pageNum").val();
		const bookContent = `
                    <div class="col">
							<div class="recipe-collection">
								<div class="col-12">
									<div class="row">
										<div class="col recipe-collection-title">
											<div class="row d-flex  align-items-center">

												<div class="col-4"></div>
												<div class="col-4">
													<h3 class="text-center my-3 fw-bold ">레시피 모아보기</h3>
												</div>
												${isSessionId}
											</div>
										</div>
									</div>
									<div class="row">
									<div class="col-2 text-end">
													<input class="detail-review-button" type="button" id="scrollButton"
														value="리뷰보기">
												</div>
												<div class="col-2 text-start">
													 ${searchOption ? `
				                                            <input class="detail-review-button" type="button" value="목록보기"
				                                                onclick="location.href='recipeList?pageNum=${pageNum}&type=${type}&keyword=${keyword}'" />
				                                        ` : `
				                                            <input class="detail-review-button" type="button" value="목록보기"
				                                                onclick="location.href='recipeList?pageNum=${pageNum}'" />
				                                        `}
												</div>
										<div
											class="col text-end text-white d-flex justify-content-end align-items-center">
											<button type="button" class="book-change-btn" id="bookBtn">책
												열기</button>
										</div>
									</div>
								</div>
								<div id="recipeListDetail"></div>
							</div>
						</div>
                    `;
		
		$(".rList").append(bookContent);
		recipeListDetail()
	})
	
// 책의 상세보기 함수
	function recipeListDetail() {
		let boardNo = $("#boardNo").val();
		$(".page").empty();
		$.ajax({
			url: "/ajax/recipeList",
			type: "GET",
			data: { boardNo: boardNo,
			cookingId:cookingId },
			success: function(recipe) {
				console.log("Received recipe:", recipe);
				const { materials } = recipe;
				const ingredients = materials.filter(material => material.typeMaterial === '재료');
				const seasonings = materials.filter(material => material.typeMaterial === '조미료');
				if (!recipe.recipe.thumbnail) {
					recipe.recipe.thumbnail = "https://via.placeholder.com/300.jpg";
				} else {
					recipe.thumbnail = "./uploads/" + recipe.recipe.thumbnail;
				}

				 let recipeContent = `
                <div class="recipeDetailForm border border-dark rounded my-4">
                    <div class="row my-3 text-center">
                        <div class="col">
                            <h2>${recipe.recipe.boardTitle}</h2>
                        </div>
                    </div>

                    <div class="row my-2">
                        <div class="col d-flex justify-content-center text-center">
                            <img src="${recipe.recipe.thumbnail}" alt="${recipe.recipe.boardTitle}" style="height: 300px; width: 300px;" class="img-fluid d-block" onerror="this.src='http://via.placeholder.com/300';">
                        </div>
                    </div>
                    <div class="row my-4 mx-3 border  rounded">
                        <div class="col  ">
                            <h5>${recipe.recipe.boardContent}</h5>
                        </div>
                    </div>
                    <div class="row my-2 recipe-info" style="font-size: 1.2rem; margin-top: 20px;">
                        <div class="col text-center">
                            <i class="bi bi-people-fill" style="font-size: 1.5rem;"></i>
                            <span style="margin-left: 5px;">${recipe.recipe.numberEaters}인분</span>
                        </div>
                        <div class="col text-center">
                            <i class="bi bi-alarm" style="font-size: 1.5rem;"></i>
                            <span style="margin-left: 5px;">${recipe.recipe.foodTime}분 이내</span>
                        </div>
                    </div>
                </div>
                <div class="row my-2">
                    <div class="col">
                        <div class="row text-center">
                            <div class="col-6 border border-dark rounded justify-content-start">
                                <div class="row">
                                    <h3>재료</h3>
                                </div>
                                <div class="row">
                                    ${ingredients.map(ingredient => `
                                        <div class="row my-1">
                                            <div class="col">${ingredient.materialName}</div>
                                            <div class="col">${ingredient.mensuration}</div>
                                        </div>
                                    `).join('')}
                                </div>
                            </div>
                            <div class="col-6 border border-dark rounded justify-content-start">
                                <div class="row">
                                    <h3>조미료</h3>
                                </div>
                                <div class="row">
                                    ${seasonings.map(seasoning => `
                                        <div class="row my-1">
                                            <div class="col">${seasoning.materialName}</div>
                                            <div class="col">${seasoning.mensuration}</div>
                                        </div>
                                    `).join('')}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                ${recipe.cookings.map((cListItem, index) => `
                    <div class="row my-2">
                        <div class="col">
                            <div class="row my-2 border border-dark rounded" style="height: 300px;">
                                <div class="col-5">
                                    <div class="row">
                                        <div class="col mx-4 text-center">
                                            ${cListItem.cookFile ? `
                                                <img src="./uploads/cooking/${cListItem.cookFile}" alt="샘플이미지" class="img-fluid" style="height: 300px; width: 300px;">
                                            ` : `
                                                <img src="https://via.placeholder.com/300" alt="샘플이미지" class="img-fluid">
                                            `}
                                        </div>
                                    </div>
                                </div>
                                <div class="col mx-3">
                                    <div class="row my-2">
                                        <div class="col">
                                            <h4 class="fw-bold">Step ${index + 1}</h4>
                                        </div>
                                    </div>
                                    <div class="row my-1">
                                        <div class="col">
                                            <h5>조리방법</h5>
                                        </div>
                                    </div>
                                    <div class="row my-1">
                                        <div class="col my-2 mx-3">
                                            <span>${cListItem.cookMethod}</span>
                                        </div>
                                    </div>
                                    ${cListItem.recommended ? `
                                        <div class="row my-1">
                                            <div class="col my-2">
                                                <h5>주의할점</h5>
                                            </div>
                                        </div>
                                        <div class="row my-1">
                                            <div class="col my-2 mx-3">
                                                <span>${cListItem.recommended}</span>
                                            </div>
                                        </div>
                                    ` : ''}
                                </div>
                            </div>
                        </div>
                    </div>
                `).join('')}
            `;
				$("#recipeListDetail").append(recipeContent);
				// updatePlayPauseButton(isPlay);
			},
			error: function(error) {
				console.error("Error fetching book details:", error);
			}
		});
	}



});
