package com.cafe24.shkim30.service;


import com.cafe24.shkim30.dto.CategoryDTO;
import com.cafe24.shkim30.dto.CategoryUpdateDTO;
import com.cafe24.shkim30.repository.BlogAddedInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        int result = blogAddedInfoRepository.saveCategory(categoryDTO);

        return result > 0 ? categoryDTO : null;
    }

    public List<CategoryDTO> getCategoryList(String memberNo) {
        return blogAddedInfoRepository.getCategoryList(memberNo);
    }

    public int updateCategory(CategoryUpdateDTO categoryUpdateDTO) {
        return blogAddedInfoRepository.updateCategory(categoryUpdateDTO);
    }

    public int deleteCategory(Long categoryNo) {
        return blogAddedInfoRepository.deleteCategory(categoryNo);
    }
}
