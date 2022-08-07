package com.cafe24.shkim30.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class BlogDTO {

    private Long no;

    @ApiModelProperty(example = "contents~~", notes = "블로그 게시내용 입니다.", required = true)
    @NotEmpty(message = "contents is a required value.")
    private String contents;

    @ApiModelProperty(example = "", notes = "등록일자", required = false)
    private String ins_timestamp;

    @ApiModelProperty(example = "", notes = "수정일자", required = false)
    private String upd_timestamp;

    private Long member_no;
    private String member_id;
    private String member_name;

    private String title;


    private Long category_no;
    private String category_name;

    private Long parent_category_no;
    private String parent_category_name;
}
