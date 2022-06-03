package com.cafe24.shkim30.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

/**
 * 회원 엔티티
 */
@Entity
@Table(name = "t_member")
@Getter @Setter
public class Member
{
    @Id @GeneratedValue
    private Long no;        // 인덱스

    @Column(name = "member_id", columnDefinition = "VARCHAR(20)")
    private String memberId;// 회원아이디

    @Column(columnDefinition = "VARBINARY(32)")
    private String name;    // 회원명 (암호화)

    @Column(columnDefinition = "VARBINARY(128)")
    private String password;// 비밀번호 (단방향 암호화)

    @Column(columnDefinition = "VARBINARY(128)")
    private String email;   // 이메일 (암호화)

    @Column(columnDefinition = "VARBINARY(32)")
    private String tel;     // 휴대전화번호

    @Enumerated(EnumType.STRING)
    private DeleteStatus is_delete = DeleteStatus.F;     // 삭제여부
}
