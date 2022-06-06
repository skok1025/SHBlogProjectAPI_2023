package com.cafe24.shkim30.repository;

import com.cafe24.shkim30.domain.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class BlogAddedInfoRepository {
    private final EntityManager em;

    public Category categoryFindOne(Long category_no) {
        return em.find(Category.class, category_no);
    }

    public Long saveCategory(Category category) {
        em.persist(category);
        return category.getNo();
    }
}
