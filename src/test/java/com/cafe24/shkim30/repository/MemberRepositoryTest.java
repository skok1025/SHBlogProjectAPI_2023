package com.cafe24.shkim30.repository;


import com.cafe24.shkim30.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

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
        Member member = new Member();
        member.setName("nameA");

        Long memberNo = memberRepository.save(member);

        Member findMember = memberRepository.findOne(memberNo);

        Assertions.assertThat(findMember.getNo()).isEqualTo(member.getNo());
        Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
        Assertions.assertThat(findMember).isEqualTo(member);
    }

}