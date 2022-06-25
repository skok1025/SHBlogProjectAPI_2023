package com.cafe24.shkim30.service;


import com.cafe24.shkim30.dto.CategoryDTO;
import com.cafe24.shkim30.repository.BlogAddedInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}