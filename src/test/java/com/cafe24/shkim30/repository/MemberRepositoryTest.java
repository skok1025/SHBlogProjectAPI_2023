package com.cafe24.shkim30.repository;


import com.cafe24.shkim30.dto.MemberDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    /**
     * 회원정보를 저장하는 테스트
     */
    @Test
    @Transactional
    public void testSaveMember() {
        MemberDTO member = new MemberDTO();
        member.setName("nameA");

        Long result = memberRepository.save(member);

        //Member findMember = memberRepository.findOne(member.getNo());

        Assertions.assertThat(result).isNotNull();
    }

}