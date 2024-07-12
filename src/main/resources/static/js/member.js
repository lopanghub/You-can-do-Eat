$(function() {

	// 회원 정보 수정 폼
	$("#joinForm").on("submit", function() {
		return joinFormCheck();
	});

	$("#reset").on("click", function() {
		$("html, body").animate({ scrollTop: 0 }, "slow");
	});

	$("#pass1").on("keyup", inputCharReplace);
	$("#pass2").on("keyup", inputCharReplace);
	$("#emailId").on("keyup", inputCharReplace);
	$("#emailDomain").on("keyup", inputEmailDomainReplace);

	// 이름에는 한글만 입력할 수 있는 정규표현식
	$("#name").on("input", function() {
		let inputText = $(this).val();
		let regex = /^[ㄱ-ㅎ|가-힣|ㅏ-ㅣ]*$/;

		if (!regex.test(inputText)) {
			let filteredText = inputText.replace(/[^ㄱ-ㅎ|가-힣|ㅏ-ㅣ]/g, '');
			$(this).val(filteredText);
			alert("한글만 입력해주세요.");
		}
	});

	// 회원가입 아이디 실시간으로 변경
	$("#id").on("input", function() {
		const inputValue = $(this).val().trim();
		const inputField = $(this)[0];
		const messageDiv = $("#message"); // message div 요소

		let regExp = /[^A-Za-z0-9]/gi;

		// 아이디에 영문 대소문자와 숫자 이외의 문자가 입력되면 경고 메시지를 표시하고 제거
		if (regExp.test($(this).val())) {
			alert("아이디는 영문 대소문자와 숫자만 가능합니다.");
			$(this).val($(this).val().replace(regExp, ""));
		}

		// 아이디 입력란이 비어있을 때 유효성 검사 결과 및 메시지 표시
		if (inputValue === '') {
			$(inputField).removeClass('is-valid').addClass('is-invalid');
			messageDiv.html('<p class="text-danger">아이디를 입력해주세요.</p>');
			return;
		}

		// 아이디 길이가 5글자 이하일 때 빨간색 테두리 및 메시지 표시
		if (inputValue.length <= 5) {
			$(inputField).removeClass('is-valid').addClass('is-invalid');
			messageDiv.html('<p class="text-danger">아이디는 최소 6글자 이상 입력해주세요.</p>');
		} else if (!regExp.test(inputValue)) {
			// 유효한 아이디 형식일 때 초록색 테두리 및 메시지 삭제
			$(inputField).removeClass('is-invalid').addClass('is-valid');
			messageDiv.empty(); // 기존 메시지 삭제
		} else {
			$(inputField).removeClass('is-valid').addClass('is-invalid');
			messageDiv.html('<p class="text-danger">아이디는 영문 대소문자와 숫자만 가능합니다.</p>');
		}
	});


	// 이메일 도메인 셀렉트 박스가 선택하면
	$("#selectDomain").on("change", function() {
		let str = $(this).val(); // this = #selectDomain

		if (str == '직접입력') {
			$("#emailDomain").val("");
			$("#emailDomain").attr("readonly", false);
			$("#emailDomain").focus("");

		} else if (str == '네이버') {
			$("#emailDomain").val("naver.com");
			$("#emailDomain").attr("readonly", true);
		} else if (str == '다음') {
			$("#emailDomain").val("daum.net");
			$("#emailDomain").attr("readonly", true);
		} else if (str == '구글') {
			$("#emailDomain").val("google.co.kr");
			$("#emailDomain").attr("readonly", true);
		}
	});

	// 우편번호 찾기 버튼이 클릭되면 - 다음 우편번호 찾기 실행
	$("#btnZipcode").click(findZipCode);

	// pass 비교하는 input
	$("#pass2").on("input", function() {
		const pass1Value = $("#pass1").val().trim(); // Pass1의 값
		const pass2Value = $(this).val().trim(); // Pass2의 값

		const inputField = $(this)[0]; // Pass2 입력란 요소
		const feedbackDiv = $("#passwordcheck")[0]; // 비밀번호 일치 여부를 표시

		let regex = /[^A-Za-z0-9]/gi;

		if (pass2Value === '') {
			$(inputField).removeClass('is-valid').addClass('is-invalid');
			$(feedbackDiv).show().text("비밀번호를 입력해주세요.");
			return;
		}

		if (!regex.test(pass2Value)) {
			$(inputField).removeClass('is-invalid').addClass('is-valid');
			$(feedbackDiv).hide(); // 유효한 입력이므로 피드백 숨기기
		} else {
			$(inputField).removeClass('is-valid').addClass('is-invalid');
			$(feedbackDiv).show(); // 유효하지 않은 입력이므로 피드백 보이기
			return;
		}

		// Pass1과 Pass2가 일치하는지 확인
		if (pass1Value === pass2Value) {
			$(inputField).removeClass('is-invalid').addClass('is-valid');
			$(feedbackDiv).hide(); // 일치하므로 피드백 숨기기
		} else {
			$(inputField).removeClass('is-valid').addClass('is-invalid');
			$(feedbackDiv).show().text("비밀번호가 일치하지 않습니다."); // 일치하지 않으므로 피드백 보이기
		}
	});


	// id 중복체크 버튼 클릭
	$("#btnOverlapId").on("click", function() {

		let id = $("#id").val();

		if (id.length === 0) {
			alert("아이디를 입력해 주세요.");
			return;
		}
		if (id.length < 5) {
			alert("아이디는 5자 이상으로 입력해주세요.")
			return;
		}

		// AJAX를 통한 아이디 중복 체크 요청
		$.ajax({
			url: "isIdCheck?id=" + id,
			type: "GET",
			dataType: "json",
			success: function(response) {
				console.log(response);

				let messageDiv = $("#message");
				if (!response.result) {
					// 사용 가능한 아이디일 경우
					messageDiv.html('<p class="text-success">사용 가능한 아이디입니다.</p>');
				} else {
					// 이미 사용 중인 아이디일 경우
					messageDiv.html('<p class="text-danger">이미 사용 중인 아이디입니다.</p>');
				}
			},
			error: function(xhr, status, error) {
				// AJAX 요청 실패 시 처리
				let messageDiv = $("#message");
				messageDiv.html('<p class="text-danger">오류 발생: ' + error + '</p>');
			}
		});
	});

	function joinFormCheck() {
		let name = $("#name").val();
		let id = $("#id").val();
		let pass1 = $("#pass1").val();
		let pass2 = $("#pass2").val();
		let zipcode = $("#zipcode").val();
		let emailId = $("#emailId").val();
		let emailDomain = $("#emailDomain").val();
		let mobile2 = $("#mobile2").val();
		let mobile3 = $("#mobile3").val();

		if (name.length == 0) {
			alert("이름이 입력되지 않았습니다.");
			return false;
		}
		if (id.length <= 5) {
			alert("아이디가 입력되지 않았습니다.");
			return false;
		}
		if (pass1.length == 0) {
			alert("비밀번호가 입력되지 않았습니다.");
			return false;
		}
		if (pass2.length == 0) {
			alert("비밀번호 확인이 입력되지 않았습니다.");
			return false;
		}
		if (pass1 != pass2) {
			alert("비밀번호와 비밀번호 확인이 같지 않습니다.");
			return false;
		}
		if (zipcode.length == 0) {
			alert("우편번호가 입력되지 않았습니다.");
			return false;
		}
		if (address1.length == 0) {
			alert("주소가 입력되지 않았습니다.");
			return false;
		}
		if (emailId.length == 0) {
			alert("이메일 아이디가 입력되지 않았습니다.");
			return false;
		}
		if (emailDomain.length == 0) {
			alert("이메일 도메인이 입력되지 않았습니다.");
			return false;
		}
		if (mobile2.length == 0 || mobile3.length == 0) {
			alert("휴대폰 번호가 입력되지 않았습니다.");
			return false;
		}
	}

});

