package com.healthmanager.service;

import com.healthmanager.dto.ExerciseDetailDTO;
import com.healthmanager.entity.ExerciseRecord;

import java.util.List;

/**
 * 运动记录Service接口
 */
public interface ExerciseService {
    /**
     * 获取运动记录列表
     * @param userId 用户ID
     * @return 运动记录列表
     */
    List<ExerciseRecord> getExerciseRecords(Long userId);
    
    /**
     * 获取运动记录详情
     * @param id 运动记录ID
     * @return 运动记录详情
     */
    ExerciseDetailDTO getExerciseDetail(Long id);
    
    /**
     * 保存运动记录
     * @param exerciseDetailDTO 运动记录详情
     * @return 运动记录ID
     */
    Long saveExerciseRecord(ExerciseDetailDTO exerciseDetailDTO);
    
    /**
     * 更新运动记录
     * @param id 运动记录ID
     * @param exerciseDetailDTO 运动记录详情
     * @return 是否成功
     */
    boolean updateExerciseRecord(Long id, ExerciseDetailDTO exerciseDetailDTO);
    
    /**
     * 删除运动记录
     * @param id 运动记录ID
     * @return 是否成功
     */
    boolean deleteExerciseRecord(Long id);
} 