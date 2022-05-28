package com.cafe24.shkim30.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 블로그 태그 엔티티
 */
@Entity
@Table(name = "t_blogtag")
@Getter @Setter
public class BlogTag {
    @Id @GeneratedValue
    private Long no;                // 인덱스

    @ManyToOne
    @JoinColumn(name = "blog_no")
    private Blog blog;              // 블로그 게시물

    @ManyToOne
    @JoinColumn(name = "tag_no")
    private Tag tag;                // 태그 정보
}
