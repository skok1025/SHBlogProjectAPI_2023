package com.cafe24.shkim30.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 블로그 게시물 엔티티
 */
@Entity
@Table(name = "t_blog")
@Getter @Setter
public class Blog {
    @Id @GeneratedValue
    private Long no;                // 인덱스
    private String contents;        // 게시물 컨텐츠

    @Column(name = "create_at")
    private LocalDateTime createAt; // 작성일자

    @Column(name = "update_at")
    private LocalDateTime updateAt; // 수정일자

    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member member;          // 작성자
}
