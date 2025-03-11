package com.healthmanager.service.impl;

import com.healthmanager.entity.User;
import com.healthmanager.exception.ApiException;
import com.healthmanager.mapper.UserMapper;
import com.healthmanager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    @Override
    public Object getUsersByPage(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        
        int offset = (pageNum - 1) * pageSize;
        List<User> users = userMapper.selectByPage(offset, pageSize);
        int total = userMapper.selectCount();
        
        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("list", users);
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        result.put("pages", (total + pageSize - 1) / pageSize);
        
        return result;
    }
} 