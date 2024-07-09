use spring;

-- 댓글
CREATE TABLE Comments (
    comment_id INT AUTO_INCREMENT PRIMARY KEY,
    board_No INT NOT NULL,
    Comment_content VARCHAR(2000) NOT NULL,
     Comment_Point INT NOT NULL DEFAULT 0,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    member_id VARCHAR(200) not null,
    FOREIGN KEY (board_No) REFERENCES recipe_Board(board_No) ON DELETE CASCADE
);
select * from Comments;
-- 댓글 select
SELECT   cm.*,  rb.member_id FROM  Comments cm JOIN   recipe_Board rb ON    cm.board_No = rb.board_No where cm.board_no = 3;

-- 댓글 임시
iNSERT INTO Comments (board_No, Comment_content, Comment_Point) 
VALUES (3, 'This is the first comment.', 5);

INSERT INTO Comments (board_No, Comment_content, Comment_Point) 
VALUES (3, 'This is the second comment.', 4);

INSERT INTO Comments (board_No, Comment_content, Comment_Point) 
VALUES (3, 'This is the third comment.', 3);


  user_id INT NOT NULL;
 FOREIGN KEY (user_id) REFERENCES Member(user_id) ON DELETE CASCADE;
