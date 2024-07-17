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
				$('#productGrid').empty().addClass('row row-cols-1 row-cols-md-2 row-cols-lg-4 gx-3 gy-3');

				$.each(resData, function(i, product) {
					let productImage = product.productImage;
					let imageUrl;

					if (productImage && productImage.startsWith('http')) {
					    imageUrl = productImage;
					} else {
					    imageUrl = '/resources/shop/' + productImage;
					}


					let productBox = `
					<div class="col">
					     <div class="productBox">
							<a href="/shopDetail?productId=${product.productId}">
								<img src="${imageUrl}" alt="${product.productName}" class="img-fluid"
									onerror="this.src='http://via.placeholder.com/300x250';">
								<div>${product.productName}</div>
								<div>${product.seller} (${product.rating})</div>
								<div>${formatPrice(product.price)}원</div>
							</a>
						</div>
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
	

	// 카테고리 버튼 클릭 이벤트
    $('.category').click(function() {
        $('.category').removeClass('active');
        $(this).addClass('active');
        var category = $(this).data('category');
        loadCategory(category);
    });

	// 페이지 로드 시 첫 번째 카테고리 활성화 
    $('.category:first').click();




});
