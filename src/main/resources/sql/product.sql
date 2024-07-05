-- 데이터베이스 생성

USE spring;

-- 카테고리 테이블 생성
DROP TABLE IF EXISTS category;
CREATE TABLE IF NOT EXISTS category(
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL,
    ingredient VARCHAR(255) NOT NULL
);

-- 상품 테이블 생성
DROP TABLE IF EXISTS product;
CREATE TABLE IF NOT EXISTS product(
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    price INT NOT NULL,
    product_image VARCHAR(255),
    seller VARCHAR(255),
    origin VARCHAR(255),
    size VARCHAR(255),
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES Category(category_id)
);

-- 카테고리 데이터 삽입
INSERT INTO Category (category_name, ingredient) VALUES 
('Fruits', 'Apple'),
('Fruits', 'Banana'),
('Fruits', 'Orange'),
('Vegetables', 'Cabbage'),
('Vegetables', 'Carrot'),
('Vegetables', 'Tomato'),
('Meat', 'Beef'),
('Meat', 'Lamb'),
('Meat', 'Pork'),
('Seafood', 'Salmon'),
('Seafood', 'Cod'),
('Seafood', 'Shrimp');

-- 상품 데이터 삽입
INSERT INTO Product (price, product_image, seller, origin, size, category_id) VALUES
-- Fruits Category
(10000, 'image1.jpg', 'Seller A', 'USA', 'Large', 1),
(12000, 'image2.jpg', 'Seller B', 'Korea', 'Medium', 1),
(15000, 'image3.jpg', 'Seller C', 'China', 'Small', 1),
(11000, 'image4.jpg', 'Seller D', 'USA', 'Large', 2),
(13000, 'image5.jpg', 'Seller E', 'Korea', 'Medium', 2),
(16000, 'image6.jpg', 'Seller F', 'China', 'Small', 2),
-- Vegetables Category
(20000, 'image7.jpg', 'Seller G', 'USA', 'Large', 4),
(18000, 'image8.jpg', 'Seller H', 'Korea', 'Medium', 4),
(22000, 'image9.jpg', 'Seller I', 'China', 'Small', 4),
(21000, 'image10.jpg', 'Seller J', 'USA', 'Large', 5),
(19000, 'image11.jpg', 'Seller K', 'Korea', 'Medium', 5),
(23000, 'image12.jpg', 'Seller L', 'China', 'Small', 5),
-- Meat Category
(25000, 'image13.jpg', 'Seller M', 'USA', 'Large', 7),
(26000, 'image14.jpg', 'Seller N', 'Korea', 'Medium', 7),
(27000, 'image15.jpg', 'Seller O', 'China', 'Small', 7),
(28000, 'image16.jpg', 'Seller P', 'USA', 'Large', 8),
(29000, 'image17.jpg', 'Seller Q', 'Korea', 'Medium', 8),
(30000, 'image18.jpg', 'Seller R', 'China', 'Small', 8),
-- Seafood Category
(31000, 'image19.jpg', 'Seller S', 'USA', 'Large', 10),
(32000, 'image20.jpg', 'Seller T', 'Korea', 'Medium', 10),
(33000, 'image21.jpg', 'Seller U', 'China', 'Small', 10),
(34000, 'image22.jpg', 'Seller V', 'USA', 'Large', 11),
(35000, 'image23.jpg', 'Seller W', 'Korea', 'Medium', 11),
(36000, 'image24.jpg', 'Seller X', 'China', 'Small', 11);

USE spring;

-- 새로운 Product 테이블 생성
CREATE TABLE NewProduct (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    price INT NOT NULL,
    product_image VARCHAR(255),
    seller VARCHAR(255),
    origin VARCHAR(255),
    size VARCHAR(255),
    category_name VARCHAR(255) NOT NULL,
    ingredient VARCHAR(255) NOT NULL,
    expiration_date DATE
);

-- 데이터 삽입
INSERT INTO NewProduct (product_id, price, product_image, seller, origin, size, category_name, ingredient)
SELECT p.product_id, p.price, p.product_image, p.seller, p.origin, p.size, c.category_name, c.ingredient
FROM Product p
JOIN Category c ON p.category_id = c.category_id;

-- 기존 테이블 삭제
DROP TABLE Product;
DROP TABLE Category;

-- 새 테이블 이름 변경
ALTER TABLE NewProduct RENAME TO Product;
ALTER TABLE Product CHANGE COLUMN category_name category VARCHAR(255) NOT NULL;

-- expiration_date 열에 임의의 소비기한 추가 (예: 2025-12-31)
UPDATE Product SET expiration_date = '2025-12-31';


