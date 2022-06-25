package com.cafe24.shkim30.controller;


import com.cafe24.shkim30.dto.JSONResult;
import com.cafe24.shkim30.dto.MemberDTO;
import com.cafe24.shkim30.dto.MemberUpdateDTO;
import com.cafe24.shkim30.service.MemberService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"회원정보"})
@RestController
@RequestMapping("/member")
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @ApiOperation(value = "회원 ID 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "조회할 회원아이디", required = true, dataType = "string", defaultValue = ""),
    })
    @GetMapping("{memberId}")
    public ResponseEntity<JSONResult> findMemberById(@PathVariable("memberId") String memberId) {
        MemberDTO findMember = memberService.findMemberByMemberId(memberId);

        return findMember.getMemberId() != null ? ResponseEntity.status(HttpStatus.OK).body(JSONResult.success("회원 ID '"+ memberId+ "' 조회성공", findMember))
                : ResponseEntity.status(HttpStatus.OK).body(JSONResult.fail("회원 ID '"+ memberId + "' 조회데이터 없음"));
    }

    @ApiOperation(value = "회원정보등록", notes = "email 을 제외한 모든 값 필수")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "memberDTO", value = "등록할 회원정보", required = true, dataType = "MemberDTO", defaultValue = ""),
    })
    @ApiResponses({
        @ApiResponse(code = 201, message = "회원정보 등록성공")
        , @ApiResponse(code = 400, message = "회원정보 등록실패 (필드에러)")
        , @ApiResponse(code = 500, message = "회원정보 등록실패 (서버에러)")
    })
    @PostMapping("")
    public ResponseEntity<JSONResult> addMember(@RequestBody @Valid MemberDTO memberDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            String errMsg = "";
            for (FieldError err : list) {
                errMsg += err.getField() +"-"+err.getDefaultMessage()+"/";
            }
            errMsg += "필드에러";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail(errMsg));
        }

        MemberDTO insertMember = memberService.addMember(memberDTO);

        return insertMember.getNo() > 0 ?
                ResponseEntity.status(HttpStatus.CREATED).body(JSONResult.success("회원정보 등록성공", insertMember))
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JSONResult.fail("회원정보 등록실패"));
    }

    @ApiOperation(value = "회원정보수정", notes = "회원명(name), 이메일(email), 전화번호(tel), password 수정가능")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberDTO", value = "수정할 회원정보", required = true, dataType = "MemberUpdateDTO", defaultValue = ""),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원정보 수정성공")
            , @ApiResponse(code = 400, message = "회원정보 수정실패 (필드에러)")
            , @ApiResponse(code = 500, message = "회원정보 수정실패 (서버에러)")
    })
    @PutMapping("")
    public ResponseEntity<JSONResult> editMember(@RequestBody @Valid MemberUpdateDTO memberDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            String errMsg = "";
            for (FieldError err : list) {
                errMsg += err.getField() +"-"+err.getDefaultMessage()+"/";
            }
            errMsg += "필드에러";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail(errMsg));
        }

        MemberUpdateDTO updateMemberInfo = memberService.updateMember(memberDTO);

        return updateMemberInfo.getMemberId() != null ?
                ResponseEntity.status(HttpStatus.OK).body(JSONResult.success("회원정보 수정성공", updateMemberInfo))
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JSONResult.fail("회원정보 수정실패"));
    }

    @ApiOperation(value = "회원정보삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "삭제할 회원아이디", required = true, dataType = "string", defaultValue = ""),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원정보 수정성공")
            , @ApiResponse(code = 500, message = "회원정보 수정실패 (서버에러)")
    })
    @DeleteMapping("{memberId}")
    public ResponseEntity<JSONResult> deleteMember(@PathVariable("memberId") String memberId) {
        MemberDTO deleteMemberInfo = memberService.setDeleteMember(memberId);

        return deleteMemberInfo.getNo() != null ?
                ResponseEntity.status(HttpStatus.OK).body(JSONResult.success("회원정보 삭제성공", deleteMemberInfo))
                : ResponseEntity.status(HttpStatus.OK).body(JSONResult.fail("회원정보 삭제실패"));
    }
}
