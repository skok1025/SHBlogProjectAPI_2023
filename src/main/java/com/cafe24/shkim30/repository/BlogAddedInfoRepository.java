package com.cafe24.shkim30.repository;

import com.cafe24.shkim30.domain.Category;
import com.cafe24.shkim30.dto.CategoryDTO;
import com.cafe24.shkim30.dto.CategoryUpdateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
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



        int insertResult = sqlSession.insert("category.insert", categoryDTO);

        log.info("categoryDTO.no :{}", categoryDTO.getNo());

        // 부모 카테고리가 없다면 자기 자신을 부모카테고리로 넣어줌
        if (categoryDTO.getParent_no() == null) {
            CategoryUpdateDTO categoryUpdateDTO = new CategoryUpdateDTO();
            categoryUpdateDTO.setNo(categoryDTO.getNo());
            categoryUpdateDTO.setParent_no(categoryDTO.getNo());

            sqlSession.update("category.updateCategory", categoryUpdateDTO);
        }

        return insertResult;
    }

    public List<CategoryDTO> getCategoryList(String memberNo) {
        return sqlSession.selectList("category.categoryList", memberNo);
    }

    public int updateCategory(CategoryUpdateDTO categoryUpdateDTO) {
        return sqlSession.update("category.updateCategory", categoryUpdateDTO);
    }

    public int deleteCategory(Long categoryNo) {
        return sqlSession.delete("category.deleteCategory", categoryNo);
    }
}
