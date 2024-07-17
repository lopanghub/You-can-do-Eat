$(function() {
	const totalPages = parseInt($("#bookCount").val()); // 전체 페이지 수
	const mainPage = 0; // 메인 소개 페이지
	let currentPage = mainPage;
	let cookingId = parseInt($("#cookingId").val()); // 요리 ID
	/*   let isPlay = false; // 플레이버튼*/
	let id = $("#id").val();
	/*  var intervalId;*/
	

	// 이전 페이지 버튼 클릭 시
	$(document).on("click", "#prevPageBtn", function() {
		cookingId--;
		currentPage--;
		if (currentPage < mainPage) {
			currentPage = totalPages;
			cookingId = parseInt($("#cookingId").val()) + totalPages - 1;
		}

		if (currentPage === mainPage) {
			loadBookDetail();
		} else {
			loadCookList(currentPage - 1, cookingId);
		}
	});

	// 다음 페이지 버튼 클릭 시
	$(document).on("click", "#nextPageBtn", rightPage);

	// 오른페이지 가는 함수
	function rightPage() {
		cookingId++;
		currentPage++;

		if (currentPage > totalPages) {
			currentPage = mainPage;
			cookingId = parseInt($("#cookingId").val());
		}

		if (currentPage === mainPage) {
			loadBookDetail();
		} else {
			loadCookList(currentPage - 1, cookingId - 1);
		}
	}

	// 책 보기 버튼 클릭 시
	$(document).on("click","#bookBtn", function() {
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

			const htmlContent = `
			<div class="col">
							<div class="recipe-collection text-center">
								<div class="col-12">
									<div class="row">
										<div class="col recipe-collection-title">
											<div class="row d-flex  align-items-center">

												<div class="col-4"></div>
												<div class="col-4">
													<h3 class="text-center my-3 fw-bold ">레시피 책 보기</h3>
												</div>
												${isSessionId}
												</div>
											</div>
										</div>
												<div class="row">
												<div class="col-2 text-start">
													<input class="detail-edit-button" type="button" id="scrollButton"
														value="리뷰보기">
												</div>
												<div class="col-2">
													 ${searchOption ? `
				                                            <input class="detail-edit-button" type="button" value="목록보기"
				                                                onclick="location.href='recipeList?pageNum=${pageNum}&type=${type}&keyword=${keyword}'" />
				                                        ` : `
				                                            <input class="detail-edit-button" type="button" value="목록보기"
				                                                onclick="location.href='recipeList?pageNum=${pageNum}'" />
				                                        `}
												</div>
													<div class="col text-end text-white d-flex justify-content-end align-items-center">
														<button type="button" class="book-change-btn" id="recipeBtn">레시피열기
														</button>
													</div>
												</div>
												<div class="row my-4">					
												<div class="col" style="background-image:url('/images/note11.png'); background-size: 100% 100%; background-repeat: no-repeat; background-position: center; height:100%; width: 100%; padding: 20px;">
								               <div class="row page" style="margin-top:80px; height: calc(100% - 100px); overflow-y: auto;"></div>
								           </div>
							           </div>
						           </div>
					           </div>
				           </div>
			           </div>
			           
			       `;
			$(".rList").append(htmlContent);


			loadBookDetail();
	});


	// 책의 상세보기 함수
	function loadBookDetail() {
		let boardNo = $("#boardNo").val();
		$(".page").empty();
		$.ajax({
			url: "/ajax/bookDetail",
			type: "GET",
			data: { boardNo: boardNo },
			success: function(recipe) {
				console.log("Received recipe:", recipe);
				const { materials } = recipe;
				const ingredients = materials.filter(material => material.typeMaterial === '재료');
				const seasonings = materials.filter(material => material.typeMaterial === '조미료');
				if (!recipe.thumbnail) {
					recipe.thumbnail = "https://via.placeholder.com/300.jpg";
				} else {
					recipe.thumbnail = "./uploads/" + recipe.thumbnail;
				}

				const bookContent = `
				<!-- 왼쪽 페이지 -->
				               <div class="col recipe-page-border p-2 m-2">
				                   <div class="row">
				                       <div class="col d-flex justify-content-center">
				                           <img src="${recipe.thumbnail}" style="height:300px; width:300px; object-fit: cover;">
				                       </div>
				                   </div>
				                   <div class="row my-2">
				                       <div class="col text-center">
				                           <h2 class="recipe-title">${recipe.boardTitle}</h2>
				                       </div>
				                   </div>
				                   <div class="row border my-2 recipe-description">
				                       <div class="col text-center">
				                           <p>${recipe.boardContent}</p>
				                       </div>
				                   </div>
								   <div class="row my-2 recipe-info" style="font-size: 1.2rem; margin-top: 20px;"> <!-- 폰트 크기 증가 및 상단 여백 추가 -->
								                  <div class="col text-center">
								                      <i class="bi bi-people-fill" style="font-size: 1.5rem;"></i> <!-- 아이콘 크기 증가 -->
								                      <span style="margin-left: 5px;">${recipe.numberEaters}인분</span> <!-- 텍스트와 아이콘 사이 간격 추가 -->
								                  </div>
								                  <div class="col text-center">
								                      <i class="bi bi-alarm" style="font-size: 1.5rem;"></i> <!-- 아이콘 크기 증가 -->
								                      <span style="margin-left: 5px;">${recipe.foodTime}분 이내</span> <!-- 텍스트와 아이콘 사이 간격 추가 -->
								                  </div>
								              </div>
								          </div>
							   <!-- 오른쪽 페이지 -->
							           <div class="col recipe-page-border d-flex flex-column p-2 m-2">
							              
									   <div class="row justify-content-center">
									       <div class="col-auto"> <!-- col-auto를 사용하여 내용물 크기에 맞게 조절 -->
									           <div class="author-info">
									               <div class="author-image">
									                   <img src="https://via.placeholder.com/100" alt="Author" class="rounded-circle">
									               </div>
									               <div class="author-name">
									                   <h3>${id}</h3>
									               </div>
									           </div>
									       </div>
									   </div>
				                   <div class="row recipe-stats mb-3">
				                       <div class="col text-center">
				                           <i class="bi bi-eye-fill"></i>
				                           <span>(${recipe.boardView})</span>
				                       </div>
				                       <div class="col text-center">
				                           <i class="bi bi-star-fill"></i>
										   <i class="bi bi-star-fill"></i>
										   <i class="bi bi-star-fill"></i>
										   <i class="bi bi-star-fill"></i>
										   <i class="bi bi-star-fill"></i>
				                           <span>(${recipe.apoint})</span>
				                       </div>

									   
				                       <div class="col text-center">
				                           <i class="bi bi-share-fill" data-bs-toggle="collapse" data-bs-target="#shareOptions" aria-expanded="false" aria-controls="shareOptions"></i>
				                       </div>
				                   </div>
				                   <div class="collapse mt-3" id="shareOptions">
				                       <div class="d-flex justify-content-center">
				                           <button class="btn btn-outline-primary mx-1"><i class="bi bi-instagram"></i></button>
				                           <button class="btn btn-outline-primary mx-1"><i class="bi bi-facebook"></i></button>
				                           <button class="btn btn-outline-primary mx-1"><i class="bi bi-twitter"></i></button>
				                       </div>
				                   </div>
				               </div>
							   <!-- 네비게이션 버튼 -->
							       <div class="row text-center navigation-buttons mt-3">
							           <div class="col">
							               <button id="prevPageBtn" class="btn btn-recipe-nav">
							                   <i class="bi bi-caret-left-fill"></i>
							               </button>
							           </div>
							           <div class="col play"></div>
							           <div class="col">
							               <button id="nextPageBtn" class="btn btn-recipe-nav">
							                   <i class="bi bi-caret-right-fill"></i>
							               </button>
							           </div>
							       </div>
							   <!-- 재료 및 양념 -->
							   <div class="row mt-3 mx-0 ingredients-container px-0">
							          <div class="ingredient-list">
							              <h4>요리재료</h4>
							              <div class="ingredients-list">
							                  ${ingredients.map(material => `
							                      <div class="ingredient-item">
							                          <span>${material.materialName}</span>
							                          <span>${material.mensuration}</span>
							                      </div>
							                  `).join('')}
							              </div>
							          </div>
							          <div class="seasoning-list">
							              <h4>양념재료</h4>
							              <div class="seasonings-list">
							                  ${seasonings.map(material => `
							                      <div class="seasoning-item">
							                          <span>${material.materialName}</span>
							                          <span>${material.mensuration}</span>
							                      </div>
							                  `).join('')}
							              </div>
							          </div>
							      </div>
				            `;
				$(".page").append(bookContent);
				// updatePlayPauseButton(isPlay);
			},
			error: function(error) {
				console.error("Error fetching book details:", error);
			}
		});
	}


	function loadCookMList(cookingId) {
		let boardNo = $("#boardNo").val();

		$(".cookM").empty();
		$.ajax({
			url: "/ajax/cookMList",
			type: "GET",
			dataType: 'json',
			data: { boardNo: boardNo, cookingId: cookingId },
			success: function(recipe) {
				console.log("recipe : \n", recipe);
				const bookContent = recipe.map(material => `
                    <div class="row my-1" style="width:550px; height:30px; font-size:20px; margin-left: 25px;">
                        <div class="col text-start">
                            <span>${material.materialName}</span>
                        </div>
                        <div class="col text-end">
                            <span>${material.mensuration}</span>
                        </div>
                    </div>
                `).join('');
				$(".cookM").append(bookContent);
			},
			error: function(error) {
				console.error("Error fetching book details:", error);
			}
		});
	}


	/* 두번째페이지 */
	function loadCookList(currentPage, cookingId) {
		let boardNo = $("#boardNo").val();
		$(".page").empty();
		$.ajax({
			url: "/ajax/cookList",
			type: "GET",
			dataType: 'json',
			data: {
				boardNo: boardNo,
				currentPage: currentPage,
				cookingId: cookingId
			},
			success: function(recipeList) {
				console.log("Received recipe list:", recipeList);
				cook = recipeList;
				if (!recipeList.cookFile) {
					recipeList.cookFile = "https://via.placeholder.com/300";
				} else {
					recipeList.cookFile = "./uploads/cooking/" + recipeList.cookFile;
				}
				const bookContent = `
				<!-- 왼쪽 페이지 -->
				               <div class="col recipe-page-border p-2 m-2">
				                   <div class="row">
				                       <div class="col d-flex justify-content-center">
				                           <img src="${recipeList.cookFile}" alt="샘플이미지" style="height:300px; width:300px">
				                       </div>
				                   </div>
				                   <div class="row my-2">
				                       <div class="col">
				                           <h4>주의할 점</h4>
				                           <div class="cautions-list">
				                               <div class="caution-item">
				                                   <span>${recipeList.recommended}</span>
				                               </div>
				                           </div>
				                       </div>
				                   </div>
				               </div>

				               <!-- 오른쪽 페이지 -->
				               <div class="col recipe-page-border d-flex flex-column p-2 m-2">
				                   <div class="row">
				                       <div class="col">
				                           <h1>${currentPage + 1}.${recipeList.cookTitle}</h1>
				                       </div>
				                   </div>
				                   <div class="row my-2">
				                       <div class="col">
				                           <h4>조리방법</h4>
				                           <div class="cooking-list">
				                               <div class="cooking-item">
				                                   <span>${recipeList.cookMethod}</span>
				                               </div>
				                           </div>
				                       </div>
				                   </div>
				               </div>
				              
							   <!-- 네비게이션 버튼 및 프로필 -->
							              <div class="row text-center navigation-buttons mt-3 align-items-center">
							                  <div class="col-3">
							                      <button id="prevPageBtn" class="btn btn-recipe-nav">
							                          <i class="bi bi-caret-left-fill"></i>
							                      </button>
							                  </div>
							                  <div class="col-6">
							                      <div class="recipe-author d-flex align-items-center justify-content-center">
							                          <img src="https://via.placeholder.com/50" alt="Author" class="rounded-circle me-2" style="width: 50px; height: 50px;">
							                          <h5 class="mb-0">${id}</h5>
							                      </div>
							                  </div>
							                  <div class="col-3">
							                      <button id="nextPageBtn" class="btn btn-recipe-nav">
							                          <i class="bi bi-caret-right-fill"></i>
							                      </button>
							                  </div>
							              </div>

							              <!-- SNS 아이콘 -->
							              <div class="row mt-2">
							                  <div class="col text-center">
							                      <div id="flush-collapseOne" class="accordion-collapse collapse" data-bs-parent="#accordionFlushExample">
							                          <div class="accordion-body">
							                              <i class="bi bi-instagram mx-2" style="font-size:24px"></i>
							                              <i class="bi bi-facebook mx-2" style="font-size:24px"></i>
							                              <i class="bi bi-twitter mx-2" style="font-size:24px"></i>
							                          </div>
							                      </div>
							                  </div>
							              </div>
                `;

				$(".page").append(bookContent);
				loadCookMList(cookingId);
				// updatePlayPauseButton(isPlay);
				console.log("cookingIndex : " + cookingIndex)
			},
			error: function(error) {
				console.error("Error fetching book details:", error);
			}
		});
	}
});
