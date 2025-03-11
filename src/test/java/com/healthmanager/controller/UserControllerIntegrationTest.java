package com.healthmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthmanager.entity.User;
import com.healthmanager.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setOpenId("test_open_id");
        testUser.setNickName("测试用户");
        testUser.setAvatarUrl("http://example.com/avatar.jpg");
        testUser.setGender(1);
        testUser.setPhone("13800138000");
        testUser.setCreateTime(new Date());
        testUser.setUpdateTime(new Date());
    }

    @Test
    @WithMockUser(username = "test_user", roles = {"USER"})
    void getUserById_ShouldReturnUser_WhenUserExists() throws Exception {
        // Arrange
        when(userService.getUserById(anyLong())).thenReturn(testUser);

        // Act & Assert
        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.openId").value("test_open_id"))
                .andExpect(jsonPath("$.data.nickName").value("测试用户"));
    }

    @Test
    @WithMockUser(username = "test_user", roles = {"USER"})
    void getUserByOpenId_ShouldReturnUser_WhenUserExists() throws Exception {
        // Arrange
        when(userService.getUserByOpenId(any())).thenReturn(testUser);

        // Act & Assert
        mockMvc.perform(get("/user/openid/test_open_id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.openId").value("test_open_id"))
                .andExpect(jsonPath("$.data.nickName").value("测试用户"));
    }

    @Test
    @WithMockUser(username = "test_user", roles = {"USER"})
    void saveUser_ShouldReturnSavedUser() throws Exception {
        // Arrange
        User newUser = new User();
        newUser.setOpenId("new_open_id");
        newUser.setNickName("新用户");
        
        when(userService.saveUser(any(User.class))).thenReturn(testUser);

        // Act & Assert
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.openId").value("test_open_id"))
                .andExpect(jsonPath("$.data.nickName").value("测试用户"));
    }

    @Test
    @WithMockUser(username = "test_user", roles = {"USER"})
    void updateUser_ShouldReturnUpdatedUser() throws Exception {
        // Arrange
        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setNickName("更新后的昵称");
        
        when(userService.updateUser(any(User.class))).thenReturn(testUser);

        // Act & Assert
        mockMvc.perform(put("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.openId").value("test_open_id"))
                .andExpect(jsonPath("$.data.nickName").value("测试用户"));
    }

    @Test
    @WithMockUser(username = "test_user", roles = {"USER"})
    void deleteUser_ShouldReturnSuccess() throws Exception {
        // Arrange
        when(userService.deleteUser(anyLong())).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data").value(true));
    }
} 