package com.healthmanager.service;

import com.healthmanager.entity.HealthData;

import java.util.Date;
import java.util.List;

/**
 * 健康数据Service接口
 */
public interface HealthDataService {
    /**
     * 根据ID查询健康数据
     * @param id 健康数据ID
     * @return 健康数据对象
     */
    HealthData getHealthDataById(Long id);
    
    /**
     * 根据用户ID和日期查询健康数据
     * @param userId 用户ID
     * @param recordDate 记录日期
     * @return 健康数据对象
     */
    HealthData getHealthDataByUserIdAndDate(Long userId, Date recordDate);
    
    /**
     * 根据用户ID查询最近的健康数据
     * @param userId 用户ID
     * @return 健康数据对象
     */
    HealthData getLatestHealthDataByUserId(Long userId);
    
    /**
     * 根据用户ID查询指定日期范围内的健康数据
     * @param userId 用户ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 健康数据列表
     */
    List<HealthData> getHealthDataByUserIdAndDateRange(Long userId, Date startDate, Date endDate);
    
    /**
     * 保存健康数据
     * @param healthData 健康数据对象
     * @return 健康数据对象
     */
    HealthData saveHealthData(HealthData healthData);
    
    /**
     * 更新健康数据
     * @param healthData 健康数据对象
     * @return 健康数据对象
     */
    HealthData updateHealthData(HealthData healthData);
    
    /**
     * 删除健康数据
     * @param id 健康数据ID
     * @return 是否成功
     */
    boolean deleteHealthData(Long id);
} 