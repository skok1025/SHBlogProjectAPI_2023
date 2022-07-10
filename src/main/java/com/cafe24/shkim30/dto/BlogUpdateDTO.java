package com.cafe24.shkim30.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class BlogUpdateDTO {

    @ApiModelProperty(example = "1", notes = "블로그 게시번호", required = true)
    @NotEmpty(message = "no is a required value.")
    private Long no;

    @ApiModelProperty(example = "contents~~", notes = "블로그 게시내용 입니다.", required = true)
    @NotEmpty(message = "contents is a required value.")
    private String contents;
    //private String ins_timestamp;
    //private String upd_timestamp;
    //private Long member_no;
}
