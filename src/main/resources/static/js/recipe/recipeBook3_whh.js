$(function() {
    const totalPages = parseInt($("#bookCount").val()); // 전체 페이지 수
    const mainPage = 0; // 메인 소개 페이지
    let currentPage = mainPage;
    let cookingId = parseInt($("#cookingId").val()); // 요리 ID
 /*   let isPlay = false; // 플레이버튼*/
    let isBook = false; // 레시피보기(북) 버튼
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
        console.log("바로 앞의 cookingId: " + cookingId);

        if (currentPage > totalPages) {
            currentPage = mainPage;
            cookingId = parseInt($("#cookingId").val());
            console.log("cookingId를 초기화 했습니다 cookingId: " + cookingId);
        }

        if (currentPage === mainPage) {
            loadBookDetail();
        } else {
            loadCookList(currentPage - 1, cookingId - 1);
            console.log("else 안의 cookingId: " + cookingId);
        }
    }

    // 책 보기 버튼 클릭 시
    $("#bookBtn").on("click", function() {
        $(".rList").empty();
        if (isBook) {
            isBook = false;
        } else {
            const htmlContent = `
			<div class="col" style="background-image:url('/images/note11.png'); background-size:cover; height:100%; width: 100%;">
			                  <div class="row page" style="margin-top:80px;"></div>
			              </div>
			       `;
			       $(".rList").append(htmlContent);

			       
            loadBookDetail();
            isBook = true;
        }
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
				<div class="col border border-dark p-2 m-2">
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
				         <div class="row my-2 recipe-info">
				             <div class="col text-center">
				                 <i class="bi bi-people-fill"></i>
				                 <span>${recipe.numberEaters}인분</span>
				             </div>
				             <div class="col text-center">
				                 <i class="bi bi-alarm"></i>
				                 <span>${recipe.foodTime}분 이내</span>
				             </div>
				         </div>
				         <div class="row my-2 recipe-stats">
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
				     </div>
				     <!-- 오른쪽 페이지 -->
				     <div class="col border border-dark d-flex flex-column p-2 m-2">
				         <div class="row">
				             <div class="col">
				                 <h4>요리재료</h4>
				                 <div class="row my-2 border border-danger justify-content-center" style="width:100%; height:200px; margin:0px;">
				                     <div class="col border border-primary" style="width:100%; height:200px;">
				                         ${ingredients.map(material => `
				                             <div class="row my-1" style="width:550px; height:30px; font-size:20px; margin-left: 25px;">
				                                 <div class="col text-start">
				                                     <span>${material.materialName}</span>
				                                 </div>
				                                 <div class="col text-end">
				                                     <span>${material.mensuration}</span>
				                                 </div>
				                             </div>
				                         `).join('')}
				                     </div>
				                 </div>
				             </div>
				         </div>
				         <div class="row">
				             <div class="col">
				                 <h4>양념재료</h4>
				                 <div class="row my-2 border border-danger justify-content-center" style="width:100%; height:200px; margin:0px;">
				                     <div class="col border border-primary" style="width:100%; height:200px;">
				                         ${seasonings.map(material => `
				                             <div class="row my-1" style="width:550px; height:30px; font-size:20px; margin-left: 25px;">
				                                 <div class="col text-start">
				                                     <span>${material.materialName}</span>
				                                 </div>
				                                 <div class="col text-end">
				                                     <span>${material.mensuration}</span>
				                                 </div>
				                             </div>
				                         `).join('')}
				                     </div>
				                 </div>
				             </div>
				         </div>
				     </div>
				     <!-- 네비게이션 버튼 -->
				     <div class="row text-center navigation-buttons">
				         <div class="col">
				             <button id="prevPageBtn" class="btn btn-outline-dark">
				                 <i class="bi bi-caret-left-fill"></i>
				             </button>
				         </div>
				         <div class="col play"></div>
				         <div class="col">
				             <button id="nextPageBtn" class="btn btn-outline-dark">
				                 <i class="bi bi-caret-right-fill"></i>
				             </button>
				         </div>
				     </div>
					 <!-- 작성자 정보 -->
					         <div class="row justify-content-center">
					             <div class="col-md-6 author-info mt-3">
					                 <div class="row align-items-center">
					                     <div class="col-4">
					                         <img src="https://via.placeholder.com/100" alt="Author" class="rounded-circle img-fluid">
					                     </div>
					                     <div class="col-8">
					                         <h3>${id}</h3>
					                     </div>
					                 </div>
					             </div>
					         </div>
				     <!-- 공유 옵션 -->
				     <div class="collapse mt-3" id="shareOptions">
				         <div class="d-flex justify-content-center">
				             <button class="btn btn-outline-primary mx-1"><i class="bi bi-instagram"></i></button>
				             <button class="btn btn-outline-primary mx-1"><i class="bi bi-facebook"></i></button>
				             <button class="btn btn-outline-primary mx-1"><i class="bi bi-twitter"></i></button>
				         </div>
				     </div>
				            `;
	            $(".page").append(bookContent);
	            updatePlayPauseButton(isPlay);
	        },
	        error: function(error) {
	            console.error("Error fetching book details:", error);
	        }
	    });
	}

    function loadCookMList(cookingId) {
        console.log("여기는 loadMList 입니다. cookingId의 값은 " + cookingId + "입니다.");
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

    function loadCookList(currentPage, cookingId) {
        let boardNo = $("#boardNo").val();
        console.log("loadCookList 입니다. cookingId:" + cookingId);

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
                    <div class="col border border-dark p-2 m-2">
                        <div class="row">
                            <div class="col d-flex justify-content-center">
                                <img src="${recipeList.cookFile}" alt="샘플이미지" style="height:400px; width:400px">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col">
                                <div class="row my-2">
                                    <div class="col mx-4">
                                        <h3>요리재료</h3>
                                    </div>
                                </div>
                                <div class="row my-2 border border-danger justify-content-center" style="width:100%; height:150px; margin:0px;">
                                    <div class="col cookM border border-primary" style="width:100%; height: 100%;">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- 오른쪽 페이지 -->
                    <div class="col border border-dark d-flex flex-column p-2 m-2">
                        <div class="row">
                            <div class="col">
                                <h1>${recipeList.cookTitle}</h1>
                            </div>
                        </div>
                        <div class="row my-2 border border-danger justify-content-center" style="width:100%; height:300px; margin:0px;">
                            <div class="col border border-primary" style="width:100%; height:100%">
                                <div class="row">
                                    <div class="col">
                                        <h3>조리방법</h3>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <span>${recipeList.cookMethod}</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row my-2 border border-danger justify-content-center" style="width:100%; height:200px; margin:0px;">
                            <div class="col border border-primary" style="width:100%; height:200px;">
                                <div class="row">
                                    <div class="col">
                                        <h3>주의사항!</h3>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <span>${recipeList.recommended}</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- 방향 아이콘 -->
                    <div class="row text-center">
                        <div class="col">
                            <button id="prevPageBtn" class="btn btn-dark">
                                <i class="bi bi-caret-left-fill" style="font-size:40px;"></i>
                            </button>
                        </div>
                        <div class="col play"></div>
                        <div class="col">
                            <button id="nextPageBtn" class="btn btn-dark">
                                <i class="bi bi-caret-right-fill" style="font-size:40px;"></i>
                            </button>
                        </div>
                    </div>
                    <!-- 프로필 / SNS 위치 -->
                    <div class="row my-2" style="height: 80px;">
                        <div class="col-3"></div>
                        <div class="col-6 border">
                            <div class="row d-flex align-items-center">
                                <div class="col-3">
                                    <img src="https://via.placeholder.com/100" alt="샘플이미지">
                                </div>
                                <div class="col">
                                    <h1>${id}</h1>
                                </div>
                            </div>
                        </div>
                        <div class="col-3">
                            <div id="flush-collapseOne" class="accordion-collapse collapse text-end" data-bs-parent="#accordionFlushExample">
                                <div class="accordion-body">
                                    <i class="bi bi-instagram" style="font-size:50px"></i>
                                    <i class="bi bi-facebook" style="font-size:50px"></i>
                                    <i class="bi bi-twitter" style="font-size:50px"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                `;

                $(".page").append(bookContent);
                loadCookMList(cookingId);
                updatePlayPauseButton(isPlay);
            },
            error: function(error) {
                console.error("Error fetching book details:", error);
            }
        });
    }
});
