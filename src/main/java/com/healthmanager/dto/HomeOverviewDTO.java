package com.healthmanager.dto;

import lombok.Data;
import java.util.List;

/**
 * 首页概览数据DTO
 */
@Data
public class HomeOverviewDTO {
    /**
     * 用户信息
     */
    private UserInfoDTO userInfo;
    
    /**
     * 健康数据
     */
    private HealthDataDTO healthData;
    
    /**
     * 活动图表
     */
    private ActivityChartDTO activityChart;
    
    /**
     * 用户信息DTO
     */
    @Data
    public static class UserInfoDTO {
        /**
         * 昵称
         */
        private String nickName;
        
        /**
         * 头像URL
         */
        private String avatarUrl;
    }
    
    /**
     * 健康数据DTO
     */
    @Data
    public static class HealthDataDTO {
        /**
         * 步数
         */
        private Integer steps;
        
        /**
         * 步数变化百分比
         */
        private Integer stepsChange;
        
        /**
         * 心率
         */
        private Integer heartRate;
        
        /**
         * 卡路里消耗
         */
        private Integer calories;
        
        /**
         * 睡眠时长
         */
        private Double sleep;
    }
    
    /**
     * 活动图表DTO
     */
    @Data
    public static class ActivityChartDTO {
        /**
         * 日期列表
         */
        private List<String> dates;
        
        /**
         * 数值列表
         */
        private List<Integer> values;
    }
} 