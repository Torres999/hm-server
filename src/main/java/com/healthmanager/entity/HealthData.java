package com.healthmanager.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 健康数据实体类
 */
@Data
public class HealthData {
    /**
     * 健康数据ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 步数
     */
    private Integer steps;
    
    /**
     * 心率
     */
    private Integer heartRate;
    
    /**
     * 睡眠时长(小时)
     */
    private BigDecimal sleepHours;
    
    /**
     * 卡路里消耗
     */
    private Integer calories;
    
    /**
     * 记录日期
     */
    private Date recordDate;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
} 