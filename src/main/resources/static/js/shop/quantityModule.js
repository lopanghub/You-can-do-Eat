// quantityModule.js
import { updateCart, updateTotalPrice } from './shopModule.js';

export function handleQuantityMinusButtonClick() {
    $('.quantity-input-minus').off('click').on('click', function() {
        let quantityInput = $(this).siblings('.quantity-input');
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
}

export function handleQuantityPlusButtonClick() {
    $('.quantity-input-plus').off('click').on('click', function() {
        let quantityInput = $(this).siblings('.quantity-input');
        let currentQuantity = parseInt(quantityInput.val(), 10);
        let newQuantity = currentQuantity + 1;
        quantityInput.val(newQuantity);

        let productBox = $(this).closest('.cartBox');
        let productId = productBox.data('product-id');
        console.log(`Plus button clicked: Product ID = ${productId}, New Quantity = ${newQuantity}`);

        updateCart(productId, newQuantity);
        updateTotalPrice(quantityInput);
    });
}

export function handleQuantityInputChange() {
    $('.quantity-input').off('input').on('input', function() {
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
            $(this).siblings('.quantity-input-minus').prop('disabled', true);
        } else {
            $(this).siblings('.quantity-input-minus').prop('disabled', false);
        }
    });
}

export function initializeQuantityHandlers() {
    handleQuantityMinusButtonClick();
    handleQuantityPlusButtonClick();
    handleQuantityInputChange();
}
