package com.cafe24.shkim30.controller;


import com.cafe24.shkim30.dto.JSONResult;
import com.cafe24.shkim30.dto.MemberDTO;
import com.cafe24.shkim30.service.MemberService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("")
    public String test() {
        return "test";
    }

    @PostMapping("")
    public ResponseEntity<JSONResult> addMember(@RequestBody @Valid MemberDTO memberdto, BindingResult bindingResult) {
        // ?��?��?�� �??�� ?��?��?��
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            String errMsg = "";
            for (FieldError err : list) {
                errMsg += err.getField() +"-"+err.getDefaultMessage()+"/";
            }
            errMsg += "?��류발?��";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail(errMsg));
        }

        MemberDTO insertMember = memberService.addMember(memberdto);

        return insertMember.getNo() > 0 ?
                ResponseEntity.status(HttpStatus.OK).body(JSONResult.success("ȸ������ ��ϼ���", insertMember))
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JSONResult.fail("ȸ������ ��Ͻ���"));
    }
}
