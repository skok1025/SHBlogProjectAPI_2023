package com.cafe24.shkim30.service;

import com.cafe24.shkim30.dto.MemberDTO;
import com.cafe24.shkim30.dto.MemberUpdateDTO;
import com.cafe24.shkim30.library.libEncrypt;
import com.cafe24.shkim30.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 정보 등록
     * @param memberdto 회원정보
     * @return 등록한 회원정보
     */
    @Transactional
    public MemberDTO addMember(MemberDTO memberdto) {
        MemberDTO memberInsertDTO = new MemberDTO();

        memberInsertDTO.setMemberId(memberdto.getMemberId());
        memberInsertDTO.setPassword(libEncrypt.getSHA512(memberdto.getPassword()));
        memberInsertDTO.setName(libEncrypt.encrypt_AES(memberdto.getName().getBytes(), libEncrypt.AES_KEY.getBytes()));
        memberInsertDTO.setEmail(libEncrypt.encrypt_AES(memberdto.getEmail().getBytes(), libEncrypt.AES_KEY.getBytes()));
        memberInsertDTO.setTel(libEncrypt.encrypt_AES(memberdto.getTel().getBytes(), libEncrypt.AES_KEY.getBytes()));

        Long insertMemberNo = memberRepository.save(memberInsertDTO);
        memberInsertDTO.setNo(insertMemberNo);

        return memberInsertDTO;
    }

    /**
     * 회원아이디로 조회
     * @param memberId 조회할 회원아이디
     * @return 조회한 회원정보
     */
    public MemberDTO findMemberByMemberId (String memberId) {
        MemberDTO findMember =
                memberRepository.findById(memberId);

        if (findMember != null) {
            findMember.setEmail(libEncrypt.decrypt_AES(findMember.getEmail().getBytes(), libEncrypt.AES_KEY.getBytes()));
            findMember.setTel(libEncrypt.decrypt_AES(findMember.getTel().getBytes(), libEncrypt.AES_KEY.getBytes()));
            findMember.setName(libEncrypt.decrypt_AES(findMember.getName().getBytes(), libEncrypt.AES_KEY.getBytes()));
        }

        return findMember == null ? new MemberDTO() : findMember;
    }

    /**
     * 회원정보 수정
     * @param memberDTO 수정할 회원정보
     * @return
     */
    @Transactional
    public MemberUpdateDTO updateMember(MemberUpdateDTO memberDTO) {
        int updateCnt = 0;

        if (memberDTO.getName() != null) {
            memberDTO.setName(libEncrypt.encrypt_AES(memberDTO.getName().getBytes(), libEncrypt.AES_KEY.getBytes()));
            updateCnt++;
        }

        if (memberDTO.getEmail() != null) {
            memberDTO.setEmail(libEncrypt.encrypt_AES(memberDTO.getEmail().getBytes(), libEncrypt.AES_KEY.getBytes()));
            updateCnt++;
        }

        if (memberDTO.getTel() != null) {
            memberDTO.setTel(libEncrypt.encrypt_AES(memberDTO.getTel().getBytes(), libEncrypt.AES_KEY.getBytes()));
            updateCnt++;
        }

        if (memberDTO.getPassword() != null) {
            memberDTO.setPassword(libEncrypt.getSHA512(memberDTO.getPassword()));
            updateCnt++;
        }

        if (memberDTO.getIs_delete() != null) {
            updateCnt++;
        }

        if (updateCnt > 0) {
            memberRepository.updateMember(memberDTO);
        }

        if (memberDTO.getName() != null) {
            memberDTO.setName(libEncrypt.decrypt_AES(memberDTO.getName().getBytes(), libEncrypt.AES_KEY.getBytes()));
        }
        if (memberDTO.getTel() != null) {
            memberDTO.setTel(libEncrypt.decrypt_AES(memberDTO.getTel().getBytes(), libEncrypt.AES_KEY.getBytes()));
        }
        if (memberDTO.getEmail() != null) {
            memberDTO.setEmail(libEncrypt.decrypt_AES(memberDTO.getEmail().getBytes(), libEncrypt.AES_KEY.getBytes()));
        }

        return memberDTO;
    }

    @Transactional
    public MemberDTO setDeleteMember(String memberId) {
        MemberDTO findMember = memberRepository.findById(memberId);

        if (findMember != null) {
            findMember.setIs_delete("T");
        }

        MemberDTO memberDTO = new MemberDTO();

        if (findMember != null) {
            memberDTO.setNo(findMember.getNo());
            memberDTO.setMemberId(memberId);
        }

        return memberDTO;
    }
}
