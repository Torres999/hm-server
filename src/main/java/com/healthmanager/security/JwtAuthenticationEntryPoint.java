//package com.healthmanager.security;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.healthmanager.common.Result;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.Serializable;
//
///**
// * JWT认证入口点
// * 处理认证异常
// */
//@Component
//public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
//
//    private static final long serialVersionUID = -7858869558953243875L;
//
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response,
//                         AuthenticationException authException) throws IOException {
//
//        response.setContentType("application/json;charset=UTF-8");
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(Result.error(401, "未授权：" + authException.getMessage()));
//
//        response.getWriter().write(json);
//    }
//}