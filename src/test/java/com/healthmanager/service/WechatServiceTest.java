package com.healthmanager.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthmanager.dto.JwtRequest;
import com.healthmanager.entity.User;
import com.healthmanager.exception.ApiException;
import com.healthmanager.mapper.UserMapper;
import com.healthmanager.service.impl.WechatServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WechatServiceTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private JsonNode jsonNode;

    @InjectMocks
    private WechatServiceImpl wechatService;

    private JwtRequest jwtRequest;
    private User testUser;
    private String testOpenId = "test_open_id";

    @BeforeEach
    void setUp() throws Exception {
        // 设置微信配置
        ReflectionTestUtils.setField(wechatService, "appid", "test_appid");
        ReflectionTestUtils.setField(wechatService, "secret", "test_secret");

        // 准备测试数据
        jwtRequest = new JwtRequest();
        jwtRequest.setCode("test_code");
        jwtRequest.setNickName("测试用户");
        jwtRequest.setAvatarUrl("http://test.com/avatar.jpg");
        jwtRequest.setGender(1);
        jwtRequest.setPhone("13800138000");

        testUser = new User();
        testUser.setId(1L);
        testUser.setOpenId(testOpenId);
        testUser.setNickName("测试用户");
        testUser.setAvatarUrl("http://test.com/avatar.jpg");
        testUser.setGender(1);
        testUser.setPhone("13800138000");
        testUser.setCreateTime(new Date());
        testUser.setUpdateTime(new Date());

        // Mock ObjectMapper和JsonNode
        when(objectMapper.readTree(anyString())).thenReturn(jsonNode);
        when(jsonNode.has("errcode")).thenReturn(false);
        when(jsonNode.get("openid")).thenReturn(jsonNode);
        when(jsonNode.asText()).thenReturn(testOpenId);
    }

    @Test
    void getOpenId_ShouldReturnOpenId_WhenCodeIsValid() {
        // 准备
        ResponseEntity<String> responseEntity = new ResponseEntity<>("{\"openid\":\"" + testOpenId + "\"}", HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);

        // 执行
        String openId = wechatService.getOpenId(jwtRequest.getCode());

        // 验证
        assertEquals(testOpenId, openId);
        verify(restTemplate).getForEntity(contains("test_appid"), eq(String.class));
    }

    @Test
    void getOpenId_ShouldThrowException_WhenWechatReturnsError() throws Exception {
        // 准备
        ResponseEntity<String> responseEntity = new ResponseEntity<>("{\"errcode\":40029,\"errmsg\":\"invalid code\"}", HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);
        
        when(jsonNode.has("errcode")).thenReturn(true);
        when(jsonNode.get("errcode")).thenReturn(jsonNode);
        when(jsonNode.asInt()).thenReturn(40029);
        when(jsonNode.get("errmsg")).thenReturn(jsonNode);
        when(jsonNode.asText()).thenReturn("invalid code");

        // 执行和验证
        ApiException exception = assertThrows(ApiException.class, () -> {
            wechatService.getOpenId(jwtRequest.getCode());
        });
        assertTrue(exception.getMessage().contains("微信登录失败"));
    }

    @Test
    void login_ShouldCreateNewUser_WhenUserNotExists() {
        // 准备
        ResponseEntity<String> responseEntity = new ResponseEntity<>("{\"openid\":\"" + testOpenId + "\"}", HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);
        when(userMapper.selectByOpenId(testOpenId)).thenReturn(null);
        when(userMapper.insert(any(User.class))).thenReturn(1);

        // 执行
        User result = wechatService.login(jwtRequest);

        // 验证
        assertNotNull(result);
        assertEquals(testOpenId, result.getOpenId());
        assertEquals(jwtRequest.getNickName(), result.getNickName());
        verify(userMapper).insert(any(User.class));
        verify(userMapper, never()).update(any(User.class));
    }

    @Test
    void login_ShouldUpdateExistingUser_WhenUserExists() {
        // 准备
        ResponseEntity<String> responseEntity = new ResponseEntity<>("{\"openid\":\"" + testOpenId + "\"}", HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);
        when(userMapper.selectByOpenId(testOpenId)).thenReturn(testUser);
        when(userMapper.update(any(User.class))).thenReturn(1);

        // 修改请求数据以测试更新
        jwtRequest.setNickName("新昵称");
        jwtRequest.setAvatarUrl("http://test.com/new_avatar.jpg");

        // 执行
        User result = wechatService.login(jwtRequest);

        // 验证
        assertNotNull(result);
        assertEquals(testOpenId, result.getOpenId());
        assertEquals(jwtRequest.getNickName(), result.getNickName());
        assertEquals(jwtRequest.getAvatarUrl(), result.getAvatarUrl());
        verify(userMapper, never()).insert(any(User.class));
        verify(userMapper).update(any(User.class));
    }
} 