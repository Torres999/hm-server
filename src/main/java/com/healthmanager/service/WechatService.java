package com.healthmanager.service;

import com.healthmanager.dto.JwtRequest;
import com.healthmanager.entity.User;

public interface WechatService {
    
    /**
     * 微信登录
     * @param jwtRequest 登录请求
     * @return 用户信息
     */
    User login(JwtRequest jwtRequest);
    
    /**
     * 获取微信OpenID
     * @param code 微信授权码
     * @return OpenID
     */
    String getOpenId(String code);
} 