function inputEmailDomainReplace() {
	let regExp = /[^a-z0-9\.]/g; // 정규표현식 객체
	if (regExp.test($(this).val())) { // 영문 대소문자, 숫자가 아니면
		alert("이메일 도메인은 영문 소문자, 숫자, 점(.)만 가능합니다.");
		$(this).val($(this).val().replace(regExp, "")); // 영문 대소문자나 숫자가 아닌 것들은 전부 삭제(빈 공백) 

	}
};

function inputCharReplace() {
	let regExp = /[^A-Za-z0-9]/gi; // 정규표현식 객체
	if (regExp.test($(this).val())) { // 영문 대소문자, 숫자가 아니면
		alert("아이디는 영문 대소문자와 숫자만 가능합니다.");
		$(this).val($(this).val().replace(regExp, "")); // 영문 대소문자나 숫자가 아닌 것들은 전부 삭제(빈 공백) 

	}
};

function findZipCode() {
	new daum.Postcode({
		oncomplete: function(data) {
			// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

			// 각 주소의 노출 규칙에 따라 주소를 조합한다.
			// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
			var addr = ''; // 주소 변수
			var extraAddr = ''; // 참고항목 변수

			//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
			if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
				addr = data.roadAddress;
			} else { // 사용자가 지번 주소를 선택했을 경우(J)
				addr = data.roadAddress;
			}

			// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
			if (data.userSelectedType === 'R') {
				// 법정동명이 있을 경우 추가한다. (법정리는 제외)
				// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
				if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
					extraAddr += data.bname;
				}
				// 건물명이 있고, 공동주택일 경우 추가한다.
				if (data.buildingName !== '' && data.apartment === 'Y') {
					extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
				}
				// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
				if (extraAddr !== '') {
					extraAddr = ' (' + extraAddr + ')';
				}
				// 조합된 참고항목을 해당 필드를 주소 뒤에 넣는다.
				addr += extraAddr;

			}

			// 우편번호와 주소 정보를 해당 필드에 넣는다.
			$("#zipcode").val(data.zonecode);
			$("#address1").val(addr);
			// 커서를 상세주소 필드로 이동한다.
			$("#address2").focus();
		}
	}).open();
}