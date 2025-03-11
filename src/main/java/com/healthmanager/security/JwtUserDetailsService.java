package com.healthmanager.security;

import com.healthmanager.entity.User;
import com.healthmanager.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * JWT用户详情服务
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 在微信登录场景中，username实际上是openId
        User user = userMapper.selectByOpenId(username);
        if (user == null) {
            throw new UsernameNotFoundException("未找到用户: " + username);
        }

        // 创建授权列表
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        // 返回UserDetails对象
        // 注意：这里的密码不重要，因为微信登录不需要密码验证
        return new org.springframework.security.core.userdetails.User(
                user.getOpenId(), "{noop}password", authorities);
    }
} 