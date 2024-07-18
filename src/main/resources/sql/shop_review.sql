-- 상품 상세보기 페이지 리뷰 테이블입니다.
Drop Table If Exists shop_review;
Create Table If Not Exists shop_review (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    id VARCHAR(255),
    product_id INT,
    review_comment TEXT,
    rating double,
    reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id) REFERENCES membership(id),
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);


commit;
select * from shop_review where product_id =2;

drop table membership;
drop table product;
