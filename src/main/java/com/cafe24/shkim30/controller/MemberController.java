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

@Api(tags = {"ȸ������"})
@RestController
@RequestMapping("/member")
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @ApiOperation(value = "ȸ�� ID ��ȸ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "��ȸ�� ȸ�����̵�", required = true, dataType = "string", defaultValue = ""),
    })
    @GetMapping("{memberId}")
    public ResponseEntity<JSONResult> findMemberById(@PathVariable("memberId") String memberId) {
        MemberDTO findMember = memberService.findMemberByMemberId(memberId);

        return findMember.getMemberId() != null ? ResponseEntity.status(HttpStatus.OK).body(JSONResult.success("ȸ�� ID '"+ memberId+ "' ��ȸ����", findMember))
                : ResponseEntity.status(HttpStatus.OK).body(JSONResult.fail("ȸ�� ID '"+ memberId + "' ��ȸ������ ����"));
    }

    @ApiOperation(value = "ȸ���������", notes = "email �� ������ ��� �� �ʼ�")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "memberDTO", value = "����� ȸ������", required = true, dataType = "MemberDTO", defaultValue = ""),
    })
    @ApiResponses({
        @ApiResponse(code = 201, message = "ȸ������ ��ϼ���")
        , @ApiResponse(code = 400, message = "ȸ������ ��Ͻ��� (�ʵ忡��)")
        , @ApiResponse(code = 500, message = "ȸ������ ��Ͻ��� (��������)")
    })
    @PostMapping("")
    public ResponseEntity<JSONResult> addMember(@RequestBody @Valid MemberDTO memberDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            String errMsg = "";
            for (FieldError err : list) {
                errMsg += err.getField() +"-"+err.getDefaultMessage()+"/";
            }
            errMsg += "�ʵ忡��";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail(errMsg));
        }

        MemberDTO insertMember = memberService.addMember(memberDTO);

        return insertMember.getNo() > 0 ?
                ResponseEntity.status(HttpStatus.CREATED).body(JSONResult.success("ȸ������ ��ϼ���", insertMember))
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JSONResult.fail("ȸ������ ��Ͻ���"));
    }

    @ApiOperation(value = "ȸ����������", notes = "ȸ����(name), �̸���(email), ��ȭ��ȣ(tel), password ��������")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberDTO", value = "������ ȸ������", required = true, dataType = "MemberUpdateDTO", defaultValue = ""),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ȸ������ ��������")
            , @ApiResponse(code = 400, message = "ȸ������ �������� (�ʵ忡��)")
            , @ApiResponse(code = 500, message = "ȸ������ �������� (��������)")
    })
    @PutMapping("")
    public ResponseEntity<JSONResult> editMember(@RequestBody @Valid MemberUpdateDTO memberDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            String errMsg = "";
            for (FieldError err : list) {
                errMsg += err.getField() +"-"+err.getDefaultMessage()+"/";
            }
            errMsg += "�ʵ忡��";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail(errMsg));
        }

        MemberUpdateDTO updateMemberInfo = memberService.updateMember(memberDTO);

        return updateMemberInfo.getMemberId() != null ?
                ResponseEntity.status(HttpStatus.OK).body(JSONResult.success("ȸ������ ��������", updateMemberInfo))
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JSONResult.fail("ȸ������ ��������"));
    }

    @ApiOperation(value = "ȸ����������")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "������ ȸ�����̵�", required = true, dataType = "string", defaultValue = ""),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ȸ������ ��������")
            , @ApiResponse(code = 500, message = "ȸ������ �������� (��������)")
    })
    @DeleteMapping("{memberId}")
    public ResponseEntity<JSONResult> deleteMember(@PathVariable("memberId") String memberId) {
        MemberDTO deleteMemberInfo = memberService.setDeleteMember(memberId);

        return deleteMemberInfo.getNo() != null ?
                ResponseEntity.status(HttpStatus.OK).body(JSONResult.success("ȸ������ ��������", deleteMemberInfo))
                : ResponseEntity.status(HttpStatus.OK).body(JSONResult.fail("ȸ������ ��������"));
    }
}
