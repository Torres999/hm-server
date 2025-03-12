package com.healthmanager.controller;

import com.healthmanager.common.Result;
import com.healthmanager.dto.JwtRequest;
import com.healthmanager.dto.JwtResponse;
import com.healthmanager.entity.User;
import com.healthmanager.security.JwtTokenUtil;
import com.healthmanager.service.WechatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 认证控制器
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@Api(tags = "认证管理", description = "用户认证相关接口")
public class AuthController {

    @Autowired
    private WechatService wechatService;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 微信登录
     *
     * @param jwtRequest 登录请求，包含微信授权码
     * @return JWT令牌和用户信息
     */
    @ApiOperation(value = "微信登录", notes = "通过微信授权码进行登录，返回JWT令牌和用户信息")
    @PostMapping("/login")
    public Result<JwtResponse> login(@Valid @RequestBody JwtRequest jwtRequest) {
        log.info("微信登录请求，授权码: {}", jwtRequest.getCode());
        // 调用微信登录服务
        User user = wechatService.login(jwtRequest);
        
        // 生成JWT令牌
        String token = jwtTokenUtil.generateToken(user.getOpenId());
        log.info("用户登录成功，token: {}", token);
        
        // 构建响应对象
        JwtResponse response = new JwtResponse(
                token,
                user.getId(),
                user.getOpenId(),
                user.getNickName(),
                user.getAvatarUrl()
        );
        
        log.info("用户登录成功，用户ID: {}, OpenID: {}", user.getId(), user.getOpenId());
        return Result.success(response);
    }
} 