$(function() {
    let id = $("#loginId").val();
    
    // 댓글 추가
   $("#commuCommentForm").on("submit", function(event) {
        event.preventDefault(); // 폼 제출 방지
		
        let no = $("#no").val();
        let commuCommentContent = $("#commuCommentContent").val();
        if (id == null || id === "") {
            alert("로그인후 이용해주세요");
            return false;
        }
        console.log(commuCommentContent);
        $.ajax({
            url: "/ajax/addCommuComment",
            type: "POST",
            data: {
                no: no,
                commuCommentContent: commuCommentContent,
                memberId: id
            },
            success: function(comment) {
				console.log("comment",comment);
                appendCommentToList(comment);
                $("#falseText").empty();
                $("#commuCommentContent").val("");
            },
            error: function(error) {
                console.error("Error adding comment:", error);
            }
        });
    });

// 댓글이 등록됐을 때 별점 출력
	function appendCommentToList(comment) {

		let createdAt = 'N/A';
		if (comment.createdAt) {
			let date = new Date(comment.createdAt);
			if (!isNaN(date.getTime())) {
				createdAt = date.toISOString().split('T')[0];
			}
		}

		let newCommentHtml = `
			<li class="list-group-item" data-commuComment-id="${comment.commuCommentId}">
				<div class="d-flex justify-content-between">
					<div>
						<p class="mb-1"><strong>${comment.memberId}</strong></p>
						<p class="mb-1 comment-content">${comment.commuCommentContent}</p>
						<small class="text-muted">${createdAt}</small>
					</div>
					<div>
						<button class="comment-edit btn-sm" onclick="editCommuComment(this)">수정</button>
						<button class="comment-delete btn-sm" onclick="deleteCommuComment(this)">삭제</button>
					</div>
				</div>
			</li>`;
		$("#commuCommentList").prepend(newCommentHtml);
	}
    // 수정 폼이 이미 열려있는지 여부를 저장하는 변수
    let isEditFormOpen = false;

    // 댓글 수정 버튼 클릭 시 수정 폼을 댓글 아래에 삽입
    window.editCommuComment = function(button) {
        var listItem = $(button).closest('li');
       var commuCommentId = listItem.attr('data-commuComment-id');
        var commuCommentContent = listItem.find('.commuComment-content').text();
        console.log("commentId" + commuCommentId);

        // 기존에 열린 수정 폼이 있으면 제거
        if (isEditFormOpen) {
            $('.edit-comment-form').remove();
            isEditFormOpen = false;
        }
        // 수정 폼 HTML 생성
		var editFormHtml = `
			<div class="edit-comment-form mt-3">
				<form id="editCommuCommentForm">
					<div class="mb-3">
						<div class="row my-2 d-flex justify-content-between align-items-center">
							<div class="col-9">
								<label for="commentContentContent" class="form-label fw-bold">기존 작성 내용</label>
							</div>
		                </div>
						<textarea class="form-control" id="editCommentContent" name="commentContentContent" rows="3" required>${commuCommentContent}</textarea>
					</div>
					<input type="hidden" id="editCommentContentId" name="commentContentId" value="${commuCommentId}">
					 <button type="submit" class="comment-edit">댓글 수정</button>
					<button type="button" class="comment-back" onclick="cancelEditComment()">취소</button>
				</form>
			</div>
		`;

		// 수정 폼을 댓글 아래에 삽입
		listItem.append(editFormHtml);
		isEditFormOpen = true;
	}

    // 수정 폼 취소 버튼 클릭 시 폼 제거
    window.cancelEditComment = function() {
        $('.edit-comment-form').remove();
        isEditFormOpen = false;
    };

    // 댓글 수정 폼 제출 시 AJAX 요청 보내기
    $(document).on('submit', '#editCommuCommentForm', function(event) {
        event.preventDefault();
        console.log("클릭 완료");
        let no = $("#no").val();
        var commuCommentId = $('#editCommentContentId').val();
        var commuCommentContent = $('#editCommentContent').val();
        
        $.ajax({
            url: '/ajax/updateCommuComment',
            type: 'POST',
            data: {
                commuCommentId: commuCommentId,
                commuCommentContent: commuCommentContent,
            },
            success: function(response) {
                console.log('response updated:', response);
			updateCommentInList(response);
                $('.edit-comment-form').remove();
                isEditFormOpen = false;
            },
            error: function(error) {
                console.error('Error updating comment:', error);
            }
        });
    });
    function updateCommentInList(comment) {
		var listItem = $('#commuCommentList').find(`li[data-commuComment-id="${comment.commuCommentId}"]`);
		console.log(comment.commuCommentContent);
		listItem.find('.comment-content').text(comment.commuCommentContent);
		
	}


   window.deleteCommuComment = function(button) {
    if (confirm('정말로 이 댓글을 삭제하시겠습니까?')) {
        var listItem = $(button).closest('li'); // 삭제 버튼이 속한 <li> 요소를 찾음
        var commuCommentId = listItem.attr('data-commuComment-id'); // data-commuComment-id 속성의 값 가져옴
        var no = $("#no").val(); // 게시글 번호 (필요에 따라 가져옴)
        console.log(commuCommentId);
        
        $.ajax({
            url: '/ajax/deleteCommuComment', // 삭제 처리를 수행할 컨트롤러 URL
            type: 'POST',
            data: {
                no: no, // 게시글 번호 전달
                commuCommentId: commuCommentId // 삭제할 댓글의 ID 전달
            },
            success: function(response) {
                console.log('Comment deleted:', response);
                listItem.remove(); // 화면에서 삭제된 댓글 제거
            },
            error: function(error) {
                console.error('Error deleting comment:', error);
            }
        });
    }
};
})