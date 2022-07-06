package com.cafe24.shkim30.controller;

import com.cafe24.shkim30.dto.CategoryDTO;
import com.cafe24.shkim30.dto.CategoryUpdateDTO;
import com.cafe24.shkim30.dto.JSONResult;
import com.cafe24.shkim30.service.BlogAddedInfoService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "카테고리 전체 리스트", notes = "카테고리 리스트 제공")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "member_no", value = "카테고리 조회할 회원번호" +
                    "", required = true, dataType = "String", defaultValue = ""),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "카테고리 리스트 조회 성공")
            , @ApiResponse(code = 400, message = "카테고리 리스트 조회실패 (필드에러)")
            , @ApiResponse(code = 500, message = "카테고리 리스트 조회실패 (서버에러)")
    })
    @GetMapping("/category-list")
    public ResponseEntity<JSONResult> readCategoryList(@RequestParam("member_no") String memberNo) {
        // 요청한 멤버번호 (member_no) 가 숫자가 아닌 경우
        if (memberNo.matches("-?\\d+(\\.\\d+)?") == false) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("멤버번호 (member_no) 를 잘못 입력하셨습니다."));
        }

        List<CategoryDTO> memberCategoryList = blogAddedInfoService.getCategoryList(memberNo);

        return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success("블로그 카테고리 리스트 조회성공", memberCategoryList));
    }

    @ApiOperation(value = "카테고리 수정", notes = "수정대상의 카테고리번호 (no) 필수")
    @ApiResponses({
            @ApiResponse(code = 200, message = "카테고리 정보 수정성공")
            , @ApiResponse(code = 400, message = "카테고리 정보 수정실패 (필드에러)")
            , @ApiResponse(code = 500, message = "카테고리 정보 수정실패 (서버에러)")
    })
    @PutMapping("/category")
    public ResponseEntity<JSONResult> updateCategoryInfo(CategoryUpdateDTO categoryUpdateDTO) {
        if (categoryUpdateDTO.getNo() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("카테고리 (no) 를 누락하였습니다."));
        }

        int result = blogAddedInfoService.updateCategory(categoryUpdateDTO);

        return result > 0 ?
                ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(categoryUpdateDTO.getNo() + "번 카테고리 수정 성공", categoryUpdateDTO)) :
                ResponseEntity.status(HttpStatus.OK).body(JSONResult.fail(categoryUpdateDTO.getNo() + "번 카테고리 수정 실패 또는 없는 카테고리"));
    }

    @ApiOperation(value = "카테고리 삭제", notes = "삭제대상의 카테고리번호 (no) 필수")
    @ApiResponses({
            @ApiResponse(code = 200, message = "카테고리 정보 삭제성공")
            , @ApiResponse(code = 400, message = "카테고리 정보 삭제실패 (필드에러)")
            , @ApiResponse(code = 500, message = "카테고리 정보 삭제실패 (서버에러)")
    })
    @DeleteMapping("/category/{categoryNo}")
    public ResponseEntity<JSONResult> deleteCategory(@PathVariable("categoryNo") Long categoryNo) {
        if (categoryNo == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("카테고리 (no) 를 누락하였습니다."));
        }

        int result = blogAddedInfoService.deleteCategory(categoryNo);

        return result > 0 ?
                ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(categoryNo + "번 카테고리 삭제를 성공하였습니다", result)) :
                ResponseEntity.status(HttpStatus.OK).body(JSONResult.fail(categoryNo + "번 카테고리 삭제 실패 또는 없는 카테고리"));
    }
}
