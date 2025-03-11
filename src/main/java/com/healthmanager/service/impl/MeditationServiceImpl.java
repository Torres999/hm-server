package com.healthmanager.service.impl;

import com.healthmanager.dto.MeditationCourseDetailDTO;
import com.healthmanager.entity.MeditationCategory;
import com.healthmanager.entity.MeditationCourse;
import com.healthmanager.entity.MeditationRecord;
import com.healthmanager.exception.ApiException;
import com.healthmanager.mapper.MeditationCategoryMapper;
import com.healthmanager.mapper.MeditationCourseMapper;
import com.healthmanager.mapper.MeditationRecordMapper;
import com.healthmanager.service.MeditationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 冥想Service实现类
 */
@Slf4j
@Service
public class MeditationServiceImpl implements MeditationService {

    @Autowired
    private MeditationCategoryMapper meditationCategoryMapper;

    @Autowired
    private MeditationCourseMapper meditationCourseMapper;

    @Autowired
    private MeditationRecordMapper meditationRecordMapper;

    @Override
    public List<MeditationCategory> getMeditationCategories() {
        log.info("获取冥想分类列表");
        return meditationCategoryMapper.selectAll();
    }

    @Override
    public MeditationCategory getMeditationCategory(Long id) {
        log.info("获取冥想分类详情，分类ID: {}", id);
        MeditationCategory category = meditationCategoryMapper.selectById(id);
        if (category == null) {
            log.error("冥想分类不存在，分类ID: {}", id);
            throw new ApiException("冥想分类不存在");
        }
        return category;
    }

    @Override
    public List<MeditationCourse> getMeditationCourses(Long categoryId) {
        if (categoryId != null) {
            log.info("获取指定分类的冥想课程列表，分类ID: {}", categoryId);
            return meditationCourseMapper.selectByCategoryId(categoryId);
        } else {
            log.info("获取所有冥想课程列表");
            return meditationCourseMapper.selectAll();
        }
    }

    @Override
    public MeditationCourseDetailDTO getMeditationCourseDetail(Long id) {
        log.info("获取冥想课程详情，课程ID: {}", id);
        MeditationCourse course = meditationCourseMapper.selectById(id);
        if (course == null) {
            log.error("冥想课程不存在，课程ID: {}", id);
            throw new ApiException("冥想课程不存在");
        }

        MeditationCourseDetailDTO courseDetailDTO = new MeditationCourseDetailDTO();
        courseDetailDTO.setId(course.getId());
        courseDetailDTO.setTitle(course.getTitle());
        courseDetailDTO.setDescription(course.getDescription());
        courseDetailDTO.setDuration(course.getDuration());
        courseDetailDTO.setImage(course.getImage());
        courseDetailDTO.setAudioUrl(course.getAudioUrl());
        courseDetailDTO.setCategoryId(course.getCategoryId());

        // 解析步骤，假设steps字段存储的是JSON格式的步骤数据
        // 这里简化处理，假设steps字段存储的是以逗号分隔的步骤字符串
        if (course.getSteps() != null && !course.getSteps().isEmpty()) {
            List<String> steps = Arrays.asList(course.getSteps().split(","));
            courseDetailDTO.setSteps(steps);
        }

        log.info("冥想课程详情获取成功，课程ID: {}, 标题: {}", id, course.getTitle());
        return courseDetailDTO;
    }

    @Override
    @Transactional
    public Long saveMeditationRecord(MeditationRecord meditationRecord) {
        log.info("保存冥想记录，用户ID: {}, 课程ID: {}", meditationRecord.getUserId(), meditationRecord.getCourseId());
        // 验证课程是否存在
        MeditationCourse course = meditationCourseMapper.selectById(meditationRecord.getCourseId());
        if (course == null) {
            log.error("冥想课程不存在，课程ID: {}", meditationRecord.getCourseId());
            throw new ApiException("冥想课程不存在");
        }

        // 保存冥想记录
        meditationRecordMapper.insert(meditationRecord);
        log.info("冥想记录保存成功，记录ID: {}", meditationRecord.getId());
        return meditationRecord.getId();
    }

    @Override
    public List<MeditationRecord> getMeditationRecords(Long userId) {
        log.info("获取用户冥想记录列表，用户ID: {}", userId);
        return meditationRecordMapper.selectByUserId(userId);
    }
} 