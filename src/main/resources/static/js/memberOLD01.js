$(function() {

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