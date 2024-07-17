// shopUtils.js
export function formatPrice(value) {
    // 천 단위 구분 기호와 "원"을 추가하는 형식으로 변환
    return value.toLocaleString('ko-KR') + ' 원';
}
