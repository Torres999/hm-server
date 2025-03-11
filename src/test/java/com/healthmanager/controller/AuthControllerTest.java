package com.healthmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthmanager.common.Result;
import com.healthmanager.dto.JwtRequest;
import com.healthmanager.dto.JwtResponse;
import com.healthmanager.entity.User;
import com.healthmanager.security.JwtTokenUtil;
import com.healthmanager.service.WechatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WechatService wechatService;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    private JwtRequest jwtRequest;
    private User testUser;
    private String testToken = "test_jwt_token";

    @BeforeEach
    void setUp() {
        // 准备测试数据
        jwtRequest = new JwtRequest();
        jwtRequest.setCode("test_code");
        jwtRequest.setNickName("测试用户");
        jwtRequest.setAvatarUrl("http://test.com/avatar.jpg");
        jwtRequest.setGender(1);
        jwtRequest.setPhone("13800138000");

        testUser = new User();
        testUser.setId(1L);
        testUser.setOpenId("test_open_id");
        testUser.setNickName("测试用户");
        testUser.setAvatarUrl("http://test.com/avatar.jpg");
        testUser.setGender(1);
        testUser.setPhone("13800138000");
        testUser.setCreateTime(new Date());
        testUser.setUpdateTime(new Date());
    }

    @Test
    void login_ShouldReturnJwtResponse_WhenLoginSuccessful() throws Exception {
        // 准备
        when(wechatService.login(any(JwtRequest.class))).thenReturn(testUser);
        when(jwtTokenUtil.generateToken(any(UserDetails.class))).thenReturn(testToken);

        // 执行
        MvcResult result = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jwtRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("操作成功"))
                .andExpect(jsonPath("$.data.token").value(testToken))
                .andExpect(jsonPath("$.data.userId").value(testUser.getId()))
                .andExpect(jsonPath("$.data.openId").value(testUser.getOpenId()))
                .andExpect(jsonPath("$.data.nickName").value(testUser.getNickName()))
                .andExpect(jsonPath("$.data.avatarUrl").value(testUser.getAvatarUrl()))
                .andReturn();

        // 验证
        String responseJson = result.getResponse().getContentAsString();
        Result<JwtResponse> responseResult = objectMapper.readValue(responseJson, 
                objectMapper.getTypeFactory().constructParametricType(Result.class, JwtResponse.class));
        
        assertNotNull(responseResult);
        assertEquals(200, responseResult.getCode());
        assertNotNull(responseResult.getData());
        assertEquals(testToken, responseResult.getData().getToken());
        assertEquals(testUser.getId(), responseResult.getData().getUserId());
    }

    @Test
    void login_ShouldReturnBadRequest_WhenCodeIsEmpty() throws Exception {
        // 准备
        jwtRequest.setCode(null);

        // 执行和验证
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jwtRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").exists());
    }
} 