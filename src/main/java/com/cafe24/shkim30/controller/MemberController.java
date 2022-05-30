package com.cafe24.shkim30.controller;


import com.cafe24.shkim30.dto.JSONResult;
import com.cafe24.shkim30.dto.MemberDTO;
import com.cafe24.shkim30.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

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
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @ApiOperation(value = "ȸ�� ID ��ȸ")
    @GetMapping("")
    public ResponseEntity<JSONResult> findMemberByName(@RequestParam String memberId) {
        List<MemberDTO> findMembers = memberService.findMemberByMemberId(memberId);

        return findMembers.size() > 0 ? ResponseEntity.status(HttpStatus.OK).body(JSONResult.success("ȸ�� ID '"+ memberId+ "' ��ȸ����", findMembers))
                : ResponseEntity.status(HttpStatus.OK).body(JSONResult.fail("ȸ�� ID '"+ memberId + "' ��ȸ������ ����"));
    }

    @ApiOperation(value = "ȸ�����")
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
}
