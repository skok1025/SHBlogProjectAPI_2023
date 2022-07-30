
/* 멤버 정보 */
CREATE TABLE t_member(
    no bigint(20) primary key auto_increment,
    email varbinary(128) null,
    member_id varchar(20) not null,
    password varbinary(128) not null,
    tel varbinary(128) not null,
    name varbinary(32) not null,
    is_delete varchar(2) null
);

/* 블로그 카테고리 정보 */
CREATE TABLE t_blog_category(
    no bigint(20) primary key auto_increment,
    name varchar(20) not null,
    parent_no bigint(20) null,
    member_no bigint(20) null,
    FOREIGN KEY (parent_no) REFERENCES t_blog_category (no),
    FOREIGN KEY (member_no) REFERENCES t_member (no)
);

/* 블로그 컨텐츠정보 */
CREATE TABLE t_blog(
    no bigint(20) primary key auto_increment,
    contents text not null,
    ins_timestamp timestamp null,
    upd_timestamp timestamp null,
    member_no bigint(20), 
    category_no bigint(20),
    FOREIGN KEY (member_no) REFERENCES t_member (no),
    FOREIGN KEY (category_no) REFERENCES t_blog_category (no)
);

ALTER TABLE t_blog ADD COLUMN title VARCHAR(50);

ALTER TABLE t_member convert to charset utf8;
ALTER TABLE t_blog_category convert to charset utf8;
ALTER TABLE t_blog convert to charset utf8;

ALTER TABLE t_blog MODIFY upd_timestamp timestamp DEFAULT now() ON UPDATE CURRENT_TIMESTAMP();

ALTER TABLE t_blog_category ADD COLUMN level enum('1','2') DEFAULT 1 NULL;

-- parent_no 제약조건 제거
ALTER TABLE t_blog_category DROP CONSTRAINT t_blog_category_ibfk_1;