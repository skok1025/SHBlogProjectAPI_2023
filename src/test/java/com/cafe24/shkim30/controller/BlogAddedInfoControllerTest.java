package com.cafe24.shkim30.controller;

import com.cafe24.shkim30.dto.CategoryDTO;
import com.cafe24.shkim30.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class BlogAddedInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MemberService memberService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @After
    @Rollback(true)
    public void cleanup() {}

    @Test
    public void testAddCategory_Success() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("insert category name");

        String requestBody = mapper.writeValueAsString(categoryDTO);

        ResultActions resultActions = mockMvc.
                perform(post("/blog/category").content(requestBody).contentType(MediaType.APPLICATION_JSON));

        resultActions
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.result", is("success")));
    }

    @Test
    public void testAddCategory_Fail1_categoryName() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("");

        String requestBody = mapper.writeValueAsString(categoryDTO);

        ResultActions resultActions = mockMvc.
                perform(post("/blog/category").content(requestBody).contentType(MediaType.APPLICATION_JSON));

        resultActions
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andExpect(jsonPath("$.result", is("fail")));
    }
}