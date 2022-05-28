package com.cafe24.shkim30.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 블로그 게시물 댓글 엔티티
 */
@Entity
@Table(name = "t_comment")
@Getter @Setter
public class Comment {
    @Id @GeneratedValue
    private Long no;

    @ManyToOne
    @JoinColumn(name = "blog_no")
    private Blog blog;

    @Column(columnDefinition = "TEXT")
    private String contents;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;
}
