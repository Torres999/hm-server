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
import java.util.stream.Collectors;

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
        return meditationCategoryMapper.selectAll();
    }

    @Override
    public MeditationCategory getMeditationCategory(Long id) {
        MeditationCategory category = meditationCategoryMapper.selectById(id);
        if (category == null) {
            throw new ApiException("冥想分类不存在");
        }
        return category;
    }

    @Override
    public List<MeditationCourse> getMeditationCourses(Long categoryId) {
        if (categoryId != null) {
            return meditationCourseMapper.selectByCategoryId(categoryId);
        } else {
            return meditationCourseMapper.selectAll();
        }
    }

    @Override
    public MeditationCourseDetailDTO getMeditationCourseDetail(Long id) {
        MeditationCourse course = meditationCourseMapper.selectById(id);
        if (course == null) {
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

        return courseDetailDTO;
    }

    @Override
    @Transactional
    public Long saveMeditationRecord(MeditationRecord meditationRecord) {
        // 验证课程是否存在
        MeditationCourse course = meditationCourseMapper.selectById(meditationRecord.getCourseId());
        if (course == null) {
            throw new ApiException("冥想课程不存在");
        }

        // 保存冥想记录
        meditationRecordMapper.insert(meditationRecord);
        return meditationRecord.getId();
    }

    @Override
    public List<MeditationRecord> getMeditationRecords(Long userId) {
        return meditationRecordMapper.selectByUserId(userId);
    }
} 