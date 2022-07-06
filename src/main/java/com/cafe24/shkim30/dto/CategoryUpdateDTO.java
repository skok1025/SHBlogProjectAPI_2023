package com.cafe24.shkim30.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 블로그 카테고리 정보
 */

@Data
public class CategoryUpdateDTO {

    @ApiModelProperty(example = "2", notes = "카테고리 번호입니다.", required = true)
    @NotEmpty(message = "Category no is a required value.")
    private Long no;            // 인덱스

    @ApiModelProperty(example = "category_name", notes = "카테고리명 입니다.", required = false)
    private String name;        // 카테고리명

    @ApiModelProperty(example = "10", notes = "부모카테고리 no 입니다", required = false)
    private Long parent_no;  // 부모 카테고리 정보

    @ApiModelProperty(example = "1", notes = "멤버의 고유번호입니다.", required = false)
    private Long member_no; // 블로그 카테고리 소유자 멤버번호
}
