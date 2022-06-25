package com.cafe24.shkim30.service;

import com.cafe24.shkim30.dto.CategoryDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
class BlogAddedInfoServiceTest {

    @Autowired
    BlogAddedInfoService service;


    @Test
    @Transactional
    //@Rollback(value = false)
    public void categoryAddTest() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("new category");

        CategoryDTO result = service.addCategory(categoryDTO);
        Assertions.assertThat(categoryDTO.getName()).isEqualTo(result.getName());
        Assertions.assertThat(categoryDTO.getNo()).isNotNull();
    }
}