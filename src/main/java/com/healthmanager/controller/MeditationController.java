package com.healthmanager.controller;

import com.healthmanager.common.Result;
import com.healthmanager.dto.MeditationCourseDetailDTO;
import com.healthmanager.entity.MeditationCategory;
import com.healthmanager.entity.MeditationCourse;
import com.healthmanager.entity.MeditationRecord;
import com.healthmanager.service.MeditationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 冥想Controller
 */
@Slf4j
@Api(tags = "冥想相关接口")
@RestController
@RequestMapping("/meditation")
public class MeditationController {
    
    @Autowired
    private MeditationService meditationService;
    
    @ApiOperation("获取冥想分类列表")
    @GetMapping("/categories")
    public Result<List<MeditationCategory>> getMeditationCategories() {
        log.info("获取冥想分类列表");
        List<MeditationCategory> categories = meditationService.getMeditationCategories();
        return Result.success(categories);
    }
    
    @ApiOperation("获取冥想分类详情")
    @GetMapping("/categories/{id}")
    public Result<MeditationCategory> getMeditationCategory(@ApiParam(value = "分类ID", required = true) @PathVariable("id") Long id) {
        log.info("获取冥想分类详情，分类ID: {}", id);
        MeditationCategory category = meditationService.getMeditationCategory(id);
        return Result.success(category);
    }
    
    @ApiOperation("获取冥想课程列表")
    @GetMapping("/courses")
    public Result<List<MeditationCourse>> getMeditationCourses(@ApiParam(value = "分类ID") @RequestParam(required = false) Long categoryId) {
        log.info("获取冥想课程列表，分类ID: {}", categoryId);
        List<MeditationCourse> courses = meditationService.getMeditationCourses(categoryId);
        return Result.success(courses);
    }
    
    @ApiOperation("获取冥想课程详情")
    @GetMapping("/courses/{id}")
    public Result<MeditationCourseDetailDTO> getMeditationCourseDetail(@ApiParam(value = "课程ID", required = true) @PathVariable("id") Long id) {
        log.info("获取冥想课程详情，课程ID: {}", id);
        MeditationCourseDetailDTO courseDetail = meditationService.getMeditationCourseDetail(id);
        return Result.success(courseDetail);
    }
    
    @ApiOperation("保存冥想记录")
    @PostMapping("/records")
    public Result<Long> saveMeditationRecord(@ApiParam(value = "冥想记录", required = true) @RequestBody MeditationRecord meditationRecord) {
        log.info("保存冥想记录，用户ID: {}, 课程ID: {}", meditationRecord.getUserId(), meditationRecord.getCourseId());
        Long id = meditationService.saveMeditationRecord(meditationRecord);
        return Result.success(id);
    }
    
    @ApiOperation("获取用户冥想记录列表")
    @GetMapping("/records")
    public Result<List<MeditationRecord>> getMeditationRecords(@ApiParam(value = "用户ID", required = true) @RequestParam Long userId) {
        log.info("获取用户冥想记录列表，用户ID: {}", userId);
        List<MeditationRecord> records = meditationService.getMeditationRecords(userId);
        return Result.success(records);
    }
} 