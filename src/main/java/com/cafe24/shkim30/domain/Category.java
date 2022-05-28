package com.cafe24.shkim30.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 블로그 카테고리 정보
 */
@Entity
@Table(name = "t_category")
@Getter @Setter
public class Category {
    @Id @GeneratedValue
    private Long no;            // 인덱스

    private String name;        // 카테고리명

    @OneToOne
    @JoinColumn(name = "parent_no")
    private Category category;  // 부모 카테고리 정보
}
