$(function(){
	
	// 삭제하기 버튼 클릭되면
		$("#noticeDetailDelete").on("click", function(){
			let pass = $("#pass").val();
			if(pass.length <= 0) {
				alert("비밀번호를 입력해주세요");
				return false;
			}
			
			$("#rPass").val(pass);
			$("#checkForm").attr("action", "deleteNotice");
			$("#checkForm").attr("method", "post");
			
			if(confirm("해당 공지를 삭제하시겠습니까?")){
				alert("게시글이 삭제되었습니다.");
				$("#checkForm").submit();
			}
			
		});


	
	// 공지 수정 폼 유효성 검사
	$("#noticeUpdateForm").on("submit", function(e){
			
		if($("#title").val().length <= 0){
			alert("제목을 입력해주세요");
			return false;	
		}
			
		if($("#content").val().length <= 0){
				alert("내용을 입력해주세요");
				return false;	
			}			
	});


	
	// 공지 수정하기 버튼이 클릭됐을때
	$("#noticeDetailUpdate").on("click", function(){
		let pass = $("#pass").val();
		if(pass.length <= 0) {
			alert("비밀번호를 입력해주세요");
			return false;
		}
		
		$("#rPass").val(pass);
		$("#checkForm").attr("action", "noticeUpdateForm");
		$("#checkForm").attr("method", "post");
		$("#checkForm").submit();
		
	});
	
	
	
	// 공지 쓰기 폼 유효성 검사
	$("#noticeWriteForm").on("submit", function(e){
			
		if($("#title").val().length <= 0){
			alert("제목을 입력해주세요");
			return false;	
		}
		
			
		if($("#content").val().length <= 0){
				alert("내용을 입력해주세요");
				return false;	
			}
		if(confirm("해당 공지를 업로드하시겠습니까?"))	{
			this.submut();
		}			

	});
	
	
});