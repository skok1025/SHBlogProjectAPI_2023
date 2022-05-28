package com.cafe24.shkim30.service;

import com.cafe24.shkim30.dto.MemberDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.List;

@SpringBootTest
public class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Test
    @Transactional
    @Rollback(value = false)
    public void memberAddTest() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberId("test_id");
        memberDTO.setPassword("1234");
        memberDTO.setEmail("skok1025");
        memberDTO.setName("kim");
        memberDTO.setTel("1234");

        MemberDTO insertMember = memberService.addMember(memberDTO);

        Assertions.assertThat(insertMember.getMemberId()).isEqualTo(memberDTO.getMemberId());
    }

    @Test
    @Transactional
    public void memberFindByName() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberId("test_id");
        memberDTO.setPassword("1234");
        memberDTO.setEmail("skok1025");
        memberDTO.setName("kim");
        memberDTO.setTel("1234");

        memberService.addMember(memberDTO);

        List<MemberDTO> kimList = memberService.findMemberByMemberName("kim");

        Assertions.assertThat(kimList).isNotEmpty();
    }

}