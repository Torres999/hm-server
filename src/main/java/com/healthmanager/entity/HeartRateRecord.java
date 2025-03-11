package com.healthmanager.entity;

import lombok.Data;
import java.util.Date;

/**
 * 心率记录实体类
 */
@Data
public class HeartRateRecord {
    /**
     * 心率记录ID
     */
    private Long id;
    
    /**
     * 运动记录ID
     */
    private Long exerciseId;
    
    /**
     * 时间点(秒)
     */
    private Integer timePoint;
    
    /**
     * 心率值
     */
    private Integer value;
    
    /**
     * 创建时间
     */
    private Date createTime;
} 