import { formatPrice } from './shopModule.js';

$(function() {
	$('.price').each(function() {
		const value = parseInt($(this).text().replace(/[^0-9.-]+/g, ""), 10);
		$(this).text(formatPrice(value));
	});

	// 새로고침시 orderBox 가격 표시
	updateOrderBox();

	// 마이너스 버튼 누를시 동작
	$('.order-quantity-input-minus').click(function() {
		let quantityInput = $(this).siblings('.order-quantity-input');
		let currentQuantity = parseInt(quantityInput.val(), 10);
		if (currentQuantity > 1) {
			let newQuantity = currentQuantity - 1;
			quantityInput.val(newQuantity);

			let productBox = $(this).closest('.cartBox');
			let productId = productBox.data('product-id');
			console.log(`Minus button clicked: Product ID = ${productId}, New Quantity = ${newQuantity}`);

			updateCart(productId, newQuantity);
			updateTotalPrice(quantityInput);
		}
	});

	// 플러스 버튼 누를시 동작 함수
	$('.order-quantity-input-plus').click(function() {
		let quantityInput = $(this).siblings('.order-quantity-input');
		let currentQuantity = parseInt(quantityInput.val(), 10);
		let newQuantity = currentQuantity + 1;
		quantityInput.val(newQuantity);

		let productBox = $(this).closest('.cartBox');
		let productId = productBox.data('product-id');
		console.log(`Plus button clicked: Product ID = ${productId}, New Quantity = ${newQuantity}`);

		updateCart(productId, newQuantity);
		updateTotalPrice(quantityInput);
	});

	// 수량 0이하 금지 및 1일 경우 -버튼 disabled
	$('.order-quantity-input').on('input', function() {
		let quantity = parseInt($(this).val(), 10);
		if (quantity < 1) {
			$(this).val(1);
			quantity = 1;
		}

		let productBox = $(this).closest('.cartBox');
		let productId = productBox.data('product-id');
		console.log(`Quantity input changed: Product ID = ${productId}, New Quantity = ${quantity}`);

		updateCart(productId, quantity);
		updateTotalPrice($(this));

		// 수량이 1일 경우 마이너스 버튼 비활성화
		if (quantity === 1) {
			$(this).siblings('.order-quantity-input-minus').prop('disabled', true);
		} else {
			$(this).siblings('.order-quantity-input-minus').prop('disabled', false);
		}
	});

	// 삭제 아이콘 클릭 시 동작
	$('.deleteCart').click(function() {
		let productBox = $(this).closest('.cartBox');
		let productId = productBox.data('product-id');
		console.log(`Delete button clicked: Product ID = ${productId}`);

		deleteCart(productId, productBox);
	});

	// input의 수량이 변동될때 마다 가격 변경 시키는 함수
	function updateTotalPrice(quantityInput) {
		let productBox = quantityInput.closest('.cartBox');
		let price = parseInt(productBox.find('.price').data('price'), 10);
		let quantity = parseInt(quantityInput.val(), 10);
		let totalPrice = price * quantity;
		productBox.find('.totalPrice').text(formatPrice(totalPrice)).data('price', totalPrice);

		updateOrderBox();
	}

	// 위의 가격 변동함수가 실행될때 "orderBox(우측 결제 박스)"부분의 가격이 변동되는 함수
	function updateOrderBox() {
		let totalProductPrice = 0;

		$('.cartBox').each(function() {
			let totalPrice = parseInt($(this).find('.totalPrice').data('price'), 10);
			totalProductPrice += totalPrice;
		});

		$('#totalProductPrice').text(formatPrice(totalProductPrice)).data('price', totalProductPrice);
		$('#totalOrderPrice').text(formatPrice(totalProductPrice)).data('price', totalProductPrice);
	}

	// 수량 변동시 변동된 수량에 대해 ajax처리
	function updateCart(productId, newQuantity) {
		$.ajax({
			url: '/updateCart',
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({ productId: productId, quantity: newQuantity }),
			success: function(resData) {
				console.log('Cart updated successfully', resData);
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
				console.log('Item deleted successfully', resData);
				productBox.remove();
				updateOrderBox();
			},
			error: function(error) {
				console.error('Error deleting item', error);
			}
		});
	}

	// 초기값 포맷팅 함수
	function formatAllPrices() {
		$('.price').each(function() {
			const value = parseInt($(this).text().replace(/[^\d]/g, ''), 10);
			$(this).text(formatPrice(value)).data('price', value);
		});
		$('.totalPrice').each(function() {
			const value = parseInt($(this).text().replace(/[^\d]/g, ''), 10);
			$(this).text(formatPrice(value)).data('price', value);
		});
	}

	formatAllPrices();
});
