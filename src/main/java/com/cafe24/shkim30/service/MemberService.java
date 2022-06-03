package com.cafe24.shkim30.service;

import com.cafe24.shkim30.domain.DeleteStatus;
import com.cafe24.shkim30.domain.Member;
import com.cafe24.shkim30.dto.MemberDTO;
import com.cafe24.shkim30.dto.MemberUpdateDTO;
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

    /**
     * 회원 정보 등록
     * @param memberdto 회원정보
     * @return 등록한 회원정보
     */
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

    /**
     * 회원아이디로 조회
     * @param memberId 조회할 회원아이디
     * @return 조회한 회원정보
     */
    public MemberDTO findMemberByMemberId (String memberId) {
        List<MemberDTO> result = new ArrayList<>();
        Member findMember =
                memberRepository.findById(memberId);

        MemberDTO memberDTO = new MemberDTO();

        if (findMember != null) {
            memberDTO.setNo(findMember.getNo());
            memberDTO.setMemberId(findMember.getMemberId());
            memberDTO.setPassword(findMember.getPassword());
            memberDTO.setName(libEncrypt.decrypt_AES(findMember.getName().getBytes(), libEncrypt.AES_KEY.getBytes()));
            memberDTO.setTel(libEncrypt.decrypt_AES(findMember.getTel().getBytes(), libEncrypt.AES_KEY.getBytes()));
            memberDTO.setEmail(libEncrypt.decrypt_AES(findMember.getEmail().getBytes(), libEncrypt.AES_KEY.getBytes()));
            memberDTO.setIs_delete(findMember.getIs_delete());
        }

        return memberDTO;
    }

    /**
     * 회원정보 수정
     * @param memberDTO 수정할 회원정보
     * @return
     */
    @Transactional
    public MemberUpdateDTO updateMember(MemberUpdateDTO memberDTO) {
        String memberId = memberDTO.getMemberId();
        Member findMember = memberRepository.findById(memberId);

        int updateCnt = 0;

        if (memberDTO.getName() != null) {
            findMember.setName(libEncrypt.encrypt_AES(memberDTO.getName().getBytes(), libEncrypt.AES_KEY.getBytes()));
            updateCnt++;
        }

        if (memberDTO.getEmail() != null) {
            findMember.setEmail(libEncrypt.encrypt_AES(memberDTO.getEmail().getBytes(), libEncrypt.AES_KEY.getBytes()));
            updateCnt++;
        }

        if (memberDTO.getTel() != null) {
            findMember.setTel(libEncrypt.encrypt_AES(memberDTO.getTel().getBytes(), libEncrypt.AES_KEY.getBytes()));
            updateCnt++;
        }

        if (memberDTO.getPassword() != null) {
            findMember.setPassword(libEncrypt.getSHA512(memberDTO.getPassword()));
            updateCnt++;
        }

        if (updateCnt > 0) {
            memberDTO.setMemberId(memberId);

            if (findMember != null) {
                memberDTO.setMemberId(findMember.getMemberId());
                memberDTO.setPassword(findMember.getPassword());
                memberDTO.setName(libEncrypt.decrypt_AES(findMember.getName().getBytes(), libEncrypt.AES_KEY.getBytes()));
                memberDTO.setTel(libEncrypt.decrypt_AES(findMember.getTel().getBytes(), libEncrypt.AES_KEY.getBytes()));
                memberDTO.setEmail(libEncrypt.decrypt_AES(findMember.getEmail().getBytes(), libEncrypt.AES_KEY.getBytes()));
            }
        }

        return memberDTO;
    }

    @Transactional
    public MemberDTO setDeleteMember(String memberId) {
        Member findMember = memberRepository.findById(memberId);

        if (findMember != null) {
            findMember.setIs_delete(DeleteStatus.T);
        }

        MemberDTO memberDTO = new MemberDTO();

        if (findMember != null) {
            memberDTO.setNo(findMember.getNo());
            memberDTO.setMemberId(memberId);
        }

        return memberDTO;
    }
}
