package com.cafe24.shkim30.controller;

import com.cafe24.shkim30.dto.MemberDTO;
import com.cafe24.shkim30.dto.MemberUpdateDTO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class MemberControllerTest {
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
        this.setBaseMemberData();
    }

    @After
    @Rollback(true)
    public void cleanup() {}

    private void setBaseMemberData() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberId("test_id");
        memberDTO.setEmail("test@exam.com");
        memberDTO.setName("kim");
        memberDTO.setPassword("sk123456789!");
        memberDTO.setTel("01000000000");

        memberService.addMember(memberDTO);
    }

    @Test
    public void testFindMember_Success() throws Exception {
        String existedMemberId = "test_id";

        ResultActions resultActions = mockMvc.
                perform(get("/member/" + existedMemberId));

        resultActions
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.result", is("success")));
    }

    @Test
    public void testFindMember_Fail() throws Exception {
        String notExistedMemberId = "test_id_not_existed";

        ResultActions resultActions = mockMvc.
                perform(get("/member/" + notExistedMemberId));

        resultActions
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.result", is("fail")));
    }

    @Test
    public void testAddMember_Success() throws Exception {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberId("member_id");
        memberDTO.setEmail("test@exam.com");
        memberDTO.setName("kim");
        memberDTO.setPassword("sk123456789!");
        memberDTO.setTel("01000000000");

        String requestBody = mapper.writeValueAsString(memberDTO);

        ResultActions resultActions = mockMvc.
                perform(post("/member").content(requestBody).contentType(MediaType.APPLICATION_JSON));

        resultActions
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.result", is("success")));
    }

    @Test
    public void testAddMember_Fail1_EmailCheck() throws Exception {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberId("member_id");
        memberDTO.setEmail("test");
        memberDTO.setName("kim");
        memberDTO.setPassword("sk123456789!");
        memberDTO.setTel("01000000000");

        String requestBody = mapper.writeValueAsString(memberDTO);

        ResultActions resultActions = mockMvc.
                perform(post("/member").content(requestBody).contentType(MediaType.APPLICATION_JSON));

        resultActions
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andExpect(jsonPath("$.result", is("fail")));
    }

    @Test
    public void testAddMember_Fail2_PasswordCheck() throws Exception {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberId("member_id");
        memberDTO.setEmail("test@exam.com");
        memberDTO.setName("kim");
        memberDTO.setPassword("11234");
        memberDTO.setTel("01000000000");

        String requestBody = mapper.writeValueAsString(memberDTO);

        ResultActions resultActions = mockMvc.
                perform(post("/member").content(requestBody).contentType(MediaType.APPLICATION_JSON));

        resultActions
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andExpect(jsonPath("$.result", is("fail")));
    }

    @Test
    public void testEditMember_Success() throws Exception {
        MemberUpdateDTO memberDTO = new MemberUpdateDTO();
        memberDTO.setMemberId("test_id");
        memberDTO.setEmail("test1@exam.com"); // test@exam.com -> test1@exam.com
        memberDTO.setName("lee"); // kim -> lee
        memberDTO.setPassword("sk987654321!"); // sk123456789! -> sk987654321!
        memberDTO.setTel("010123456789"); // 01000000000 -> 010123456789

        String requestBody = mapper.writeValueAsString(memberDTO);

        ResultActions resultActions = mockMvc.
                perform(put("/member").content(requestBody).contentType(MediaType.APPLICATION_JSON));

        resultActions
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.result", is("success")));
    }

    @Test
    public void testEditMember_Fail1_EmailCHeck() throws Exception {
        MemberUpdateDTO memberDTO = new MemberUpdateDTO();
        memberDTO.setMemberId("test_id");
        memberDTO.setEmail("test1"); // test@exam.com -> test1 !! is not email format
        memberDTO.setName("lee"); // kim -> lee
        memberDTO.setPassword("sk987654321!"); // sk123456789! -> sk987654321!
        memberDTO.setTel("010123456789"); // 01000000000 -> 010123456789

        String requestBody = mapper.writeValueAsString(memberDTO);

        ResultActions resultActions = mockMvc.
                perform(put("/member").content(requestBody).contentType(MediaType.APPLICATION_JSON));

        resultActions
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andExpect(jsonPath("$.result", is("fail")));
    }

    @Test
    public void testEditMember_Fail2_PasswordCheck() throws Exception {
        MemberUpdateDTO memberDTO = new MemberUpdateDTO();
        memberDTO.setMemberId("test_id");
        memberDTO.setEmail("test1@exam.com"); // test@exam.com -> test1@exam.com
        memberDTO.setName("lee"); // kim -> lee
        memberDTO.setPassword("1234"); // sk123456789! -> 1234  !!password error
        memberDTO.setTel("010123456789"); // 01000000000 -> 010123456789

        String requestBody = mapper.writeValueAsString(memberDTO);

        ResultActions resultActions = mockMvc.
                perform(put("/member").content(requestBody).contentType(MediaType.APPLICATION_JSON));

        resultActions
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andExpect(jsonPath("$.result", is("fail")));
    }

    @Test
    public void testDeleteMember_Success() throws Exception {
        String existedMemberId = "test_id";

        ResultActions resultActions = mockMvc.
                perform(delete("/member/" + existedMemberId));

        resultActions
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.result", is("success")));
    }

    @Test
    public void testDeleteMember_Fail() throws Exception {
        String notExistedMemberId = "test_id_not_existed";

        ResultActions resultActions = mockMvc.
                perform(delete("/member/" + notExistedMemberId));

        resultActions
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.result", is("fail")));
    }
}