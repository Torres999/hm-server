package com.healthmanager.service;

import com.healthmanager.entity.User;
import com.healthmanager.exception.ApiException;
import com.healthmanager.mapper.UserMapper;
import com.healthmanager.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

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
    void getUserById_ShouldReturnUser_WhenUserExists() {
        // Arrange
        when(userMapper.selectById(anyLong())).thenReturn(testUser);

        // Act
        User result = userService.getUserById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(testUser.getId(), result.getId());
        assertEquals(testUser.getOpenId(), result.getOpenId());
        assertEquals(testUser.getNickName(), result.getNickName());
        verify(userMapper, times(1)).selectById(1L);
    }

    @Test
    void getUserByOpenId_ShouldReturnUser_WhenUserExists() {
        // Arrange
        when(userMapper.selectByOpenId(anyString())).thenReturn(testUser);

        // Act
        User result = userService.getUserByOpenId("test_open_id");

        // Assert
        assertNotNull(result);
        assertEquals(testUser.getId(), result.getId());
        assertEquals(testUser.getOpenId(), result.getOpenId());
        verify(userMapper, times(1)).selectByOpenId("test_open_id");
    }

    @Test
    void saveUser_ShouldInsertUser_WhenUserIsNew() {
        // Arrange
        User newUser = new User();
        newUser.setOpenId("new_open_id");
        newUser.setNickName("新用户");
        
        when(userMapper.insert(any(User.class))).thenReturn(1);

        // Act
        User result = userService.saveUser(newUser);

        // Assert
        assertNotNull(result);
        assertEquals(newUser.getOpenId(), result.getOpenId());
        assertEquals(newUser.getNickName(), result.getNickName());
        verify(userMapper, times(1)).insert(newUser);
    }

    @Test
    void saveUser_ShouldUpdateUser_WhenUserExists() {
        // Arrange
        when(userMapper.selectById(anyLong())).thenReturn(testUser);
        when(userMapper.update(any(User.class))).thenReturn(1);

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setNickName("更新的昵称");

        // Act
        User result = userService.saveUser(existingUser);

        // Assert
        assertNotNull(result);
        verify(userMapper, times(1)).selectById(1L);
        verify(userMapper, times(1)).update(existingUser);
    }

    @Test
    void updateUser_ShouldThrowException_WhenUserIdIsNull() {
        // Arrange
        User userWithoutId = new User();
        userWithoutId.setNickName("无ID用户");

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            userService.updateUser(userWithoutId);
        });
        assertEquals("用户ID不能为空", exception.getMessage());
        verify(userMapper, never()).update(any(User.class));
    }

    @Test
    void updateUser_ShouldThrowException_WhenUserNotExists() {
        // Arrange
        User nonExistentUser = new User();
        nonExistentUser.setId(999L);
        nonExistentUser.setNickName("不存在的用户");
        
        when(userMapper.selectById(anyLong())).thenReturn(null);

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            userService.updateUser(nonExistentUser);
        });
        assertEquals("用户不存在", exception.getMessage());
        verify(userMapper, times(1)).selectById(999L);
        verify(userMapper, never()).update(any(User.class));
    }

    @Test
    void updateUser_ShouldUpdateUser_WhenUserExists() {
        // Arrange
        when(userMapper.selectById(anyLong())).thenReturn(testUser);
        when(userMapper.update(any(User.class))).thenReturn(1);
        when(userMapper.selectById(anyLong())).thenReturn(testUser);

        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setNickName("更新后的昵称");

        // Act
        User result = userService.updateUser(updatedUser);

        // Assert
        assertNotNull(result);
        verify(userMapper, times(2)).selectById(1L);
        verify(userMapper, times(1)).update(updatedUser);
    }

    @Test
    void deleteUser_ShouldThrowException_WhenUserIdIsNull() {
        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            userService.deleteUser(null);
        });
        assertEquals("用户ID不能为空", exception.getMessage());
        verify(userMapper, never()).deleteById(any());
    }

    @Test
    void deleteUser_ShouldThrowException_WhenUserNotExists() {
        // Arrange
        when(userMapper.selectById(anyLong())).thenReturn(null);

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            userService.deleteUser(999L);
        });
        assertEquals("用户不存在", exception.getMessage());
        verify(userMapper, times(1)).selectById(999L);
        verify(userMapper, never()).deleteById(any());
    }

    @Test
    void deleteUser_ShouldDeleteUser_WhenUserExists() {
        // Arrange
        when(userMapper.selectById(anyLong())).thenReturn(testUser);
        when(userMapper.deleteById(anyLong())).thenReturn(1);

        // Act
        boolean result = userService.deleteUser(1L);

        // Assert
        assertTrue(result);
        verify(userMapper, times(1)).selectById(1L);
        verify(userMapper, times(1)).deleteById(1L);
    }
} 