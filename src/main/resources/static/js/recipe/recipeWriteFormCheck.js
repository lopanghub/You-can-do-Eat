$(document).ready(function() {
    $('#recipeWriteForm').submit(function(event) {
        // 음식 이름 유효성 검사
        if ($('#foodName').val().trim() === '') {
            alert('음식 이름을 입력해주세요.');
            event.preventDefault(); // 폼 제출 중지
            return false;
        }

        // 레시피 제목 유효성 검사
        if ($('#boardTitle').val().trim() === '') {
            alert('레시피 제목을 입력해주세요.');
            event.preventDefault();
            return false;
        }

        // 조리 과정 최소 하나 요구
        if ($('.cookingSection').length < 1) {
            alert('최소 하나의 조리 과정을 추가해야 합니다.');
            event.preventDefault();
            return false;
        }

        // 각 조리 과정의 필수 입력 필드 검사
        var allSectionsValid = true;
        $('.cookingSection').each(function(index) {
            var cookTitle = $(this).find('input[name="cookTitles"]').val().trim();
            var cookMethod = $(this).find('input[name="cookMethods"]').val().trim();
            if (!cookTitle || !cookMethod) {
                alert(`조리 과정 ${index + 1}의 제목과 과정을 모두 입력해주세요.`);
                allSectionsValid = false;
                event.preventDefault();
                return false; // break out of each loop
            }
        });

        if (!allSectionsValid) {
            return false;
        }

        // 모든 재료의 이름과 양 검사
        var allMaterialsValid = true;
        $('.materialRow').each(function(index) {
            var materialName = $(this).find('input[name$="].materialName"]').val().trim();
            var mensuration = $(this).find('input[name$="].mensuration"]').val().trim();
            if (!materialName || !mensuration) {
                alert(`재료 ${index + 1}의 이름과 양을 입력해주세요.`);
                allMaterialsValid = false;
                event.preventDefault();
                return false; // break out of each loop
            }
        });

        if (!allMaterialsValid) {
            return false;
        }
    });
});
