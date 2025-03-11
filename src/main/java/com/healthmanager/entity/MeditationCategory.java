package com.healthmanager.entity;

import lombok.Data;
import java.util.Date;

/**
 * 冥想分类实体类
 */
@Data
public class MeditationCategory {
    /**
     * 分类ID
     */
    private Long id;
    
    /**
     * 分类名称
     */
    private String name;
    
    /**
     * 分类描述
     */
    private String description;
    
    /**
     * 分类图片
     */
    private String image;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
} 