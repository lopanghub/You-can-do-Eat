$(function() {
	// 로그인 폼 user id
	$("#userId").on("input", function() {
		const inputValue = $(this).val().trim(); // 입력란 현재 값 value를 가져와 공백을 제거 한 후 변수에 저장
		const inputField = $(this)[0]; // 입력란 요소를 가져오는 코드
		const feedbackDiv = $("#please1")[0]; // 유효하지 않은 코드를 피드백 메시지 요소를 가져와 feedbackDiv 변수 저장 - jQuery

		let regex = /[^A-Za-z0-9]/gi;

		if (inputValue === '') {
			$(inputField).removeClass('is-valid').addClass('is-invalid');
			$(feedbackDiv).show().text("아이디를 입력해주세요.");
			return;
		}

		// 문자열이 입력되었을 때 테두리를 초록색으로 변경
		if (!regex.test(inputValue)) {
			$(inputField).removeClass('is-invalid').addClass('is-valid');
			$(feedbackDiv).hide(); // 유효한 입력이므로 피드백 숨기기
		} else {
			$(inputField).removeClass('is-valid').addClass('is-invalid');
			$(feedbackDiv).show(); // 유효하지 않은 입력이므로 피드백 보이기
		}
	});
	// 로그폼 user pass
	$("#userPass").on("input", function() {
		const inputValue = $(this).val().trim(); // 입력란 현재 값 value를 가져와 공백을 제거 한 후 변수에 저장
		const inputField = $(this)[0]; // 입력란 요소를 가져오는 코드
		const feedbackDiv = $("#please2")[0]; // 유효하지 않은 코드를 피드백 메시지 요소를 가져와 feedbackDiv 변수 저장 - jQuery

		let regex = /[^A-Za-z0-9]/gi;

		if (inputValue === '') {
			$(inputField).removeClass('is-valid').addClass('is-invalid');
			$(feedbackDiv).show().text("비밀번호를 입력해주세요.");
			return;
		}

		// 문자열이 입력되었을 때 테두리를 초록색으로 변경
		if (!regex.test(inputValue)) {
			$(inputField).removeClass('is-invalid').addClass('is-valid');
			$(feedbackDiv).hide(); // 유효한 입력이므로 피드백 숨기기
		} else {
			$(inputField).removeClass('is-valid').addClass('is-invalid');
			$(feedbackDiv).show() // 유효하지 않은 입력이므로 피드백 보이기
		}
	});
});