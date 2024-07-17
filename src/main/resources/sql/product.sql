-- 데이터베이스 사용
USE spring;

-- 기존 테이블 삭제
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS shop_review;
DROP TABLE IF EXISTS cart;

-- 기존테이블 삭제시 위에 종속 테이블도 지우셔야 합니다 
-- 안전모드 비활성화 데이터 변경 안전모드 활성화 커밋 해주셔야지 main에서 정상작동 됩니다.

-- 새로운 단일 테이블 생성product
CREATE TABLE IF NOT EXISTS product (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    price INT NOT NULL,
    product_name VARCHAR(1000),
    product_image VARCHAR(255),
    size VARCHAR(255),
    rating double,   
    category VARCHAR(255) NOT NULL,
    ingredient VARCHAR(255) NOT NULL,
    expiration_date DATE,
    detail_image VARCHAR(255)
);

select * from product;

-- 안전 모드 비활성화
SET SQL_SAFE_UPDATES = 0;

UPDATE product
SET category = CASE 
    WHEN category = '정육/계란' THEN '정육'
    WHEN category = '장/양념/소스' THEN '조미료'
    ELSE category
END
WHERE category IN ('정육/계란', '장/양념/소스');

-- 안전 모드 다시 활성화
SET SQL_SAFE_UPDATES = 1;

commit;
select * from product;

