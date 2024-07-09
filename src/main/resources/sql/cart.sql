use spring;

Drop Table If Exists cart;
Create Table If Not Exists cart(
	cart_id INT AUTO_INCREMENT PRIMARY KEY,
    quantity INT not Null,
    product_id Int not null,
    Foreign Key(product_id) 
    References product(product_id)
    );

select * from cart;