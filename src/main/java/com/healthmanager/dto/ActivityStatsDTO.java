package com.healthmanager.dto;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 活动统计数据DTO
 */
@Data
public class ActivityStatsDTO {
    /**
     * 运动统计
     */
    private ExerciseStatsDTO exercise;
    
    /**
     * 冥想统计
     */
    private MeditationStatsDTO meditation;
    
    /**
     * 步数统计
     */
    private StepsStatsDTO steps;
    
    /**
     * 运动统计DTO
     */
    @Data
    public static class ExerciseStatsDTO {
        /**
         * 运动总次数
         */
        private Integer totalSessions;
        
        /**
         * 运动总时长(分钟)
         */
        private Integer totalMinutes;
        
        /**
         * 运动总卡路里消耗
         */
        private Integer totalCalories;
        
        /**
         * 运动总距离(公里)
         */
        private BigDecimal totalDistance;
    }
    
    /**
     * 冥想统计DTO
     */
    @Data
    public static class MeditationStatsDTO {
        /**
         * 冥想总次数
         */
        private Integer totalSessions;
        
        /**
         * 冥想总时长(分钟)
         */
        private Integer totalMinutes;
        
        /**
         * 冥想连续天数
         */
        private Integer streak;
    }
    
    /**
     * 步数统计DTO
     */
    @Data
    public static class StepsStatsDTO {
        /**
         * 步数总计
         */
        private Integer total;
        
        /**
         * 日均步数
         */
        private Integer dailyAverage;
        
        /**
         * 步数最佳日
         */
        private String bestDay;
    }
} 