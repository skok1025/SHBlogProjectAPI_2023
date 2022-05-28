package com.cafe24.shkim30.dto;


import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MemberDTO {

    private Long no;        // 인덱스

    @NotEmpty(message = "Member ID is a required value.")
    private String memberId;// 회원아이디

    @NotEmpty(message = "Member Name is a required value.")
    private String name;    // 회원명 (암호화)
    private String password;// 비밀번호 (단방향 암호화)
    private String email;   // 이메일 (암호화)
    private String tel;     // 휴대전화번호
}
