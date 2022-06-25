
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
CREATE TABLE t_category(
    no bigint(20) primary key auto_increment,
    name varchar(20) not null,
    parent_no bigint(20) null,
    FOREIGN KEY (parent_no) REFERENCES t_category (no)
);