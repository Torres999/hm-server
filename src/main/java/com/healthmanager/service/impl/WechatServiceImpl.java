package com.healthmanager.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthmanager.dto.JwtRequest;
import com.healthmanager.entity.User;
import com.healthmanager.exception.ApiException;
import com.healthmanager.mapper.UserMapper;
import com.healthmanager.service.WechatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Slf4j
@Service
public class WechatServiceImpl implements WechatService {

    @Value("${wechat.appid:your_appid}")
    private String appid;

    @Value("${wechat.secret:your_secret}")
    private String secret;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public User login(JwtRequest jwtRequest) {
        // 获取微信OpenID
        String openId = getOpenId(jwtRequest.getCode());
        
        // 查询用户是否存在
        User user = userMapper.selectByOpenId(openId);
        
        // 如果用户不存在，则创建新用户
        if (user == null) {
            user = new User();
            user.setOpenId(openId);
            user.setNickName(jwtRequest.getNickName());
            user.setAvatarUrl(jwtRequest.getAvatarUrl());
            user.setGender(jwtRequest.getGender());
            user.setPhone(jwtRequest.getPhone());
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            
            userMapper.insert(user);
        } else {
            // 如果用户存在，则更新用户信息
            if (jwtRequest.getNickName() != null) {
                user.setNickName(jwtRequest.getNickName());
            }
            if (jwtRequest.getAvatarUrl() != null) {
                user.setAvatarUrl(jwtRequest.getAvatarUrl());
            }
            if (jwtRequest.getGender() != null) {
                user.setGender(jwtRequest.getGender());
            }
            if (jwtRequest.getPhone() != null) {
                user.setPhone(jwtRequest.getPhone());
            }
            user.setUpdateTime(new Date());
            
            userMapper.update(user);
        }
        
        return user;
    }

    @Override
    public String getOpenId(String code) {
        try {
            // 微信登录凭证校验接口
            String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid +
                    "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
            
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            
            // 检查是否有错误
            if (jsonNode.has("errcode") && jsonNode.get("errcode").asInt() != 0) {
                log.error("微信登录失败: {}", jsonNode.get("errmsg").asText());
                throw new ApiException("微信登录失败: " + jsonNode.get("errmsg").asText());
            }
            
            // 获取OpenID
            String openId = jsonNode.get("openid").asText();
            log.info("获取到OpenID: {}", openId);
            
            return openId;
        } catch (Exception e) {
            log.error("获取微信OpenID失败", e);
            throw new ApiException("获取微信OpenID失败: " + e.getMessage());
        }
    }
} 