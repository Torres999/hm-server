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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Api(tags = "认证相关接口")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private WechatService wechatService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @ApiOperation("微信登录")
    @PostMapping("/login")
    public Result<JwtResponse> login(@Valid @RequestBody JwtRequest jwtRequest) {
        log.info("微信登录请求: {}", jwtRequest);
        
        // 微信登录
        User user = wechatService.login(jwtRequest);
        
        // 生成JWT token
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getOpenId(), "{noop}password", authorities);

        String token = jwtTokenUtil.generateToken(userDetails);
        
        // 返回JWT响应
        JwtResponse jwtResponse = new JwtResponse(
                token, user.getId(), user.getOpenId(), user.getNickName(), user.getAvatarUrl());
        
        return Result.success(jwtResponse);
    }
} 