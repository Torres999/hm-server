package com.healthmanager.service;

import com.healthmanager.dto.MeditationCourseDetailDTO;
import com.healthmanager.entity.MeditationCategory;
import com.healthmanager.entity.MeditationCourse;
import com.healthmanager.entity.MeditationRecord;

import java.util.List;

/**
 * 冥想Service接口
 */
public interface MeditationService {
    /**
     * 获取冥想分类列表
     * @return 冥想分类列表
     */
    List<MeditationCategory> getMeditationCategories();
    
    /**
     * 获取冥想分类详情
     * @param id 分类ID
     * @return 冥想分类
     */
    MeditationCategory getMeditationCategory(Long id);
    
    /**
     * 获取冥想课程列表
     * @param categoryId 分类ID，可为空
     * @return 冥想课程列表
     */
    List<MeditationCourse> getMeditationCourses(Long categoryId);
    
    /**
     * 获取冥想课程详情
     * @param id 课程ID
     * @return 冥想课程详情
     */
    MeditationCourseDetailDTO getMeditationCourseDetail(Long id);
    
    /**
     * 保存冥想记录
     * @param meditationRecord 冥想记录
     * @return 冥想记录ID
     */
    Long saveMeditationRecord(MeditationRecord meditationRecord);
    
    /**
     * 获取用户冥想记录列表
     * @param userId 用户ID
     * @return 冥想记录列表
     */
    List<MeditationRecord> getMeditationRecords(Long userId);
} 