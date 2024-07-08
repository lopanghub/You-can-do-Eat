$(function() {
	let quantity = 1;
	const price = parseInt($("#productPrice").data("price"));

	function formatPrice(value) {
		return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + ' 원';
	}

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
});
