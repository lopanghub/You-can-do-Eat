$(function(){
	
	// 삭제하기 버튼 클릭되면
		$("#eventDetailDelete").on("click", function(){
			let pass = $("#pass").val();
			if(pass.length <= 0) {
				alert("비밀번호를 입력해주세요");
				return false;
			}
			
			$("#rPass").val(pass);
			$("#checkForm").attr("action", "deleteEvent");
			$("#checkForm").attr("method", "post");
			
			if(confirm("해당 이벤트를 삭제하시겠습니까?")){
				alert("이벤트가 삭제되었습니다.");
				$("#checkForm").submit();
			}
			
		});


	
	// 공지 수정 폼 유효성 검사
	$("#eventUpdateForm").on("submit", function(e){
			
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
	$("#eventDetailUpdate").on("click", function(){
		let pass = $("#pass").val();
		if(pass.length <= 0) {
			alert("비밀번호를 입력해주세요");
			return false;
		}
		
		$("#rPass").val(pass);
		$("#checkForm").attr("action", "eventUpdateForm");
		$("#checkForm").attr("method", "post");
		$("#checkForm").submit();
		
	});
	
	
	
	// 공지 쓰기 폼 유효성 검사
	$("#eventWriteForm").on("submit", function(e){
			
		if($("#title").val().length <= 0){
			alert("제목을 입력해주세요");
			return false;	
		}
		
			
		if($("#content").val().length <= 0){
				alert("내용을 입력해주세요");
				return false;	
			}
		if(confirm("해당 이벤트를 업로드하시겠습니까?"))	{
			this.submut();
		}			

	});
	
	
});