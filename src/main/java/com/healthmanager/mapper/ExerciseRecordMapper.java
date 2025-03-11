package com.healthmanager.mapper;

import com.healthmanager.entity.ExerciseRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 运动记录Mapper接口
 */
@Repository
public interface ExerciseRecordMapper {
    /**
     * 根据ID查询运动记录
     * @param id 运动记录ID
     * @return 运动记录对象
     */
    ExerciseRecord selectById(@Param("id") Long id);
    
    /**
     * 根据用户ID查询运动记录列表
     * @param userId 用户ID
     * @return 运动记录列表
     */
    List<ExerciseRecord> selectByUserId(@Param("userId") Long userId);
    
    /**
     * 根据用户ID和日期范围查询运动记录列表
     * @param userId 用户ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 运动记录列表
     */
    List<ExerciseRecord> selectByUserIdAndDateRange(@Param("userId") Long userId, 
                                                   @Param("startDate") Date startDate, 
                                                   @Param("endDate") Date endDate);
    
    /**
     * 插入运动记录
     * @param exerciseRecord 运动记录对象
     * @return 影响行数
     */
    int insert(ExerciseRecord exerciseRecord);
    
    /**
     * 更新运动记录
     * @param exerciseRecord 运动记录对象
     * @return 影响行数
     */
    int update(ExerciseRecord exerciseRecord);
    
    /**
     * 删除运动记录
     * @param id 运动记录ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 统计用户在指定日期范围内的运动次数
     * @param userId 用户ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 运动次数
     */
    int countByUserIdAndDateRange(@Param("userId") Long userId, 
                                 @Param("startDate") Date startDate, 
                                 @Param("endDate") Date endDate);
    
    /**
     * 统计用户在指定日期范围内的运动总时长
     * @param userId 用户ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 运动总时长(分钟)
     */
    int sumDurationByUserIdAndDateRange(@Param("userId") Long userId, 
                                       @Param("startDate") Date startDate, 
                                       @Param("endDate") Date endDate);
} 