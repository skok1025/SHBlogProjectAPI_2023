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

@Api(tags = {"��α� �ΰ�����"})
@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogAddedInfoController {

    private final BlogAddedInfoService blogAddedInfoService;

    @ApiOperation(value = "ī�װ� ���", notes = "email �� ������ ��� �� �ʼ�")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryDTO", value = "����� ī�װ�����", required = true, dataType = "CategoryDTO", defaultValue = ""),
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "ī�װ� ���� ��ϼ���")
            , @ApiResponse(code = 400, message = "ī�װ� ���� ��Ͻ��� (�ʵ忡��)")
            , @ApiResponse(code = 500, message = "ī�װ� ���� ��Ͻ��� (��������)")
    })
    @PostMapping("/category")
    public ResponseEntity<JSONResult> addBlogCategory(@RequestBody @Valid CategoryDTO categoryDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            String errMsg = "";
            for (FieldError err : list) {
                errMsg += err.getField() +"-"+err.getDefaultMessage()+"/";
            }
            errMsg += "�ʵ忡��";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail(errMsg));
        }

        CategoryDTO insertCategory = blogAddedInfoService.addCategory(categoryDTO);

        return insertCategory.getNo() > 0 ?
                ResponseEntity.status(HttpStatus.CREATED).body(JSONResult.success("��α� ī�װ����� ��ϼ���", insertCategory))
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JSONResult.fail("��α� ī�װ����� ��Ͻ���"));
    }
}
