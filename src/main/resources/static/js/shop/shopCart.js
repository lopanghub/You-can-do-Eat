$(document).ready(function() {
	//새로고침시 orderBox 가격 표시
	updateOrderBox();

	// 마이너스 버튼 누를시 동작
	$('.quantity-input-minus').click(function() {
		let quantityInput = $(this).siblings('.quantity-input');
		let currentQuantity = parseInt(quantityInput.val());
		if (currentQuantity > 1) {
			let newQuantity = currentQuantity - 1;
			quantityInput.val(newQuantity);

			let productId = $(this).closest('.productBox').data('product-id');

			updateCart(productId, newQuantity);
			updateTotalPrice(quantityInput);
		}
	});

	// 플러스 버튼 누를시 동작 함수
	$('.quantity-input-plus').click(function() {
		let quantityInput = $(this).siblings('.quantity-input');
		let currentQuantity = parseInt(quantityInput.val());
		let newQuantity = currentQuantity + 1;
		quantityInput.val(newQuantity);

		let productId = $(this).closest('.productBox').data('product-id');

		updateCart(productId, newQuantity);
		updateTotalPrice(quantityInput);
	});

	// 수량 0이하 금지 및 1일 경우 -버튼 disabled
	$('.quantity-input').on('input', function() {
		let quantity = parseInt($(this).val());
		if (quantity < 1) {
			$(this).val(1);
			quantity = 1;
		}

		let productId = $(this).closest('.productBox').data('product-id');
		updateCart(productId, quantity);
		updateTotalPrice($(this));

		// 수량이 1일 경우 마이너스 버튼 비활성화
		if (quantity == 1) {
			$(this).siblings('.quantity-input-minus').prop('disabled', true);
		} else {
			$(this).siblings('.quantity-input-minus').prop('disabled', false);
		}
	});
	

	// 삭제 아이콘 클릭 시 동작
	$('.deleteCart').click(function() {
		let productBox = $(this).closest('.productBox');
		let productId = productBox.data('product-id');

		deleteCart(productId, productBox);
	});

	// input의 수량이 변동될때 마다 가격 변경 시키는 함수
	function updateTotalPrice(quantityInput) {
		let productBox = quantityInput.closest('.productBox');
		let price = parseFloat(productBox.find('.price').text());
		let quantity = parseInt(quantityInput.val());
		let totalPrice = price * quantity;
		productBox.find('.totalPrice').text(totalPrice);

		updateOrderBox();
	}

	// 위의 가격 변동함수가 실행될때 "orderBox(우측 결제 박스)"부분의 가격이 변동되는 함수
	function updateOrderBox() {
		let totalProductPrice = 0;

		$('.productBox').each(function() {
			let totalPrice = parseInt($(this).find('.totalPrice').text());
			totalProductPrice += totalPrice;
		});

		$('#totalProductPrice').text(totalProductPrice);
		$('#totalOrderPrice').text(totalProductPrice);
	}

	// 수량 변동시 변동된 수량에 대해 ajax처리
	function updateCart(productId, newQuantity) {
		$.ajax({
			url: '/updateCart',
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({ productId: productId, quantity: newQuantity }),
			success: function(resData) {
				console.log('Cart updated successfully');
			},
			error: function(error) {
				console.error('Error updating cart', error);
			}
		});
	}

	// 삭제 요청을 처리하는 함수
	function deleteCart(productId, productBox) {
		$.ajax({
			url: '/deleteCart',
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({ productId: productId }),
			success: function(resData) {
				console.log('Item deleted successfully');
				productBox.remove();
				updateOrderBox();
			},
			error: function(error) {
				console.error('Error deleting item', error);
			}
		});
	}
});
