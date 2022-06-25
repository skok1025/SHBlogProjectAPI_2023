package com.cafe24.shkim30.repository;

import com.cafe24.shkim30.domain.Category;
import com.cafe24.shkim30.dto.CategoryDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BlogAddedInfoRepository {
    //private final EntityManager em;

    private final SqlSession sqlSession;

    public Category categoryFindOne(Long category_no) {
        //return em.find(Category.class, category_no);
        return null;
    }

    public int saveCategory(CategoryDTO categoryDTO) {
//        em.persist(category);
//        return category.getNo();

        return sqlSession.insert("category.insert", categoryDTO);
    }
}
