package com.cafe24.shkim30.dto;


import lombok.Data;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

@Data
public class MemberDTO {

    private Long no;        // 인덱스

    @ApiModelProperty(example = "member_id", notes = "회원아이디입니다.", required = true)
    @NotEmpty(message = "Member ID is a required value.")
    private String memberId;// 회원아이디

    @ApiModelProperty(example = "member_name", notes = "회원의 이름입니다.", required = true)
    @NotEmpty(message = "Member Name is a required value.")
    private String name;    // 회원명 (암호화)
    
    @ApiModelProperty(example = "password", notes = "비밀번호입니다.", required = true)
    private String password;// 패스워드 (단방향 암호화)
    
    @ApiModelProperty(example = "example@exam.com", notes = "이메일 입니다.", required = false)
    private String email;   // 이메일 (암호화)
    
    @ApiModelProperty(example = "01000000000", notes = "전화번호 입니다.", required = true)
    private String tel;     // 휴대전화번호 (암호화)
}
