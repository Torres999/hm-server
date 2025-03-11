package com.healthmanager.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 运动记录详情DTO
 */
@Data
public class ExerciseDetailDTO {
    /**
     * 运动记录ID
     */
    private String id;
    
    /**
     * 运动类型
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
    private String distance;
    
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
    private String date;
    
    /**
     * 运动时间
     */
    private String time;
    
    /**
     * 心率记录
     */
    private List<HeartRateDTO> heartRate;
    
    /**
     * 运动路线
     */
    private RouteDTO route;
    
    /**
     * 备注
     */
    private String notes;
    
    /**
     * 心率记录DTO
     */
    @Data
    public static class HeartRateDTO {
        /**
         * 时间点(秒)
         */
        private Integer time;
        
        /**
         * 心率值
         */
        private Integer value;
    }
    
    /**
     * 运动路线DTO
     */
    @Data
    public static class RouteDTO {
        /**
         * 起点纬度
         */
        private BigDecimal startLat;
        
        /**
         * 起点经度
         */
        private BigDecimal startLng;
        
        /**
         * 标记点列表
         */
        private List<MarkerDTO> markers;
        
        /**
         * 路线列表
         */
        private List<PolylineDTO> polyline;
    }
    
    /**
     * 标记点DTO
     */
    @Data
    public static class MarkerDTO {
        /**
         * 标记点ID
         */
        private Integer id;
        
        /**
         * 纬度
         */
        private BigDecimal latitude;
        
        /**
         * 经度
         */
        private BigDecimal longitude;
        
        /**
         * 标题
         */
        private String title;
    }
    
    /**
     * 路线DTO
     */
    @Data
    public static class PolylineDTO {
        /**
         * 坐标点列表
         */
        private List<PointDTO> points;
        
        /**
         * 颜色
         */
        private String color;
        
        /**
         * 宽度
         */
        private Integer width;
    }
    
    /**
     * 坐标点DTO
     */
    @Data
    public static class PointDTO {
        /**
         * 纬度
         */
        private BigDecimal latitude;
        
        /**
         * 经度
         */
        private BigDecimal longitude;
    }
} 