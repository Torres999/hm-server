package com.healthmanager.dto;

import lombok.Data;
import java.util.List;

/**
 * 冥想课程详情DTO
 */
@Data
public class MeditationCourseDetailDTO {
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
     * 课程步骤
     */
    private List<String> steps;
} 