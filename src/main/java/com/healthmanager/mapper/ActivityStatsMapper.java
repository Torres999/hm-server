package com.healthmanager.mapper;

import com.healthmanager.entity.ActivityStats;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 活动统计Mapper接口
 */
@Repository
public interface ActivityStatsMapper {
    /**
     * 根据ID查询活动统计
     * @param id 统计ID
     * @return 活动统计对象
     */
    ActivityStats selectById(@Param("id") Long id);
    
    /**
     * 根据用户ID和周期类型查询活动统计
     * @param userId 用户ID
     * @param periodType 周期类型：week-周，month-月，year-年
     * @param periodValue 周期值，如：2023-01，2023-W01
     * @return 活动统计对象
     */
    ActivityStats selectByUserIdAndPeriod(@Param("userId") Long userId, 
                                         @Param("periodType") String periodType, 
                                         @Param("periodValue") String periodValue);
    
    /**
     * 插入活动统计
     * @param activityStats 活动统计对象
     * @return 影响行数
     */
    int insert(ActivityStats activityStats);
    
    /**
     * 更新活动统计
     * @param activityStats 活动统计对象
     * @return 影响行数
     */
    int update(ActivityStats activityStats);
    
    /**
     * 删除活动统计
     * @param id 统计ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
} 