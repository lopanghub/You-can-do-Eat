$(function() {
	function formatPrice(price) {
	        return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    }
		
	function loadCategory(category) {
		$.ajax({
			url: '/products/' + category,
			method: 'GET',
			success: function(resData) {
				console.log(resData);
				$('#productGrid').empty();

				$.each(resData, function(i, product) {
					let imageUrl = product.product_image ? product.product_image : 'http://via.placeholder.com/300x250';
					let productBox = `
						<div class="col-4 productBox">
							<img src="${imageUrl}" alt="${product.ingredient}" class="img-fluid">
							<div>${product.ingredient}</div>
							<div>${product.seller} & ${product.origin}</div>
							<div>${formatPrice(product.price)}원</div>
						</div>
					`;
					$('#productGrid').append(productBox);	
				});
			},
			error: function(error) {
				console.error(error);
			}
		});
	}

	// 초기 카테고리를 로드할 때 사용
	loadCategory('fruits');

	// 카테고리 버튼 클릭 시 해당 카테고리 로드
	$('.btn').click(function() {
		var category = $(this).data('category');
		loadCategory(category);
	});
});
