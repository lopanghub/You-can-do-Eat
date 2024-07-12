$(function() {

	document.getElementById('profile').addEventListener('change', function(event) {
		var previewImage = document.getElementById('preview'); // 이미지 미리 보기를 위한 img 요소 선택
		var file = event.target.files[0]; // 업로드된 파일 가져오기
		var reader = new FileReader(); // 파일을 읽기 위한 FileReader 객체 생성

		reader.onload = function() {
			previewImage.src = reader.result; // 파일을 읽어서 이미지 미리 보기 엘리먼트의 src 속성에 할당
		};

		if (file) {
			reader.readAsDataURL(file); // 파일을 Data URL로 변환하여 읽기 시작
		}
	});

	// 비밀번호 변경
	$("#memberUpdateForm").on("click", "#oldPassUpdateBtn", function() {
		if ($("#passUpdateForm").is(":visible")) {
			$("#passUpdateForm").slideUp(300);

		} else { // 화면에 보이지 않을때
			$("#passUpdateForm").removeClass("d-none")
				.css("display", "none").slideDown(300);
		}
	});

	// 회원 정보 수정 폼
	$("#memberUpdateForm").on("submit", function() {
		// 비밀번호가 체크되었는지 확인
		if (!$("#btnPassCheck").attr("disabled")) {
			alert("기존 비밀번호를 확인해주세요.");
			return false;
		}
		return joinupdateCheck();
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
			url: "isPassCheck", 	// 요청 주소
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

					// 비밀번호 확인 후 버튼 생성
					let updateBtn = $('<button>', {
						id: 'oldPassUpdateBtn',
						text: '비밀번호 수정',
						class: 'btn btn-success',
						type: 'button'
					});
					$('#someContainer').append(updateBtn);
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
	function joinupdateCheck() {
		let name = $("#name").val();
		let id = $("#id").val();
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
			alert("아이디는 5자 이상입니다.");
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
