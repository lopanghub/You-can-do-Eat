$(function() {
	$("#commentForm").on("submit", function(event) {
		event.preventDefault(); // 폼 제출 방지

		if ($("#commentPoint").val().length <= 0) {
			alert("점수가 입력되지 않았습니다.\n점수를 입력해주세요");
			$("#commentPoint").focus();
			return false;
		}
		if ($("#commentContent").val().length <= 0) {
			alert("후기가 입력되지 않았습니다.\n후기를 입력해주세요");
			$("#commentContent").focus();
			return false;
		}

		let boardNo = $("#boardNo").val();
		let commentContent = $("#commentContent").val();
		let commentPoint = $("#commentPoint").val();

		$.ajax({
			url: "/ajax/addComment",
			type: "POST",
			data: {
				boardNo: boardNo,
				commentContent: commentContent,
				commentPoint: commentPoint
			},
			success: function(comment) {
				console.log(comment);
				appendCommentToList(comment);
				$("#commentContent").val("");
				$("#commentPoint").val("");
			},
			error: function(error) {
				console.error("Error adding comment:", error);
			}
		});
	});

	function appendCommentToList(comment) {
		let starIcons = "";
		if(comment.commentPoint > 0){
			for (let i = 0; i < comment.commentPoint; i++) {
				starIcons += '<i class="bi bi-star-fill"></i>';
			}
		}
		if(comment.commentPoint < 5){
			for (let i = comment.commentPoint; i < 5; i++) {
				starIcons += '<i class="bi bi-star"></i>';
			}
		}

		let newCommentHtml = `
            <li class="list-group-item">
                <div class="d-flex justify-content-between">
                    <div>
                        <p class="mb-1"><strong>${comment.memberId}</strong></p>
                        <p class="mb-1">${comment.commentContent}</p>
                        <small class="text-muted">${new Date(comment.createdAt).toISOString().split('T')[0]}</small>
                        <div>
                        	${starIcons}
                        	</div>
                    </div>
                    <div>
                        <button class="btn btn-secondary btn-sm" onclick="editComment(this)">수정</button>
                        <button class="btn btn-danger btn-sm" onclick="deleteComment(this)">삭제</button>
                    </div>
                </div>
            </li>`;
		$("#commentList").append(newCommentHtml);
	}

	// 수정 폼이 이미 열려있는지 여부를 저장하는 변수
	let isEditFormOpen = false;

	// 댓글 수정 버튼 클릭 시 수정 폼을 댓글 아래에 삽입
	window.editComment = function(button) {
		var listItem = $(button).closest('li');
		var commentId = listItem.data('comment-id');
		var commentContent = listItem.find('.comment-content').text();
		var commentPoint = listItem.find('.star-icons .bi-star-fill').length;
		
		// 기존에 열린 수정 폼이 있으면 제거
		if (isEditFormOpen) {
			$('.edit-comment-form').remove();
			isEditFormOpen = false;
		}

		// 수정 폼 HTML 생성
		var editFormHtml = `
            <div class="edit-comment-form mt-3">
                <form id="editCommentForm">
                    <div class="mb-3">
                        <div
											class="row my-2 d-flex justify-content-between align-items-center ">
											<div class="col-9">
												<label for="commentContent" class="form-label  fw-bold">후기
													내용 </label>
											</div>
											<div class="col-1 text-end">
												<label for="commentPoint" class="form-label  fw-bold">점수
													: </label>
											</div>
											<div class="col-2">
												<input type="number" class="form-control w-3"
													id="editCommentPoint" name="commentPoint" min="0" max="5"
													placeholder="1~5점" required>
											</div>
										</div>
                        
                        
                        <textarea class="form-control" id="editCommentContent" name="commentContent" rows="3" required>${commentContent}</textarea>
                    </div>
                    <input type="hidden" id="editCommentId" name="commentId" value="${commentId}">
                    <button type="submit" class="btn btn-primary">댓글 수정</button>
                    <button type="button" class="btn btn-secondary" onclick="cancelEditComment()">취소</button>
                </form>
            </div>
        `;

		// 수정 폼을 댓글 아래에 삽입
		listItem.append(editFormHtml);
		isEditFormOpen = true;
	};

	// 수정 폼 취소 버튼 클릭 시 폼 제거
	window.cancelEditComment = function() {
		$('.edit-comment-form').remove();
		isEditFormOpen = false;
	};

	// 댓글 수정 폼 제출 시 AJAX 요청 보내기
	$(document).on('submit', '#editCommentForm', function(event) {
		event.preventDefault();

		var commentId = $('#editCommentId').val();
		var commentContent = $('#editCommentContent').val();
		var commentPoint = $('#editCommentPoint').val();
		console.log(commentId +"왔냐?");
		$.ajax({
			url: '/ajax/updateComment',
			type: 'POST',
			data: {
				commentId: commentId,
				commentContent: commentContent,
				commentPoint: commentPoint
			},
			success: function(response) {
				console.log('Comment updated:', response);
				updateCommentInList(response);
				$('.edit-comment-form').remove();
				isEditFormOpen = false;
			},
			error: function(error) {
				console.error('Error updating comment:', error);
			}
		});
	});

	// 댓글 리스트에서 해당 댓글 업데이트
	function updateCommentInList(comment) {
		var listItem = $('#commentList').find(`li[data-comment-id="${comment.commentId}"]`);
		listItem.find('.comment-content').text(comment.commentContent);



		var starIcons = '';
		if(comment.commentPoint > 0){
			for (let i = 0; i < comment.commentPoint; i++) {
				starIcons += '<i class="bi bi-star-fill"></i>';
			}
		}
		if(comment.commentPoint < 5){
			for (let i = comment.commentPoint; i < 5; i++) {
				starIcons += '<i class="bi bi-star"></i>';
			}
		}
		listItem.find('.star-icons').html(starIcons);
	}

	function deleteComment(button) {
		// 댓글 삭제 버튼 클릭 시 호출되는 함수
		// 실제 구현에서는 서버에 삭제 요청을 보내야 합니다.
		var listItem = button.closest('li');
		listItem.remove(); // 임시로 댓글 아이템을 리스트에서 제거
	}


});