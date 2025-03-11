package com.healthmanager.entity;

import lombok.Data;
import java.util.Date;

/**
 * 任务实体类
 */
@Data
public class Task {
    /**
     * 任务ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 任务标题
     */
    private String title;
    
    /**
     * 任务描述
     */
    private String description;
    
    /**
     * 任务类型：exercise-运动，meditation-冥想
     */
    private String type;
    
    /**
     * 是否完成：0-未完成，1-已完成
     */
    private Boolean completed;
    
    /**
     * 任务日期
     */
    private Date taskDate;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
} 