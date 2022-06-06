package com.cafe24.shkim30.controller;

import com.cafe24.shkim30.dto.CategoryDTO;
import com.cafe24.shkim30.dto.JSONResult;
import com.cafe24.shkim30.service.BlogAddedInfoService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"블로그 부가정보"})
@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogAddedInfoController {

    private final BlogAddedInfoService blogAddedInfoService;

    @ApiOperation(value = "카테고리 등록", notes = "email 을 제외한 모든 값 필수")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryDTO", value = "등록할 카테고리정보", required = true, dataType = "CategoryDTO", defaultValue = ""),
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "카테고리 정보 등록성공")
            , @ApiResponse(code = 400, message = "카테고리 정보 등록실패 (필드에러)")
            , @ApiResponse(code = 500, message = "카테고리 정보 등록실패 (서버에러)")
    })
    @PostMapping("/category")
    public ResponseEntity<JSONResult> addBlogCategory(@RequestBody @Valid CategoryDTO categoryDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            String errMsg = "";
            for (FieldError err : list) {
                errMsg += err.getField() +"-"+err.getDefaultMessage()+"/";
            }
            errMsg += "필드에러";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail(errMsg));
        }

        CategoryDTO insertCategory = blogAddedInfoService.addCategory(categoryDTO);

        return insertCategory.getNo() > 0 ?
                ResponseEntity.status(HttpStatus.CREATED).body(JSONResult.success("블로그 카테고리정보 등록성공", insertCategory))
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JSONResult.fail("블로그 카테고리정보 등록실패"));
    }
}
