package com.cafe24.shkim30.service;

import com.cafe24.shkim30.domain.Member;
import com.cafe24.shkim30.dto.MemberDTO;
import com.cafe24.shkim30.library.libEncrypt;
import com.cafe24.shkim30.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberDTO addMember(MemberDTO memberdto) {
        Member member = new Member();

        member.setMemberId(memberdto.getMemberId());
        member.setPassword(libEncrypt.getSHA512(memberdto.getPassword()));
        member.setName(libEncrypt.encrypt_AES(memberdto.getName().getBytes(), libEncrypt.AES_KEY.getBytes()));
        member.setEmail(libEncrypt.encrypt_AES(memberdto.getEmail().getBytes(), libEncrypt.AES_KEY.getBytes()));
        member.setTel(libEncrypt.encrypt_AES(memberdto.getTel().getBytes(), libEncrypt.AES_KEY.getBytes()));

        Long memberNo = memberRepository.save(member);

        memberdto.setNo(memberNo);

        return memberNo > 0 ? memberdto : null;
    }

    public List<MemberDTO> findMemberByMemberName(String name) {
        List<MemberDTO> result = new ArrayList<>();
        List<Member> findbyName =
                memberRepository.findByName(libEncrypt.encrypt_AES(name.getBytes(),libEncrypt.AES_KEY.getBytes()));

        for (Member m :findbyName) {
            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setMemberId(m.getMemberId());
            memberDTO.setPassword(m.getPassword());
            memberDTO.setName(libEncrypt.decrypt_AES(m.getName().getBytes(), libEncrypt.AES_KEY.getBytes()));
            memberDTO.setTel(libEncrypt.decrypt_AES(m.getTel().getBytes(), libEncrypt.AES_KEY.getBytes()));
            memberDTO.setEmail(libEncrypt.decrypt_AES(m.getEmail().getBytes(), libEncrypt.AES_KEY.getBytes()));

            result.add(memberDTO);
        }

        return result;
    }
}
