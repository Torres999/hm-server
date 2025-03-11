package com.healthmanager.controller;

import com.healthmanager.common.Result;
import com.healthmanager.dto.ExerciseDetailDTO;
import com.healthmanager.entity.ExerciseRecord;
import com.healthmanager.service.ExerciseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 运动记录Controller
 */
@Slf4j
@Api(tags = "运动相关接口")
@RestController
@RequestMapping("/exercise")
public class ExerciseController {
    
    @Autowired
    private ExerciseService exerciseService;
    
    @ApiOperation("获取运动记录列表")
    @GetMapping("/records")
    public Result<List<ExerciseRecord>> getExerciseRecords(@ApiParam(value = "用户ID", required = true) @RequestParam Long userId) {
        log.info("获取运动记录列表，用户ID: {}", userId);
        List<ExerciseRecord> exerciseRecords = exerciseService.getExerciseRecords(userId);
        return Result.success(exerciseRecords);
    }
    
    @ApiOperation("获取运动记录详情")
    @GetMapping("/records/{id}")
    public Result<ExerciseDetailDTO> getExerciseDetail(@ApiParam(value = "运动记录ID", required = true) @PathVariable("id") Long id) {
        log.info("获取运动记录详情，记录ID: {}", id);
        ExerciseDetailDTO exerciseDetailDTO = exerciseService.getExerciseDetail(id);
        return Result.success(exerciseDetailDTO);
    }
    
    @ApiOperation("保存运动记录")
    @PostMapping("/records")
    public Result<Long> saveExerciseRecord(@ApiParam(value = "运动记录详情", required = true) @RequestBody ExerciseDetailDTO exerciseDetailDTO) {
        log.info("保存运动记录，运动类型: {}", exerciseDetailDTO.getType());
        Long id = exerciseService.saveExerciseRecord(exerciseDetailDTO);
        return Result.success(id);
    }
    
    @ApiOperation("更新运动记录")
    @PutMapping("/records/{id}")
    public Result<Boolean> updateExerciseRecord(
            @ApiParam(value = "运动记录ID", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "运动记录详情", required = true) @RequestBody ExerciseDetailDTO exerciseDetailDTO) {
        log.info("更新运动记录，记录ID: {}", id);
        boolean result = exerciseService.updateExerciseRecord(id, exerciseDetailDTO);
        return Result.success(result);
    }
    
    @ApiOperation("删除运动记录")
    @DeleteMapping("/records/{id}")
    public Result<Boolean> deleteExerciseRecord(@ApiParam(value = "运动记录ID", required = true) @PathVariable("id") Long id) {
        log.info("删除运动记录，记录ID: {}", id);
        boolean result = exerciseService.deleteExerciseRecord(id);
        return Result.success(result);
    }
} 