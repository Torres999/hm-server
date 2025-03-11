package com.healthmanager.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 活动统计实体类
 */
@Data
public class ActivityStats {
    /**
     * 统计ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 周期类型：week-周，month-月，year-年
     */
    private String periodType;
    
    /**
     * 周期值，如：2023-01，2023-W01
     */
    private String periodValue;
    
    /**
     * 运动次数
     */
    private Integer exerciseSessions;
    
    /**
     * 运动总时长(分钟)
     */
    private Integer exerciseMinutes;
    
    /**
     * 运动消耗卡路里
     */
    private Integer exerciseCalories;
    
    /**
     * 运动总距离(公里)
     */
    private BigDecimal exerciseDistance;
    
    /**
     * 冥想次数
     */
    private Integer meditationSessions;
    
    /**
     * 冥想总时长(分钟)
     */
    private Integer meditationMinutes;
    
    /**
     * 冥想连续天数
     */
    private Integer meditationStreak;
    
    /**
     * 步数总计
     */
    private Integer stepsTotal;
    
    /**
     * 日均步数
     */
    private Integer stepsDailyAverage;
    
    /**
     * 步数最佳日
     */
    private String stepsBestDay;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
} 