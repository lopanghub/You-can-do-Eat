import { formatPrice } from './shopModule.js';

$(function() {
    $('.price').each(function() {
        const value = parseInt($(this).text().replace(/[^0-9.-]+/g, ""), 10);
        $(this).text(formatPrice(value));
    });
});
