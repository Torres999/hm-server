package com.healthmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    
    private final String token;
    private final Long userId;
    private final String openId;
    private final String nickName;
    private final String avatarUrl;
} 