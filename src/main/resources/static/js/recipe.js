$(function() {
    const boardNo = $("#boardNo").val();
    const FristId = $("#FristId").val();
    let currentPage = 0;
    let endPage = $("#bookCount").val();

    $("#bookBtn").on("click", function() {
		$(".bg").empty();
        $.ajax({
            url: "/ajax/bookDetail",
            type: "GET",
            data: { boardNo: boardNo },
            success: function(recipe) {
                // 서버에서 반환된 RecipeBoard 객체를 사용하여 HTML 콘텐츠 생성
                const htmlContent = `
                <div class="col "
						style=" background-image:url('/images/note11.png'); background-size:cover; height:100%; width: 100%; ">
						<div class="row page" style="margin-top:80px;">
							왼쪽페이지
							<div class="col  border border-dark p-2 m-2	">
								<input type="hidden" id="cookingId" th:value="${cookingId}">
								<div class="row">
									<div class="col  d-flex justify-content-center">
										<img src="https://via.placeholder.com/400" alt="샘플이미지">
									</div>
								</div>
								<div class="row  my-2">
									<div class="col text-center">
										<h2 th:text="${rList.boardTitle}"></h2>
									</div>
								</div>
								<div class="row border  my-2 ">
									<div class="col text-center">
										<span th:text="${rList.boardContent}"></span>
									</div>
								</div>
								<div class="row  my-2">
									<div class="col text-center">
										<i class="bi bi-people-fill" style="font-size:40px"></i>사람아이콘
									</div>
									<div class="col text-center ">
										<i class="bi bi-alarm" style="font-size:40px"></i>시계아이콘
									</div>
								</div>
								<div class="row  my-2">
									<div class="col text-center">
										<span th:text="${rList.numberEaters}"></span>인분
									</div>
									<div class="col text-center">
										<span th:text="${rList.foodTime}"></span>분 이내
									</div>
								</div>
							</div>왼쪽페이지 마무리


							오른쪽페이지
							<div class="col border border-dark d-flex flex-column p-2 m-2">
								<div class="row">
									<div class="col">
										<h3 th:text="${rList.foodName}"></h3>
									</div>
								</div>
								반복문 돌려야함
								<div class="row  my-2 border border-danger justify-content-center"
									style="width:100%;    height:200px; margin:0px;">
									<div class="col  border border-primary  " style="width:100%;  height:200px; ">
										<th:block th:each="mList, status: ${mList}">
											<div class="row  my-1 "
												style=" width:550px;  height:30px; font-size:20px; margin-left: 25px;">
												<div class="col text-start">
													<span th:text="${mList.materialName}"></span>
												</div>
												<div class="col text-end">
													<span th:text="${mList.mensuration}"></span>
												</div>
											</div>
										</th:block>
									</div>

								</div>

								<div class="row  my-2">
									<div class="col">
										<h3>양념재료</h3>
									</div>
								</div>
								반복문 돌려야함
								<div class="row  my-2 border border-danger justify-content-center"
									style="width:100%;    height:200px; margin:0px;">
									<div class="col  border border-primary  " style="width:100%;  height:200px; ">
										<th:block th:each="mList, status: ${mList}">
											<div class="row  my-1 "
												style=" width:550px;  height:30px; font-size:20px; margin-left: 25px;">
												<div class="col text-start">
													<span th:text="${mList.materialName}"></span>
												</div>
												<div class="col text-end">
													<spand th:text="${mList.mensuration}"></spand>
												</div>
											</div>
										</th:block>
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
												(<span th:text="${rList.boardView}"></span>)
											</div>
										</div>
									</div>
									<div class="col text-center ">
										<div class="row">
											<div class="col d-flex justify-content-center align-items-center">
												<i class="bi bi-star-fill" style="font-size:40px;"></i>
												<i class="bi bi-star-fill" style="font-size:40px;"></i>
												<i class="bi bi-star-fill" style="font-size:40px;"></i>
											</div>
										</div>
										<div class="row">
											<div class="col">
												(<span th:text="${rList.apoint}"></span>)
											</div>
										</div>
									</div>
									<div class="col  d-flex justify-content-center align-items-center">
										<div class="row">
											<div class="col text-center">
												<i class="accordion-button collapsed  bg-transparent bi bi-share-fill"
													type="button" data-bs-toggle="collapse"
													data-bs-target="#flush-collapseOne" aria-expanded="false"
													aria-controls="flush-collapseOne" style="font-size:55px;">
												</i>
											</div>
										</div>
										<div class="row">
											<div class="col">
											</div>
										</div>

									</div>
								</div>
							</div>오른페이지
						</div>페이지 div
					</div>
				
			방향 아이콘
			<div class="row text-center">
				<div class="col">
					<button id="prevPageBtn" class="btn  btn-dark">
						<i class="bi bi-caret-left-fill" style="font-size:40px;"></i>
					</button>
				</div>
				<div class="col">
					<button id="pauseBtn" class="btn  btn-dark">
						<i class="bi bi-pause-fill" style="font-size:40px;"></i>
					</button>
				</div>
				<div class="col">
					<button id="nextPageBtn" class="btn  btn-dark">
						<i class="bi bi-caret-right-fill" style="font-size:40px;"></i>
					</button>
				</div>
			</div>
			프로필 /sns 위치
			<div class="row my-2" style="height: 80px;">
				<div class="col-3">
				</div>
				<div class="col-6 border">
					<div class="row d-flex align-items-center">
						<div class="col-3">
							<img src="https://via.placeholder.com/100" alt="샘플이미지">
						</div>
						<div class="col ">
							<h1 th:text="${rList.memberId}"></h1>
						</div>
					</div>
				</div>
				<div class="col-3">
					<div id="flush-collapseOne" class="accordion-collapse collapse text-end"
						data-bs-parent="#accordionFlushExample">
						<div class="accordion-body">
							<i class="bi bi-instagram" style="font-size:50px"></i>
							<i class="bi bi-facebook" style="font-size:50px"></i>
							<i class="bi bi-twitter-x" style="font-size:50px"></i>
						</div>
					</div>
				</div>
			</div>
                `;

                // 새로운 콘텐츠를 타겟 div에 추가
                $(".bg").append(htmlContent);
            },
            error: function(error) {
                console.error("Error fetching book details:", error);
            }
        });
    });
});
