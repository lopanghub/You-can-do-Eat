$(function() {
    let endPage = parseInt($("#bookCount").val()); // 전체 페이지 수
    let currentPage = 0;
     let cookingId = $("#cookingId").val();

    // 이전 페이지 버튼 클릭 시
    $(document).on("click", "#prevPageBtn", function() {
	
        if (currentPage == 0) {
            currentPage = endPage;
             loadBookDetail();
        } else  if (currentPage > 0){
            currentPage--;
            cookingId--;
            loadCookList(currentPage);
            loadCookMList(cookingId);
        }
    });

    // 다음 페이지 버튼 클릭 시
    $(document).on("click", "#nextPageBtn", function() {
        let cookingId = parseInt($("#cookingId").val());

        if (currentPage == endPage) {
			loadCookList(currentPage);
			loadCookMList();
            currentPage = 0;
        } else {
            currentPage++;
        }
    });

    // 일시 정지 버튼 클릭 시
    $(document).on("click", "#pauseBtn", function() {
        console.log("일시 정지 버튼 클릭");
    });

    // 책 보기 버튼 클릭 시
    $("#bookBtn").on("click", function() {
        $(".bg").empty(); // 기존 내용 초기화

        // 초기 페이지 구조
        const htmlContent = `
            <div class="col" style="background-image:url('/images/note11.png'); background-size:cover; height:100%; width: 100%;">
                <div class="row page" style="margin-top:80px;"></div>
            </div>
        `;
        $(".bg").append(htmlContent);
        
       loadBookDetail()
    });

    function loadBookDetail() {
        let boardNo = $("#boardNo").val();
        $(".page").empty();
        $.ajax({
            url: "/ajax/bookDetail",
            type: "GET",
            data: { boardNo: boardNo ,
            			},
            success: function(recipe) {
                console.log("Received recipe:", recipe);

                const bookContent = `
                    <!-- 왼쪽 페이지 -->
                    <div class="col border border-dark p-2 m-2">
                        <input type="hidden" id="cookingId" value="${recipe.cookingId}">
                        <div class="row">
                            <div class="col d-flex justify-content-center">
                                <img src="https://via.placeholder.com/400" alt="샘플이미지">
                            </div>
                        </div>
                        <div class="row my-2">
                            <div class="col text-center">
                                <h2>${recipe.boardTitle}</h2>
                            </div>
                        </div>
                        <div class="row border my-2">
                            <div class="col text-center">
                                <span>${recipe.boardContent}</span>
                            </div>
                        </div>
                        <div class="row my-2">
                            <div class="col text-center">
                                <i class="bi bi-people-fill" style="font-size:40px"></i>
                            </div>
                            <div class="col text-center">
                                <i class="bi bi-alarm" style="font-size:40px"></i>
                            </div>
                        </div>
                        <div class="row my-2">
                            <div class="col text-center">
                                <span>${recipe.numberEaters}</span>인분
                            </div>
                            <div class="col text-center">
                                <span>${recipe.foodTime}</span>분 이내
                            </div>
                        </div>
                    </div>
                    <!-- 오른쪽 페이지 -->
                    <div class="col border border-dark d-flex flex-column p-2 m-2">
                        <div class="row">
                            <div class="col">
                                <h3>${recipe.foodName}</h3>
                            </div>
                        </div>
                        <!-- 재료 목록 -->
                        <div class="row my-2 border border-danger justify-content-center" style="width:100%; height:200px; margin:0px;">
                            <div class="col border border-primary" style="width:100%; height:200px;">
                                ${recipe.materialList.map(material => `
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
                        <div class="row my-2">
                            <div class="col">
                                <h3>양념재료</h3>
                            </div>
                        </div>
                        <!-- 양념재료 목록 -->
                        <div class="row my-2 border border-danger justify-content-center" style="width:100%; height:200px; margin:0px;">
                            <div class="col border border-primary" style="width:100%; height:200px;">
                                ${recipe.materialList.map(material => `
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
                        <div class="row">
                            <div class="col text-center">
                                <div class="row">
                                    <div class="col d-flex justify-content-center align-items-center">
                                        <i class="bi bi-eye-fill" style="font-size:40px;"></i>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        (<span>${recipe.boardView}</span>)
                                    </div>
                                </div>
                            </div>
                            <div class="col text-center">
                                <div class="row">
                                    <div class="col d-flex justify-content-center align-items-center">
                                        <i class="bi bi-star-fill" style="font-size:40px;"></i>
                                        <i class="bi bi-star-fill" style="font-size:40px;"></i>
                                        <i class="bi bi-star-fill" style="font-size:40px;"></i>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        (<span>${recipe.apoint}</span>)
                                    </div>
                                </div>
                            </div>
                            <div class="col d-flex justify-content-center align-items-center">
                                <div class="row">
                                    <div class="col text-center">
                                        <i class="accordion-button collapsed bg-transparent bi bi-share-fill" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseOne" aria-expanded="false" aria-controls="flush-collapseOne" style="font-size:55px;"></i>
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
                        <div class="col">
                            <button id="pauseBtn" class="btn btn-dark">
                                <i class="bi bi-pause-fill" style="font-size:40px;"></i>
                            </button>
                        </div>
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
                                    <h1>${recipe.memberId}</h1>
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

                // 새로운 콘텐츠를 타겟 div에 추가
                $(".page").append(bookContent);
            },
            error: function(error) {
                console.error("Error fetching book details:", error);
            }
        });
    }
    
  function loadCookMList() {
	console.log("여기는 loadMList 입니다.");
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
            //console.log(bookContent);
            $(".cookM").append(bookContent);
            
        },
        error: function(error) {
            console.error("Error fetching book details:", error);
        }
    });
}

    
    function loadCookList(currentPage) {
        let boardNo = $("#boardNo").val();
        console.log("loadCookList"+currentPage);
        $(".page").empty();
        $.ajax({
            url: "/ajax/cookList",
            type: "GET",
            dataType: 'json',
            data: { boardNo: boardNo ,
            currentPage:currentPage},
            success: function(recipeList) {
                console.log("Received recipe list:", recipeList);

               
                    const bookContent = `
                        <!-- 왼쪽 페이지 -->
                        <div class="col border border-dark p-2 m-2">
                            <div class="row">
                                <div class="col d-flex justify-content-center">
                                    <img src="https://via.placeholder.com/400" alt="샘플이미지">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col">
                                    <div class="row my-2">
                                        <div class="col mx-4">
                                            <h3>요리재료</h3>
                                        </div>
                                    </div>
                                    <!-- 요리재료 반복문돌리기 -->
                                    <div class="row my-2 border border-danger justify-content-center" style="width:100%; height:100px; margin:0px;">
                                        <div class="col cookM border border-primary" style="width:100%; height:100px;">
                                           
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div><!-- 왼쪽페이지 마무리 -->

                        <!-- 오른쪽페이지 -->
                        <div class="col border border-dark d-flex flex-column p-2 m-2">
                            <div class="row">
                                <div class="col">
                                    <h1>${recipeList.cookTitle}</h1>
                                </div>
                            </div>
                            <div class="row my-2 border border-danger justify-content-center" style="width:100%; height:200px; margin:0px;">
                                <div class="col border border-primary" style="width:100%; height:200px;">
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
                        </div><!-- 오른페이지 -->
                          <!-- 방향 아이콘 -->
                        <div class="row text-center">
                            <div class="col">
                                <button id="prevPageBtn" class="btn btn-dark">
                                    <i class="bi bi-caret-left-fill" style="font-size:40px;"></i>
                                </button>
                            </div>
                            <div class="col">
                                <button id="pauseBtn" class="btn btn-dark">
                                    <i class="bi bi-pause-fill" style="font-size:40px;"></i>
                                </button>
                            </div>
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
                                        <h1>${recipeList.memberId}</h1>
                                    </div>
                                </div>
                            </div>
                            <div class="col-3">
                                <div id="flush-collapseOne" class="accordion-collapse collapse text-end" data-bs-parent="#accordionFlushExample">
                                    <div class="accordion-body">
                                        <i class="bi bi-instagram" style="font-size:50px;"></i>
                                        <i class="bi bi-facebook" style="font-size:50px;"></i>
                                        <i class="bi bi-twitter" style="font-size:50px;"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    `;

                    // 새로운 콘텐츠를 타겟 div에 추가
                    $(".page").append(bookContent);
                	
            },
            error: function(error) {
                console.error("Error fetching book details:", error);
            }
        });
    }

    // 예제 초기 로드
});
