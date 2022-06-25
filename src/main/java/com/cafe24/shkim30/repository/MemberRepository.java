package com.cafe24.shkim30.repository;

import com.cafe24.shkim30.dto.MemberDTO;
import com.cafe24.shkim30.dto.MemberUpdateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MemberRepository {
    //private final EntityManager em;
    
    private final SqlSession sqlSession;

    /**
     * 회원정보 저장
     * @param memberDTO 저장할 멤버정보
     * @return 회원번호
     */
    public Long save(MemberDTO memberDTO) {
        //em.persist(member);

        int result = sqlSession.insert("member.insertMember", memberDTO);

        return result > 0 ? memberDTO.getNo() : null;
    }

    /**
     * 회원 1명 얻어오기
     * @param no 회원번호
     * @return 회원정보
     */
    public MemberDTO findOne(Long no) {
        //return em.find(Member.class, no);
        return sqlSession.selectOne("member.selectOneByNo", no);
    }

    /**
     * 회원리스트 가져오기
     * @return 회원리스트
     */
    public List<MemberDTO> findAll() {
        //return em.createQuery("select m from Member m", Member.class).getResultList();
        return sqlSession.selectList("member.selectAll");
    }

    /**
     * 회원 이름으로 리스트 가져오기
     * @param memberId 검색 id
     * @return 회원 리스트
     */
    public MemberDTO findById(String memberId) {
        return sqlSession.selectOne("member.selectOneById", memberId);
    }

    public int updateMember(MemberUpdateDTO memberDTO) {
        return sqlSession.update("member.update", memberDTO);
    }
}
