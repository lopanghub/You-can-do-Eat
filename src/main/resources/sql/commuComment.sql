use spring;
drop table commuComment;
-- 댓글
CREATE TABLE commuComment (
    commuComment_id INT AUTO_INCREMENT PRIMARY KEY,
    no INT NOT NULL,
    commuComment_content VARCHAR(2000) NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    member_id VARCHAR(200) not null,
    FOREIGN KEY (no) REFERENCES community(no) ON DELETE CASCADE
);
select * from commuComment;


