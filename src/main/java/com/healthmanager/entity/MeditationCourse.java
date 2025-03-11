package com.healthmanager.entity;

import lombok.Data;
import java.util.Date;

/**
 * 冥想课程实体类
 */
@Data
public class MeditationCourse {
    /**
     * 课程ID
     */
    private Long id;
    
    /**
     * 课程标题
     */
    private String title;
    
    /**
     * 课程描述
     */
    private String description;
    
    /**
     * 课程时长(分钟)
     */
    private Integer duration;
    
    /**
     * 课程图片
     */
    private String image;
    
    /**
     * 音频URL
     */
    private String audioUrl;
    
    /**
     * 分类ID
     */
    private Long categoryId;
    
    /**
     * 课程步骤，JSON格式
     */
    private String steps;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
} 