$(function() {
	let quantity = 1;
	const price = parseInt($("#productPrice").data("price"));

	//1,000원 처럼 천단위 콤마
	function formatPrice(value) {
		return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + ' 원';
	}

	//상품 수량 +- 제어
	function updateQuantityDisplay() {
		$("#quantity").text(quantity);
		$("#quantityMinus").prop('disabled', quantity == 1);
		$("#totalPrice").text(formatPrice(price * quantity));
		$("#productPrice").text(formatPrice(price));
	}

	$("#quantityMinus").click(function() {
		if (quantity > 1) {
			quantity -= 1;
			updateQuantityDisplay();
		}
	});

	$("#quantityPlus").click(function() {
		quantity += 1;
		updateQuantityDisplay();
	});
	$(".resetQuantity").click(function() {
		quantity = 1;
		updateQuantityDisplay();
	});

	updateQuantityDisplay();
	
	//장바구니 및 결제 버튼 제어
	function updateCartQuantity() {
		$('#cartQuantity').val($('#quantity').text());
	}

	$('#addCart').click(function() {
		updateCartQuantity();
		$('#productForm').attr('action', '/addCart').submit();
	});

	$('#orderNow').click(function() {
		updateCartQuantity();
		$('#productForm').attr('action', '/orderNow').submit();
	});
});
