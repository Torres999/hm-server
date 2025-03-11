package com.healthmanager.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 运动路线实体类
 */
@Data
public class ExerciseRoute {
    /**
     * 路线ID
     */
    private Long id;
    
    /**
     * 运动记录ID
     */
    private Long exerciseId;
    
    /**
     * 起点纬度
     */
    private BigDecimal startLat;
    
    /**
     * 起点经度
     */
    private BigDecimal startLng;
    
    /**
     * 终点纬度
     */
    private BigDecimal endLat;
    
    /**
     * 终点经度
     */
    private BigDecimal endLng;
    
    /**
     * 路线坐标点集合，JSON格式
     */
    private String polyline;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
} 