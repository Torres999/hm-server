package com.healthmanager.service.impl;

import com.healthmanager.entity.User;
import com.healthmanager.exception.ApiException;
import com.healthmanager.mapper.UserMapper;
import com.healthmanager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户Service实现类
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }
    
    @Override
    public User getUserByOpenId(String openId) {
        return userMapper.selectByOpenId(openId);
    }
    
    @Override
    public User saveUser(User user) {
        if (user.getId() != null) {
            User existUser = userMapper.selectById(user.getId());
            if (existUser != null) {
                return updateUser(user);
            }
        }
        
        int result = userMapper.insert(user);
        if (result > 0) {
            return user;
        } else {
            throw new ApiException("保存用户失败");
        }
    }
    
    @Override
    public User updateUser(User user) {
        if (user.getId() == null) {
            throw new ApiException("用户ID不能为空");
        }
        
        User existUser = userMapper.selectById(user.getId());
        if (existUser == null) {
            throw new ApiException("用户不存在");
        }
        
        int result = userMapper.update(user);
        if (result > 0) {
            return userMapper.selectById(user.getId());
        } else {
            throw new ApiException("更新用户失败");
        }
    }
    
    @Override
    public boolean deleteUser(Long id) {
        if (id == null) {
            throw new ApiException("用户ID不能为空");
        }
        
        User existUser = userMapper.selectById(id);
        if (existUser == null) {
            throw new ApiException("用户不存在");
        }
        
        int result = userMapper.deleteById(id);
        return result > 0;
    }
} 