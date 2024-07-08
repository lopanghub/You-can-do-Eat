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
					let imageUrl = product.productImage ? product.productImage : 'http://via.placeholder.com/300x250';
					let productBox = `
						<div class="col-4 productBox">
							<a href="/shopDetail?id=${product.productId}">
								<img src="${imageUrl}" alt="${product.ingredient}" class="img-fluid">
								<div>${product.ingredient}</div>
								<div>${product.seller} & ${product.origin}</div>
								<div>${formatPrice(product.price)}원</div>
							</a>
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
	$('.category').click(function() {
		var category = $(this).data('category');
		loadCategory(category);
	});
});
