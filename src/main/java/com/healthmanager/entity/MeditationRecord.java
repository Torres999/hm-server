package com.healthmanager.entity;

import lombok.Data;
import java.util.Date;

/**
 * 冥想记录实体类
 */
@Data
public class MeditationRecord {
    /**
     * 冥想记录ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 课程ID
     */
    private Long courseId;
    
    /**
     * 实际冥想时长(分钟)
     */
    private Integer duration;
    
    /**
     * 冥想日期
     */
    private Date meditationDate;
    
    /**
     * 冥想时间
     */
    private Date meditationTime;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
} 