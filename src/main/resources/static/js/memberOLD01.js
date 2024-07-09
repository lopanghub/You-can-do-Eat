$(function() {

	// 회원 정보 수정 폼
	$("#memberUpdateForm").on("submit", function() {

		// 비밀번호가 체크되었는지 확인
		if (!$("#btnPassCheck").attr("disabled")) {
			alert("기존 비밀번호를 확인해주세요.");
			return false;
		}

		return joinFormCheck();

	});

	// 회원 정보 수정 폼에서 비밀번호 확인 버튼이 클릭되면
	$("#btnPassCheck").click(function() {

		let oldPass = $("#oldPass").val();
		let oldId = $("#id").val();

		if ($.trim(oldPass).length == 0) {
			alert("기존 비밀번호를 입력해주세요.");
			return false;
		}
		let data = "id=" + oldId + "&pass=" + oldPass;
		console.log("data : " + data);

		// AJAX 구현 방법
		// XMLHttpRequest 객체, 
		// JQuery의 AJax 지원 메서드 $.ajax();
		// ES6+ Fetch API
		// AXIOS의 라이브러리
		$.ajax({
			url: "passCheck.ajax", 	// 요청 주소
			type: "get", 			// 요청 방식 폼 method
			data: data, 			// 서버로 보내는 데이터
			dataType: "json",		// 응답으로 받을 결과 데이터 형식
			success: function(resData) {
				// 단순 성공이 아닌 ajax가 성공되고 응답 데이터(서버로부터 받은 데이터)를 dataType에 맞게 parsing이 완료 되면 호출되는 callback
				console.log(resData);
				if (resData.result) { // 비밀번호가 맞으면
					// 비밀번호 확인 버튼을 비활성화 - disabled
					$("#oldPassComment").text("비밀번호가 확인 되었습니다!");
					$("#btnPassCheck").attr("disabled", true);
					$("#oldPass").attr("readonly", true);
					$("#pass1").focus();
				} else { // 비밀번호가 틀리면
					alert("비밀번호가 틀립니다! 다시 시도해주세요.");
					$("#oldPass").val("").focus();
				}
			},
			error: function() {
				// ajax 작업 중에 오류가 발생되면 호출되는 callback
				console.log("error");
			}
		});
	});
	//아이디 중복 폼이 submit이 될 때
	$("#idCheckForm").on("submit", function() {
		let id = $("#checkId") + val();
		if (id.length == 0) {
			alert("아이디를 입력해주세요.");
			return false;
		}

		if (id.length < 5) {

			alert("아이디는 5자 이상이어야 합니다.")

			return false;
		}

	});

	$("#btnOverlapId").on("click", function() {
		let id = $("#id").val();
		if (id.length == 0) {
			alert("아이디를 입력해 주세요.");

			return false;
		}
		if (id.length < 5) {
			alert("아이디는 5자 이상이어야 합니다.");

			return false;
		}
	});
	// 회원가입 폼 아이디 중복
	$("#id").on("keyup", function() {
		let regExp = /[^A-Za-z0-9]/gi; // 정규표현식 객체
		if (regExp.test($(this).val())) { // 영문 대소문자, 숫자가 아니면
			alert("아이디는 영문 대소문자와 숫자만 가능합니다.");
			$(this).val($(this).val().replace(regExp, "")); // 영문 대소문자나 숫자가 아닌 것들은 전부 삭제(빈 공백) 
		}
	});
	// 로그인폼 유효성 검사
	$("#loginForm").on("submit", function() {

		let id = $("#userId").val();
		let pass = $("#userPass").val();

		if (id.length <= 0) {
			alert("아이디를 입력해주세요.");
			$("#userId").focus();

			return false;
		}
		if (pass.length <= 0) {
			alert("비밀번호를 입력해주세요.");
			$("#userPass").focus();

			return false;
		}
	});
});