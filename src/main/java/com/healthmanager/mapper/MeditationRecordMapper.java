package com.healthmanager.mapper;

import com.healthmanager.entity.MeditationRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 冥想记录Mapper接口
 */
@Repository
public interface MeditationRecordMapper {
    /**
     * 根据ID查询冥想记录
     * @param id 冥想记录ID
     * @return 冥想记录对象
     */
    MeditationRecord selectById(@Param("id") Long id);
    
    /**
     * 根据用户ID查询冥想记录列表
     * @param userId 用户ID
     * @return 冥想记录列表
     */
    List<MeditationRecord> selectByUserId(@Param("userId") Long userId);
    
    /**
     * 根据用户ID和日期范围查询冥想记录列表
     * @param userId 用户ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 冥想记录列表
     */
    List<MeditationRecord> selectByUserIdAndDateRange(@Param("userId") Long userId, 
                                                     @Param("startDate") Date startDate, 
                                                     @Param("endDate") Date endDate);
    
    /**
     * 插入冥想记录
     * @param meditationRecord 冥想记录对象
     * @return 影响行数
     */
    int insert(MeditationRecord meditationRecord);
    
    /**
     * 更新冥想记录
     * @param meditationRecord 冥想记录对象
     * @return 影响行数
     */
    int update(MeditationRecord meditationRecord);
    
    /**
     * 删除冥想记录
     * @param id 冥想记录ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 统计用户在指定日期范围内的冥想次数
     * @param userId 用户ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 冥想次数
     */
    int countByUserIdAndDateRange(@Param("userId") Long userId, 
                                 @Param("startDate") Date startDate, 
                                 @Param("endDate") Date endDate);
    
    /**
     * 统计用户在指定日期范围内的冥想总时长
     * @param userId 用户ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 冥想总时长(分钟)
     */
    int sumDurationByUserIdAndDateRange(@Param("userId") Long userId, 
                                       @Param("startDate") Date startDate, 
                                       @Param("endDate") Date endDate);
    
    /**
     * 查询用户最近的连续冥想天数
     * @param userId 用户ID
     * @return 连续冥想天数
     */
    int selectMeditationStreak(@Param("userId") Long userId);
} 