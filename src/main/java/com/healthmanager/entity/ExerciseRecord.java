package com.healthmanager.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 运动记录实体类
 */
@Data
public class ExerciseRecord {
    /**
     * 运动记录ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 运动类型：running-跑步，cycling-骑行，yoga-瑜伽等
     */
    private String type;
    
    /**
     * 运动名称
     */
    private String name;
    
    /**
     * 运动时长(分钟)
     */
    private Integer duration;
    
    /**
     * 运动距离(公里)
     */
    private BigDecimal distance;
    
    /**
     * 卡路里消耗
     */
    private Integer calories;
    
    /**
     * 配速
     */
    private String pace;
    
    /**
     * 运动日期
     */
    private Date exerciseDate;
    
    /**
     * 运动时间
     */
    private Date exerciseTime;
    
    /**
     * 备注
     */
    private String notes;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
} 