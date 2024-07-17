// shopOrder.js
import { updateOrderBox, formatAllPrices, deleteCart } from './shopModule.js';
import { initializeQuantityHandlers } from './quantityModule.js';

$(document).ready(function() {
    // 새로고침시 orderBox 가격 표시
    updateOrderBox();

    // 수량 관련 이벤트 핸들러 초기화
    initializeQuantityHandlers();

    // 삭제 아이콘 클릭 시 동작
    $('.deleteCart').click(function() {
        let productBox = $(this).closest('.cartBox');
        let productId = productBox.data('product-id');
        console.log(`Delete button clicked: Product ID = ${productId}`);

        deleteCart(productId, productBox);
    });

    // 초기값 포맷팅 함수
    formatAllPrices();
	
});
