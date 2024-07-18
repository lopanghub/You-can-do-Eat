use spring;

-- 댓글
CREATE TABLE commuComment (
    comment_id INT AUTO_INCREMENT PRIMARY KEY,
    no INT NOT NULL,
    comment_content VARCHAR(2000) NOT NULL,
     comment_point INT NOT NULL DEFAULT 0,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    member_id VARCHAR(200) not null,
    FOREIGN KEY (no) REFERENCES community(no) ON DELETE CASCADE
);
select * from commuComment;


