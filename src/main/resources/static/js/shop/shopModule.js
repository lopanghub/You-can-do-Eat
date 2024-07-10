// shopModule - cart와 order에서 같이 쓰기 위해 모듈화
// 1,000원 처럼 천단위 콤마
export function formatPrice(value) {
    return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + ' 원';
}

// 상품 수량 제어시 표시 함수
export function quantityDisplay(quantity, price, quantityInput, totalPrice) {
    quantityInput.val(quantity);
    quantityInput.siblings('.quantity-input-minus').prop('disabled', quantity == 1);
    totalPrice.text(formatPrice(price * quantity));
}

// 상품수량 제어(1 이하로 안떨어지게)
export function preventLessOneQuantity(quantityInput, price, totalPrice) {
    quantityInput.on('input', function () {
        let quantity = parseInt($(this).val());
        if (quantity < 1) {
            $(this).val(1);
            quantity = 1;
        }
        quantityDisplay(quantity, price, quantityInput, totalPrice);
    });
}

// 수량 증가 및 감소
export function updateQuantity(minusBtn, plusBtn, quantityInput, price, totalPrice, productId) {
    minusBtn.click(function () {
        let quantity = parseInt(quantityInput.val());
        if (quantity > 1) {
            quantity -= 1;
            quantityDisplay(quantity, price, quantityInput, totalPrice);
            updateQuantityAjax(productId, quantity);
        }
    });

    plusBtn.click(function () {
        let quantity = parseInt(quantityInput.val());
        quantity += 1;
        quantityDisplay(quantity, price, quantityInput, totalPrice);
        updateQuantityAjax(productId, quantity);
    });
}

// 삭제버튼 동작
export function deleteCart(deleteCart, cartBox, productId) {
    deleteCart.click(function(){
		
		deleteCartAjax(productId, cartBox);
	});
	
}

// Ajax 요청으로 장바구니 수량 업데이트
export function updateQuantityAjax(productId, quantity) {
    $.ajax({
        url: '/updateCart',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ productId: productId, quantity: quantity }),
        success: function (resData) {
            console.log('Cart updated successfully');
            updateOrderBox();  // 수량 업데이트 후 orderBox 업데이트
        },
        error: function (error) {
            console.error('Error updating cart', error);
        }
    });
}

// ajax 요청으로 삭제 처리하는 함수
export function deleteCartAjax(productId, cartBox) {
    $.ajax({
        url: '/deleteCart',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ productId: productId }),
        success: function (resData) {
            console.log('Item deleted successfully');
            cartBox.remove();
            updateOrderBox();  // 항목 삭제 후 orderBox 업데이트
        },
        error: function (error) {
            console.error('Error deleting item', error);
        }
    });
}

// 우측 결제 박스 업데이트 함수 (예시)
export function updateOrderBox() {
    let totalProductPrice = 0;

    $('.cartBox').each(function () {
        let totalPrice = parseInt($(this).find('.totalPrice').text().replace(/[^0-9.-]+/g, ""));
        totalProductPrice += totalPrice;
    });

    $('#totalProductPrice').text(formatPrice(totalProductPrice));
    $('#totalOrderPrice').text(formatPrice(totalProductPrice));
}
