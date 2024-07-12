import { formatPrice } from './shopUtils.js';

$(function() {
    let detailQuantity = 1;
    const detailPrice = parseInt($("#productPrice").data("price"));


    // 상품 수량 +- 제어
    function updateDetailQuantityDisplay() {
        $(".detail-quantity-input").val(detailQuantity);
        $(".detail-quantity-input-minus").prop('disabled', detailQuantity == 1);
        $("#detail-totalPrice").text(formatPrice(detailPrice * detailQuantity));
        $("#detail-productPrice").text(formatPrice(detailPrice));
    }

    $(".detail-quantity-input-minus").click(function() {
        if (detailQuantity > 1) {
            detailQuantity -= 1;
            updateDetailQuantityDisplay();
        }
    });

    $(".detail-quantity-input-plus").click(function() {
        detailQuantity += 1;
        updateDetailQuantityDisplay();
    });

    $(".detail-resetQuantity").click(function() {
        detailQuantity = 1;
        updateDetailQuantityDisplay();
    });

    // 수량 0이하 금지 및 1일 경우 -버튼 disabled
    $(".quantity-input").on('input', function() {
        let newQuantity = parseInt($(this).val());
        if (newQuantity < 1) {
            $(this).val(1);
            newQuantity = 1;
        }
        detailQuantity = newQuantity;
        updateDetailQuantityDisplay();
    });

    function updateCartQuantity() {
        $('#cartQuantity').val(detailQuantity);
    }

    $('#addCart').click(function() {
        updateCartQuantity();
        $('#productForm').attr('action', '/addCart').submit();
    });

    $('#orderNow').click(function() {
        updateCartQuantity();
        $('#productForm').attr('action', '/orderNow').submit();
    });

    updateDetailQuantityDisplay();
});
