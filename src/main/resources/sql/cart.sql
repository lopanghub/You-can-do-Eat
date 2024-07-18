use spring;

Drop Table If Exists cart;
DROP Table if EXISTS orders;
drop table if EXISTS order_items;

Create Table If Not Exists cart(
	cart_id INT AUTO_INCREMENT PRIMARY KEY,
    quantity INT not Null,
    product_id Int not null,
    Foreign Key(product_id) 
    References product(product_id)
    );

-- orders 테이블 생성
CREATE TABLE orders (
    order_id VARCHAR(255) PRIMARY KEY,
    member_id VARCHAR(255) NOT NULL,
    total_amount INT NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- order_items 테이블 생성
CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id VARCHAR(255) NOT NULL,
    product_id INT NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    product_image VARCHAR(1000),
    price INT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
);


commit;
select * from orders;
select * from order_items;