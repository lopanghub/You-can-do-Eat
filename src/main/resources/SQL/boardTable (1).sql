CREATE DATABASE spring;
use spring;


CREATE TABLE recipe_Board (  -- 레시피 보드
    board_No INT  AUTO_INCREMENT  PRIMARY KEY,
    food_name VARCHAR(200) NOT NULL,
    board_title VARCHAR(200) NOT NULL,
    board_content VARCHAR(2000) NOT NULL,
    food_genre VARCHAR(100) NOT NULL,
    board_commend INT DEFAULT 0,
    board_view INT DEFAULT 0,
    reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    thumbnail VARCHAR(200),
    food_time INT DEFAULT 0,
    numberEaters  int DEFAULT 0,
    Apoint INT DEFAULT 0,
    member_id VARCHAR(200) NOT NULL
);

CREATE TABLE material (  -- 재료 리스트 테이블
    material_id INT AUTO_INCREMENT PRIMARY KEY,
    board_No INT,
    material_name VARCHAR(200) NOT NULL,-- 재료이름
    mensuration VARCHAR(200) NOT NULL,-- 재료량
   type_Material VARCHAR(200) DEFAULT '소스',-- 요리재료의　종류
    FOREIGN KEY (board_No) REFERENCES recipe_Board(board_No)
);

CREATE TABLE cooking (  -- 레이피 리스트 테이블 
    cooking_id INT AUTO_INCREMENT PRIMARY KEY,
    board_No INT,
    cook_title VARCHAR(200) NOT NULL,	-- 레시피제복
    cook_Method VARCHAR(2000) NOT NULL,-- 레시피내용
    recommended VARCHAR(2000),-- 주의사항
    cook_file VARCHAR(500) ,
    FOREIGN KEY (board_No) REFERENCES recipe_Board(board_No)
);

CREATE TABLE cook_material ( -- 요리에 재료테이블
	cooking_id INT,
	material_id INT,
	board_No INT,
    FOREIGN KEY (board_No) REFERENCES recipe_Board(board_No),
    FOREIGN KEY (board_No) REFERENCES material(material_id),
    FOREIGN KEY (board_No) REFERENCES cooking(cooking_id)
);



INSERT INTO recipe_Board (food_name, board_title, board_content, food_genre, member_id)
VALUES ('맛있는 파스타', '스파게티 카르보나라', '스파게티 카르보나라 레시피 내용이 여기 있습니다.', '이탈리안', 'john_doe');

INSERT INTO material (board_No, material_name, mensuration, type_Material)
VALUES 
(1, '스파게티', '200g', '면류'),
(1, '베이컨', '100g', '육류'),
(1, '크림', '100ml', '조미료');



INSERT INTO cooking (board_No, cook_title, cook_Method, recommended)
VALUES (1, '스파게티 면 삶기', 
        '1. 끓는 물에 소금을 넣고 스파게티 면을 넣습니다.',' 뜨거우니 조심');
INSERT INTO cooking (board_No, cook_title, cook_Method, recommended)
VALUES (1, '토마토 소스 만들기', 
        '2. 팬에 올리브 오일을 데우고 다진 마늘을 볶습니다.\n2. 다진 토마토와 토마토 페이스트를 넣고 볶습니다.','약불로');
INSERT INTO cooking (board_No, cook_title, cook_Method, recommended)
VALUES (1, '스파게티 조리', 
        '3.삶은 스파게티를 체에 걸러 물기를 뺀 후, 토마토 소스에 넣고 잘 섞어 줍니다.','3분만');

Insert Into cook_material values(1,1,1);

SELECT m.*
FROM cook_material cm
INNER JOIN material m ON cm.material_id = m.material_id where cooking_id=1 and cm.board_No=1;


SELECT m.*
FROM recipe_board rb
INNER JOIN material m ON rb.board_no = m.board_no
WHERE rb.board_no = 1;

SELECT count(*)
FROM recipe_board rb
INNER JOIN cooking c ON rb.board_no = c.board_no
WHERE rb.board_no = 1;


drop table material;
drop table cooking;
drop table recipe_Board;
drop table cook_material;



