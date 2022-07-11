package com.cafe24.shkim30.controller;

import com.cafe24.shkim30.dto.BlogDTO;
import com.cafe24.shkim30.dto.BlogInsertDTO;
import com.cafe24.shkim30.dto.BlogUpdateDTO;
import com.cafe24.shkim30.dto.JSONResult;
import com.cafe24.shkim30.service.BlogService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 블로그 게시물 관련 컨트롤러
 * @package
 * @author  김석현 < skok1025@naver.com >
 * @since   2022. 07. 08
 * @version 1.0
 */
@Api(tags = {"블로그 게시물 정보"})
@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @ApiOperation(value = "블로그 게시물 등록", notes = "contents 값 필수")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blogDTO", value = "등록할 블로그 게시물 정보", required = true, dataType = "BlogInsertDTO"),
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "블로그게시물 정보 등록성공")
            , @ApiResponse(code = 400, message = "블로그게시물 정보 등록실패 (필드에러)")
            , @ApiResponse(code = 500, message = "블로그게시물 정보 등록실패 (서버에러)")
    })
    @PostMapping("/contents")
    public ResponseEntity<JSONResult> createBlog(@RequestBody @Valid BlogInsertDTO blogDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            String errMsg = "";
            for (FieldError err : list) {
                errMsg += err.getField() +"-"+err.getDefaultMessage()+"/";
            }
            errMsg += "필드에러";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail(errMsg));
        }

        BlogInsertDTO insertBlog = blogService.addBlog(blogDTO);

        return insertBlog.getNo() > 0 ?
                ResponseEntity.status(HttpStatus.CREATED).body(JSONResult.success("블로그 게시물정보 등록성공", insertBlog))
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JSONResult.fail("블로그 게시물정보 등록실패"));
    }

    @ApiOperation(value = "블로그 게시물 단건조회", notes = "no 값 필수")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "no", value = "조회할 블로그 게시물번호 (no)", required = true, defaultValue = "1"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "블로그게시물 정보 단건조회성공")
            , @ApiResponse(code = 400, message = "블로그게시물 정보 단건조회실패 (필드에러)")
            , @ApiResponse(code = 500, message = "블로그게시물 정보 단건조회실패 (서버에러)")
    })
    @GetMapping("/contents/{no}")
    public ResponseEntity<JSONResult> readBlog(@PathVariable("no") Long no) {
        if (no == 0 || no == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("게시물번호 (no) 를 누락하였습니다."));
        }

        BlogDTO blogDTO = blogService.getBlog(no);

        return blogDTO != null ?
                ResponseEntity.status(HttpStatus.OK).body(JSONResult.success("블로그 게시물정보 조회성공", blogDTO))
                : ResponseEntity.status(HttpStatus.OK).body(JSONResult.fail("해당하는 블로그 게시물정보가 없음"));
    }

    @ApiOperation(value = "블로그 게시물 다건조회", notes = "모든 값 필수")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_no", value = "조회할 블로그 카테고리번호", required = false),
            @ApiImplicitParam(name = "page_content_size", value = "조회할 블로그 개수", required = true, defaultValue = "5"),
            @ApiImplicitParam(name = "start_index", value = "조회 시작 인덱스 (블로그게시물 번호기준)", required = true, defaultValue = "0")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "블로그게시물 정보 다건조회성공")
            , @ApiResponse(code = 400, message = "블로그게시물 정보 다건조회실패 (필드에러)")
            , @ApiResponse(code = 500, message = "블로그게시물 정보 다건조회실패 (서버에러)")
    })
    @GetMapping("/contents-list")
    public ResponseEntity<JSONResult> readBlogList(
            @RequestParam(value = "category_no", required = false) Long categoryNo
            ,@RequestParam("page_content_size") Long pageContentSize
            ,@RequestParam("start_index") Long startIndex
    ) {

        List<BlogDTO> blogList = blogService.getBlogList(categoryNo, pageContentSize, startIndex);

        return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success("블로그 게시물 리스트 조회성공", blogList));
    }

    @ApiOperation(value = "블로그 게시물 수정", notes = "모든 값 필수")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blogUpdateDTO", value = "수정할 블로그 게시물 정보", required = true, dataType = "BlogUpdateDTO"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "블로그게시물 정보 수정성공")
            , @ApiResponse(code = 400, message = "블로그게시물 정보 수정실패 (필드에러)")
            , @ApiResponse(code = 500, message = "블로그게시물 정보 수정실패 (서버에러)")
    })
    @PutMapping("/contents")
    public ResponseEntity<JSONResult> editBlog(@RequestBody BlogUpdateDTO blogDTO) {
        if (blogDTO.getNo() == null || blogDTO.getNo() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("수정할 게시물번호 (no) 를 누락하였습니다."));
        }

        int updateResult = blogService.editBlog(blogDTO);

        return updateResult > 0 ?
                ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(blogDTO.getNo() + "번 블로그 게시물 수정성공", blogDTO))
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JSONResult.fail("게시물 수정에 실패하였습니다."));
    }

    @ApiOperation(value = "블로그 게시물 삭제", notes = "삭제할 게시물번호 필수")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "no", value = "삭제할 게시물 번호", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "블로그게시물 정보 수정성공")
            , @ApiResponse(code = 400, message = "블로그게시물 정보 수정실패 (필드에러)")
            , @ApiResponse(code = 500, message = "블로그게시물 정보 수정실패 (서버에러)")
    })
    @DeleteMapping("/contents/{no}")
    public ResponseEntity<JSONResult> deleteBlog(@PathVariable("no") Long no) {
        if (no == null || no == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("삭제할 게시물번호 (no) 를 누락하였습니다."));
        }

        int deleteResult = blogService.deleteBlog(no);

        return deleteResult > 0 ?
                ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(no + "번 블로그 게시물 삭제성공", no))
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JSONResult.fail("게시물 삭제에 실패하였습니다."));
    }
}
