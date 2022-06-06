package com.cafe24.shkim30.service;


import com.cafe24.shkim30.domain.Category;
import com.cafe24.shkim30.dto.CategoryDTO;
import com.cafe24.shkim30.repository.BlogAddedInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class BlogAddedInfoService {
    private final BlogAddedInfoRepository blogAddedInfoRepository;

    /**
     * 카테고리 등록
     * @param categoryDTO 등록할 카테고리 정보
     * @return 등록한 카테고리 정보
     */
    @Transactional
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        Category category = new Category();

        category.setName(categoryDTO.getName());

        if (categoryDTO.getParent_no() != null) {
            Category parentCategory = blogAddedInfoRepository.categoryFindOne(categoryDTO.getParent_no());
            category.setParentCategory(parentCategory);
        }

        Long categoryNo = blogAddedInfoRepository.saveCategory(category);
        categoryDTO.setNo(categoryNo);

        return categoryNo > 0 ? categoryDTO : null;
    }
}
