package com.cafe24.shkim30.repository;

import com.cafe24.shkim30.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    /**
     * 회원정보 저장
     * @param member 저장할 멤버정보
     * @return 회원번호
     */
    public Long save(Member member) {
        em.persist(member);
        return member.getNo();
    }

    /**
     * 회원 1명 얻어오기
     * @param no 회원번호
     * @return 회원정보
     */
    public Member findOne(Long no) {
        return em.find(Member.class, no);
    }

    /**
     * 회원리스트 가져오기
     * @return 회원리스트
     */
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    /**
     * 회원 이름으로 리스트 가져오기
     * @param name 검색이름
     * @return 회원 리스트
     */
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
