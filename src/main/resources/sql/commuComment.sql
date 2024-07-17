use spring;

-- 댓글
CREATE TABLE commuComment (
    comment_id INT AUTO_INCREMENT PRIMARY KEY,
    no INT NOT NULL,
    Comment_content VARCHAR(2000) NOT NULL,
     Comment_Point INT NOT NULL DEFAULT 0,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    member_id VARCHAR(200) not null,
    FOREIGN KEY (no) REFERENCES community(no) ON DELETE CASCADE
);
select * from commuComment;
drop table commuComment;
-- 댓글 임시
iNSERT INTO Comments (board_No, Comment_content, Comment_Point) 
VALUES (3, 'This is the first comment.', 5);

INSERT INTO Comments (board_No, Comment_content, Comment_Point) 
VALUES (3, 'This is the second comment.', 4);

INSERT INTO Comments (board_No, Comment_content, Comment_Point) 
VALUES (3, 'This is the third comment.', 3);


