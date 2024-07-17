$(document).ready(function() {
	// 폼 제출 이벤트 처리
	$('#recipeUpdateForm').submit(function(event) {
		let isValid = true;

		// 레시피 제목 검사
		if ($('#boardTitle').val().trim() === '') {
			alert('레시피 제목을 입력하세요.');
			$('#boardTitle').focus();
			isValid = false;
			event.preventDefault(); // 폼 제출 중단
			return false;
		}

		// 레시피 소개 검사
		if ($('#boardContent').val().trim() === '') {
			alert('레시피 소개를 입력하세요.');
			$('#boardContent').focus();
			isValid = false;
			event.preventDefault(); // 폼 제출 중단
			return false;
		}

		// 카테고리 선택 검사
		if ($('#foodGenre').val() === '') {
			alert('카테고리를 선택하세요.');
			$('#foodGenre').focus();
			isValid = false;
			event.preventDefault(); // 폼 제출 중단
			return false;
		}

		// 대표사진 선택 검사
		if ($('#thumbnailname').val() === '') {
			alert('대표사진을 선택하세요.');
			$('#thumbnailname').focus();
			isValid = false;
			event.preventDefault(); // 폼 제출 중단
			return false;
		}

		// 요리 정보 검사
		if ($('#numberEaters').val().trim() === '') {
			alert('인원을 입력하세요.');
			$('#numberEaters').focus();
			isValid = false;
			event.preventDefault(); // 폼 제출 중단
			return false;
		}

		if ($('#foodTime').val().trim() === '') {
			alert('요리 시간을 입력하세요.');
			$('#foodTime').focus();
			isValid = false;
			event.preventDefault(); // 폼 제출 중단
			return false;
		}
		

    if (!validateMaterial()) {
        isValid = false;
    }

    if (!validateCooking()) {
        isValid = false;
    }

    if (!isValid) {
        event.preventDefault(); // 폼 제출 중단
    }
  
    return isValid;
})
function validateMaterial() {
    let isValid = true;

    $('#material').find('.row[id^="materialRow"]').each(function() {
        let id = $(this).attr('id');
        let number = id.match(/\d+$/);
        if (number) {
            let materialName = $(`#material${number[0]}\\.materialName`).val().trim();
            let mensuration = $(`#material${number[0]}\\.mensuration`).val().trim();

            if (materialName === "") {
                isValid = false;
                alert(`재료 이름을 입력해 주세요. (행: ${number[0]})`);
                $(`#material${number[0]}\\.materialName`).focus();
                return false; // 루프 중단
            }

            if (mensuration === "") {
                isValid = false;
                alert(`재료 양을 입력해 주세요. (행: ${number[0]})`);
                $(`#material${number[0]}\\.mensuration`).focus();
                return false; // 루프 중단
            }
        }
    });

    return isValid;
}

function validateCooking() {
    let isValid = true;
    let cookingCount = $('.cookingSection .row[id^="cooking"]').length;

    if (cookingCount === 0) {
        alert('조리 과정을 최소 1개 이상 입력하세요.');
        isValid = false;
        return false;
    }

    $('.cookingSection .row[id^="cooking"]').each(function() {
        let cookingIndex = $(this).data('cookingindex');
        let cookMethod = $(`#cookings${cookingIndex}\\.cookMethod`).val().trim();
        let recommended = $(`#cookings${cookingIndex}\\.recommended`).val().trim();

        // recommended 값이 null인 경우 빈 문자열로 설정
        if (recommended == null) {
            recommended = "";
        }

        // cookMethod가 빈 문자열인지 확인
        if (cookMethod === '' || cookMethod === undefined) {
            alert('요리 설명을 입력하세요.');
            $(`#cookings${cookingIndex}\\.cookMethod`).focus();
            isValid = false;
            return false; // 반복문 종료
        }
    });

    return isValid;
}

});
