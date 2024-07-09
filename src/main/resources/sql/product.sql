-- 데이터베이스 사용
USE spring;

-- 기존 테이블 삭제
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS category;

-- 새로운 단일 테이블 생성product
CREATE TABLE IF NOT EXISTS product (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    price INT NOT NULL,
    product_image VARCHAR(255),
    seller VARCHAR(255),
    origin VARCHAR(255),
    size VARCHAR(255),
    category VARCHAR(255) NOT NULL,
    ingredient VARCHAR(255) NOT NULL,
    expiration_date DATE
);

-- 데이터 삽입
INSERT INTO product (price, product_image, seller, origin, size, category, ingredient, expiration_date) VALUES
-- Fruits Category
(10000, 'image1.jpg', 'Seller A', 'USA', 'Large', 'Fruits', 'Apple', '2025-12-31'),
(12000, 'image2.jpg', 'Seller B', 'Korea', 'Medium', 'Fruits', 'Banana', '2025-12-31'),
(15000, 'image3.jpg', 'Seller C', 'China', 'Small', 'Fruits', 'Orange', '2025-12-31'),
(11000, 'image4.jpg', 'Seller D', 'USA', 'Large', 'Fruits', 'Apple', '2025-12-31'),
(13000, 'image5.jpg', 'Seller E', 'Korea', 'Medium', 'Fruits', 'Banana', '2025-12-31'),
(16000, 'image6.jpg', 'Seller F', 'China', 'Small', 'Fruits', 'Orange', '2025-12-31'),
-- Vegetables Category
(20000, 'image7.jpg', 'Seller G', 'USA', 'Large', 'Vegetables', 'Cabbage', '2025-12-31'),
(18000, 'image8.jpg', 'Seller H', 'Korea', 'Medium', 'Vegetables', 'Carrot', '2025-12-31'),
(22000, 'image9.jpg', 'Seller I', 'China', 'Small', 'Vegetables', 'Tomato', '2025-12-31'),
(21000, 'image10.jpg', 'Seller J', 'USA', 'Large', 'Vegetables', 'Cabbage', '2025-12-31'),
(19000, 'image11.jpg', 'Seller K', 'Korea', 'Medium', 'Vegetables', 'Carrot', '2025-12-31'),
(23000, 'image12.jpg', 'Seller L', 'China', 'Small', 'Vegetables', 'Tomato', '2025-12-31'),
-- Meat Category
(25000, 'image13.jpg', 'Seller M', 'USA', 'Large', 'Meat', 'Beef', '2025-12-31'),
(26000, 'image14.jpg', 'Seller N', 'Korea', 'Medium', 'Meat', 'Lamb', '2025-12-31'),
(27000, 'image15.jpg', 'Seller O', 'China', 'Small', 'Meat', 'Pork', '2025-12-31'),
(28000, 'image16.jpg', 'Seller P', 'USA', 'Large', 'Meat', 'Beef', '2025-12-31'),
(29000, 'image17.jpg', 'Seller Q', 'Korea', 'Medium', 'Meat', 'Lamb', '2025-12-31'),
(30000, 'image18.jpg', 'Seller R', 'China', 'Small', 'Meat', 'Pork', '2025-12-31'),
-- Seafood Category
(31000, 'image19.jpg', 'Seller S', 'USA', 'Large', 'Seafood', 'Salmon', '2025-12-31'),
(32000, 'image20.jpg', 'Seller T', 'Korea', 'Medium', 'Seafood', 'Cod', '2025-12-31'),
(33000, 'image21.jpg', 'Seller U', 'China', 'Small', 'Seafood', 'Shrimp', '2025-12-31'),
(34000, 'image22.jpg', 'Seller V', 'USA', 'Large', 'Seafood', 'Salmon', '2025-12-31'),
(35000, 'image23.jpg', 'Seller W', 'Korea', 'Medium', 'Seafood', 'Cod', '2025-12-31'),
(36000, 'image24.jpg', 'Seller X', 'China', 'Small', 'Seafood', 'Shrimp', '2025-12-31');

-- 안전 모드 비활성화
SET SQL_SAFE_UPDATES = 0;

-- 쿼리 실행
UPDATE product
SET product_image = NULL;

-- 안전 모드 다시 활성화
SET SQL_SAFE_UPDATES = 1;

commit;
select * from product;

