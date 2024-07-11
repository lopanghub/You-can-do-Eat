// 1,000원 처럼 천단위 콤마
// shopModule.js
export function formatPrice(value) {
    return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + ' 원';
}
