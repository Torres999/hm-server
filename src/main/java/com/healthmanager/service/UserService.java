package com.healthmanager.service;

import com.healthmanager.entity.User;

/**
 * 用户Service接口
 */
public interface UserService {
    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return 用户对象
     */
    User getUserById(Long id);
    
    /**
     * 根据OpenID查询用户
     * @param openId 微信OpenID
     * @return 用户对象
     */
    User getUserByOpenId(String openId);
    
    /**
     * 保存用户
     * @param user 用户对象
     * @return 用户对象
     */
    User saveUser(User user);
    
    /**
     * 更新用户
     * @param user 用户对象
     * @return 用户对象
     */
    User updateUser(User user);
    
    /**
     * 删除用户
     * @param id 用户ID
     * @return 是否成功
     */
    boolean deleteUser(Long id);
} 