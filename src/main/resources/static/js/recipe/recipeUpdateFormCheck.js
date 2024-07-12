$(document).ready(function() {
    $('#recipeUpdateForm').submit(function(event) {
        let isValid = true;

        // 필수 입력 필드 검사
        $('#foodName, #boardTitle, #boardContent, #foodGenre, #thumbnailname').each(function() {
            if ($(this).val().trim() === '') {
                alert($(this).prev('label').text() + '를(을) 입력해주세요.');
                $(this).focus();
                isValid = false;
                return false; // break the loop
            }
        });

        if (!isValid) {
            event.preventDefault(); // 폼 제출 중단
            return;
        }

        // 요리 섹션 최소 한 개 이상 검사
        if ($('.cookingSection').length === 0) {
            alert('최소 한 개의 요리 섹션이 필요합니다.');
            isValid = false;
        } else {
            // 각 요리 섹션의 재료 검사
            $('.cookingSection').each(function() {
                const cookingIndex = $(this).attr('id').replace('cooking', '');
                if ($(`#materials${cookingIndex} .row.mt-2`).length === 0) {
                    alert('각 요리 섹션에 최소 한 개의 재료가 필요합니다.');
                    $(`#materials${cookingIndex} .btn.btn-primary.addMaterial`).focus();
                    isValid = false;
                    return false; // break the loop
                }
            });
        }

        // 각 재료 필드 비어 있는 경우 기본값 설정
        $('.cookingSection').each(function() {
            $(this).find('.row.mt-2').each(function() {
                $(this).find('input[type="text"], select').each(function() {
                    if ($(this).val().trim() === '') {
                        $(this).val('');
                    }
                });
            });
        });

        if (!isValid) {
            event.preventDefault(); // 폼 제출 중단
        }
    });
});
