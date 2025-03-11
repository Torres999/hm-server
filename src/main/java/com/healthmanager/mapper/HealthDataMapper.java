package com.healthmanager.mapper;

import com.healthmanager.entity.HealthData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 健康数据Mapper接口
 */
@Repository
public interface HealthDataMapper {
    /**
     * 根据ID查询健康数据
     * @param id 健康数据ID
     * @return 健康数据对象
     */
    HealthData selectById(@Param("id") Long id);
    
    /**
     * 根据用户ID和日期查询健康数据
     * @param userId 用户ID
     * @param recordDate 记录日期
     * @return 健康数据对象
     */
    HealthData selectByUserIdAndDate(@Param("userId") Long userId, @Param("recordDate") Date recordDate);
    
    /**
     * 根据用户ID查询最近的健康数据
     * @param userId 用户ID
     * @return 健康数据对象
     */
    HealthData selectLatestByUserId(@Param("userId") Long userId);
    
    /**
     * 根据用户ID查询指定日期范围内的健康数据
     * @param userId 用户ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 健康数据列表
     */
    List<HealthData> selectByUserIdAndDateRange(@Param("userId") Long userId, 
                                               @Param("startDate") Date startDate, 
                                               @Param("endDate") Date endDate);
    
    /**
     * 插入健康数据
     * @param healthData 健康数据对象
     * @return 影响行数
     */
    int insert(HealthData healthData);
    
    /**
     * 更新健康数据
     * @param healthData 健康数据对象
     * @return 影响行数
     */
    int update(HealthData healthData);
    
    /**
     * 删除健康数据
     * @param id 健康数据ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
} 