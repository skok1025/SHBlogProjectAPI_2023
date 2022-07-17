package com.cafe24.shkim30.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class BlogInsertDTO {

    private Long no;

    @ApiModelProperty(example = "contents~~", notes = "블로그 게시내용 입니다.", required = true)
    @NotEmpty(message = "contents is a required value.")
    private String contents;

    private Long member_no;
    private Long category_no;
    private String title;
}
