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
							<div class="recipe-collection text-center">
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
								<div class="row my-2">
									<div class="col">
										/*재료들어간부분*/
									</div>
								</div>
								/*요리과정들어간부분*/
							</div>
						</div>
                    `;

		$(".rList").append(bookContent);

	})


});
