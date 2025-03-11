package com.healthmanager.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    @NotBlank(message = "微信授权码不能为空")
    private String code;
    
    private String nickName;
    
    private String avatarUrl;
    
    private Integer gender;
    
    private String phone;
} 