create database spring;
use spring;

-- 회원가입
DROP TABLE IF EXISTS membership;

CREATE TABLE IF NOT EXISTS membership(
	id varchar(200) PRIMARY KEY,
	name varchar(100) not null,
    profile varchar(100),
    birthdate varchar(100) not null,
    pass varchar(200) not null,
    email varchar(300) not null,
    mobile varchar(120) not null,
    zipcode varchar(200) not null,
    address1 varchar(300) not null,
    emailGet varchar(1) not null,
    gerdonalGet varchar(1) not null
);

commit;

select * from membership;

INSERT INTO membership (id, name, profile, birthdate, pass, email, mobile, zipcode, address1, emailGet, gerdonalGet)
VALUES
('admin', '홍길동', '프로필1', '1990-01-01', '1234', 'user1@example.com', '010-1234-5678', '12345', '서울시 강남구', 'Y', 'N'),
('user2', '김철수', '프로필2', '1985-05-15', 'password2', 'user2@example.com', '010-2345-6789', '54321', '서울시 서초구', 'N', 'Y'),
('user3', '이영희', '프로필3', '1988-09-30', 'password3', 'user3@example.com', '010-3456-7890', '67890', '서울시 종로구', 'Y', 'Y');
