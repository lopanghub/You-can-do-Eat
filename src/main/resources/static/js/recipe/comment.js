$(function() {
	let id = $("#loginId").val();
	// 댓글 추가
	$("#commentForm").on("submit", function(event) {
		event.preventDefault(); // 폼 제출 방지


		let boardNo = $("#boardNo").val();
		let commentContent = $("#commentContent").val();
		let commentPoint = $("#commentPoint").val();
		console.log(id);
		console.log(boardNo);
		console.log(commentContent);
		console.log(commentPoint);
		if(id == null || id === ""){
		    alert("로그인후 이용해주세요");
		    return false;
		}
		$.ajax({
			url: "/ajax/addComment",
			type: "POST",
			data: {
				boardNo: boardNo,
				commentContent: commentContent,
				commentPoint: commentPoint,
				memberId: id
			},
			success: function(comment) {
				console.log(comment.stars);
				appendCommentToList(comment.comment);
				$("#falseText").empty();
				$("#stars").html("");
				const ApontHtml = `<div>${comment.stars}</div>`
				
				$("#stars").html(ApontHtml);				
				$("#commentContent").val("");
				$("#commentPoint").val("");
				$("#commentAt").val("");
			},
			error: function(error) {
				console.error("Error adding comment:", error);
			}
		});
	});

	// 댓글이 등록됐을 때 별점 출력
	function appendCommentToList(comment) {
		let starIcons = "";
		if (comment.commentPoint > 0) {
			for (let i = 0; i < comment.commentPoint; i++) {
				starIcons += ' <i class="bi bi-star-fill"></i>';
			}
		}
		if (comment.commentPoint < 5) {
			for (let i = comment.commentPoint; i < 5; i++) {
				starIcons += ' <i class="bi bi-star"></i>';
			}
		}

		let createdAt = 'N/A';
		if (comment.createdAt) {
			let date = new Date(comment.createdAt);
			if (!isNaN(date.getTime())) {
				createdAt = date.toISOString().split('T')[0];
			}
		}

		let newCommentHtml = `
			<li class="list-group-item" data-comment-id="${comment.commentId}">
				<div class="d-flex justify-content-between">
					<div>
						<p class="mb-1"><strong>${comment.memberId}</strong></p>
						<p class="mb-1 comment-content">${comment.commentContent}</p>
						<small class="text-muted">${createdAt}</small>
						<div class="star-icons">
							${starIcons}
						</div>
					</div>
					<div>
						<button class="comment-edit btn-sm" onclick="editComment(this)">수정</button>
						<button class="comment-delete btn-sm" onclick="deleteComment(this)">삭제</button>
					</div>
				</div>
			</li>`;
		$("#commentList").prepend(newCommentHtml);
	}

	// 수정 폼이 이미 열려있는지 여부를 저장하는 변수
	let isEditFormOpen = false;

	// 댓글 수정 버튼 클릭 시 수정 폼을 댓글 아래에 삽입
	window.editComment = function(button) {
		var listItem = $(button).closest('li');
		var commentId = listItem.data('comment-id');
		var commentContent = listItem.find('.comment-content').text();
		var commentPoint = listItem.find('.star-icons .bi-star-fill').length;
		console.log("commentId"+ commentId);
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
						<div class="row my-2 d-flex justify-content-between align-items-center">
							<div class="col-9">
								<label for="commentContent" class="form-label fw-bold">기존 작성 내용</label>
							</div>
							<div class="col-1 text-end">
								<label for="commentPoint" class="form-label fw-bold">점수:</label>
							</div>
							
							<div class="col-2">
		                        <select class="form-select" id="editCommentPoint" name="commentPoint" required>
		                            <option value="">선택</option>
		                            <option value="1" ${commentPoint == 1 ? 'selected' : ''}>1점</option>
		                            <option value="2" ${commentPoint == 2 ? 'selected' : ''}>2점</option>
		                            <option value="3" ${commentPoint == 3 ? 'selected' : ''}>3점</option>
		                            <option value="4" ${commentPoint == 4 ? 'selected' : ''}>4점</option>
		                            <option value="5" ${commentPoint == 5 ? 'selected' : ''}>5점</option>
		                        </select>
		                    </div>
		                </div>
						<textarea class="form-control" id="editCommentContent" name="commentContent" rows="3" required>${commentContent}</textarea>
					</div>
					<input type="hidden" id="editCommentId" name="commentId" value="${commentId}">
					 <button type="submit" class="comment-edit">댓글 수정</button>
					<button type="button" class="comment-back" onclick="cancelEditComment()">취소</button>
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
		console.log("클릭 완료");
		let boardNo = $("#boardNo").val();
		var commentId = $('#editCommentId').val();
		var commentContent = $('#editCommentContent').val();
		var commentPoint = $('#editCommentPoint').val();
		$.ajax({
			url: '/ajax/updateComment',
			type: 'POST',
			data: {
				boardNo : boardNo,
				commentId: commentId,
				commentContent: commentContent,
				commentPoint: commentPoint
			},
			success: function(response) {
				console.log('response updated:', response);
				
				$("#stars").html("");
				const ApontHtml = `<div>${response.stars}</div>`
				
				$("#stars").html(ApontHtml);			
				updateCommentInList(response.comment);
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
		if (comment.commentPoint > 0) {
			for (let i = 0; i < comment.commentPoint; i++) {
				starIcons += ' <i class="bi bi-star-fill"></i>';
			}
		}
		if (comment.commentPoint < 5) {
			for (let i = comment.commentPoint; i < 5; i++) {
				starIcons += ' <i class="bi bi-star"></i>';
			}
		}
		listItem.find('.star-icons').html(starIcons);
	}

	// 댓글 삭제 기능 추가
	window.deleteComment = function(button) {
		if (confirm('정말로 이 댓글을 삭제하시겠습니까?')) {
			var listItem = $(button).closest('li');
			var commentId = listItem.data('comment-id');
			let boardNo = $("#boardNo").val();
			console.log("commentId : " + commentId);
			console.log("id : " + id);
			$.ajax({
				url: '/ajax/deleteComment',
				type: 'POST',
				data: { 
					boardNo:boardNo,
					commentId: commentId },
				success: function(response) {
					console.log('Comment deleted:', response);
					$("#stars").html("");
				const ApontHtml = `<div>${response.stars}</div>`
				
				$("#stars").html(ApontHtml);			
					listItem.remove();
				},
				error: function(error) {
					console.error('Error deleting comment:', error);
				}
			});
		}
	};
});
