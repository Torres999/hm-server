package com.healthmanager.mapper;

import com.healthmanager.entity.HeartRateRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 心率记录Mapper接口
 */
@Repository
public interface HeartRateRecordMapper {
    /**
     * 根据ID查询心率记录
     * @param id 心率记录ID
     * @return 心率记录对象
     */
    HeartRateRecord selectById(@Param("id") Long id);
    
    /**
     * 根据运动记录ID查询心率记录列表
     * @param exerciseId 运动记录ID
     * @return 心率记录列表
     */
    List<HeartRateRecord> selectByExerciseId(@Param("exerciseId") Long exerciseId);
    
    /**
     * 批量插入心率记录
     * @param heartRateRecords 心率记录列表
     * @return 影响行数
     */
    int batchInsert(List<HeartRateRecord> heartRateRecords);
    
    /**
     * 插入心率记录
     * @param heartRateRecord 心率记录对象
     * @return 影响行数
     */
    int insert(HeartRateRecord heartRateRecord);
    
    /**
     * 更新心率记录
     * @param heartRateRecord 心率记录对象
     * @return 影响行数
     */
    int update(HeartRateRecord heartRateRecord);
    
    /**
     * 删除心率记录
     * @param id 心率记录ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 根据运动记录ID删除心率记录
     * @param exerciseId 运动记录ID
     * @return 影响行数
     */
    int deleteByExerciseId(@Param("exerciseId") Long exerciseId);
} 