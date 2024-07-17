// shopModule.js
import { formatPrice } from './shopUtils.js';

export function updateOrderBox() {
    let totalProductPrice = 0;

    $('.cartBox').each(function() {
        let totalPrice = parseInt($(this).find('.totalPrice').data('price'), 10);
        if (isNaN(totalPrice)) {
            totalPrice = 0;
        }
        console.log(`Total Price: ${totalPrice}`); // 디버깅 코드 추가
        totalProductPrice += totalPrice;
    });

    console.log(`Total Product Price: ${totalProductPrice}`); // 디버깅 코드 추가

    const formattedTotalProductPrice = formatPrice(totalProductPrice);
    $('#totalProductPrice').text(formattedTotalProductPrice).data('price', totalProductPrice);
    $('#totalOrderPrice').text(formattedTotalProductPrice).data('price', totalProductPrice);
}

export function updateTotalPrice(quantityInput) {
    let productBox = quantityInput.closest('.cartBox');
    let price = parseInt(productBox.find('.price').data('price'), 10);
    let quantity = parseInt(quantityInput.val(), 10);

    if (isNaN(price) || isNaN(quantity)) {
        price = 0;
        quantity = 0;
    }

    let totalPrice = price * quantity;
    const formattedTotalPrice = formatPrice(totalPrice);

    productBox.find('.totalPrice').text(formattedTotalPrice).data('price', totalPrice);

    updateOrderBox();
}

export function updateCart(productId, newQuantity) {
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

export function deleteCart(productId, productBox) {
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

export function formatAllPrices() {
    $('.price').each(function() {
        const value = parseInt($(this).text().replace(/[^\d]/g, ''), 10);
        const formattedValue = formatPrice(value);
        $(this).text(formattedValue).data('price', value);
    });
    $('.totalPrice').each(function() {
        const value = parseInt($(this).text().replace(/[^\d]/g, ''), 10);
        const formattedValue = formatPrice(value);
        $(this).text(formattedValue).data('price', value);
    });
}
