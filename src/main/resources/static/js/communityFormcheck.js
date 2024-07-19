$(function(){
	$('.community-btn').on("click", function(event) {
    let member = $('#loginId').val(); 
    if (member === undefined || member === "" || member === null) {
        event.preventDefault(); // 기본 동작을 막음
        alert('로그인 후 이용해주세요.'); // 경고 메시지 표시
        window.location.href = '/login'; // 로그인 페이지로 리디렉션
    }
});
	
	
	
	// 삭제하기 버튼 클릭되면
		$("#communityDetailDelete").on("click", function(){
			
			$("#checkForm").attr("action", "deleteCommunity");
			$("#checkForm").attr("method", "post");
			
			if(confirm("해당 게시물을 삭제하시겠습니까?")){
				alert("게시글이 삭제되었습니다.");
				$("#checkForm").submit();
			}
			
		});


	
	// 공지 수정 폼 유효성 검사
	$("#communityUpdateForm").on("submit", function(e){
			
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
	$("#communityDetailUpdate").on("click", function(){
		
		$("#checkForm").attr("action", "communityUpdateForm");
		$("#checkForm").attr("method", "post");
		$("#checkForm").submit();
		
	});
	
	
	
	// 공지 쓰기 폼 유효성 검사
	$("#communityWriteForm").on("submit", function(e){
			
		if($("#title").val().length <= 0){
			alert("제목을 입력해주세요");
			return false;	
		}
		
			
		if($("#content").val().length <= 0){
				alert("내용을 입력해주세요");
				return false;	
			}
		if(confirm("해당 게시물을 업로드하시겠습니까?"))	{
			this.submut();
		}			

	});
	
	
});