use spring;
-- 게시글 번호, 제목, 이메일, 내용, 글쓴이, 날짜, 조회수, 비밀번호, 파일정보,
-- no, title, email, content, writer, reg_date, read_count, pass, file
DROP TABLE IF EXISTS community;
CREATE TABLE IF NOT EXISTS community(
  no INTEGER AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(50) NOT NULL,
  writer VARCHAR(10) NOT NULL,  
  content VARCHAR(1000) NOT NULL,
  reg_date TIMESTAMP NOT NULL,
  read_count INTEGER(5) NOT NULL,
  file1 VARCHAR(100)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO community (title, writer, content, reg_date, read_count, file1) 
VALUES 
('첫 번째 글', 'writer1', '이 글은 첫 번째 글입니다. 내용은 간단하지만 의미 있는 글을 썼습니다.', '2024-07-16 10:00:00', 25, 'file1.pdf'),
('두 번째 글', 'writer2', '두 번째 글의 내용입니다. 여기에는 더 많은 정보와 사진이 포함되어 있습니다.', '2024-07-16 11:30:00', 18, 'file2.jpg'),
('세 번째 글', 'writer3', '세 번째 글은 긴 글입니다. 많은 사람들이 읽기를 원할 것입니다.', '2024-07-16 13:45:00', 12, NULL);



SELECT * FROM community ORDER BY no DESC;

