$(document).ready(function() {
	// 유효성 검사 함수
	function validateForm() {
		let isValid = true;
		let materialCount = $('#material .materialset').length;
		let cookingCount = $('#cookingSections .cookingSet').length;
		console.log("materialCount"+materialCount);
		console.log("cookingCount"+cookingCount);
		// 입력 필드 검사
		$('input[type="text"], textarea, input[type="number"], select').each(function() {
			if ($(this).val() === '') {
				alert('모든 입력 필드를 채워주세요.');
				$(this).focus();
				isValid = false;
				return false; // each 루프 중단
			}
		});

		if (!isValid) return false;

		// 재료와 조리과정 개수 검사
		if (materialCount < 1) {
			alert('최소 하나 이상의 재료를 등록해주세요.');
			return false;
		}

		if (cookingCount < 1) {
			alert('최소 하나 이상의 조리과정을 등록해주세요.');	
			return false;
		}
		$('input[type="text"], textarea, input[type="number"], select').each(function() {
			if ($(this).val() === '') {
				alert('모든 입력 필드를 채워주세요.');
				$(this).focus();
				isValid = false;
				return false; // each 루프 중단
			}
		});

		// .mensuration 필드 검사
		$('input[name="mensurations"]').each(function() {
			if ($(this).val() === '') {
				alert('모든 재료의 양을 입력해주세요.');
				$(this).focus();
				isValid = false;
				return false; // each 루프 중단
			}
		});

		// 요리과정의 입력 필드 검사
		$('#cookingSections .cookingSet').each(function() {
			let cookTitle = $(this).find('input[name="cookTitles"]').val();
			let cookMethod = $(this).find('input[name="cookMethods"]').val();
			let recommended = $(this).find('input[name="recommendeds"]').val();

			if (cookTitle === '' || cookMethod === '' || recommended === '') {
				alert('요리 과정의 모든 필드를 채워주세요.');
				$(this).find('input[name="cookTitles"]').focus();
				isValid = false;
				return false; // each 루프 중단
			}
		});

		return isValid;
	}

	// 폼 제출 전에 유효성 검사 실행
	$('#recipeUpdateForm').on('submit', function(event) {
		if (!validateForm()) {
			event.preventDefault(); // 제출 중단
		}
	});
});
