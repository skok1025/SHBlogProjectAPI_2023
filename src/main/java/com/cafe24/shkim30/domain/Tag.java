package com.cafe24.shkim30.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 태그 정보 엔티티
 */
@Entity
@Table(name = "t_tag")
@Getter @Setter
public class Tag {
    @Id @GeneratedValue
    private Long no;    // 인덱스
    private String name;// 태그명
}
