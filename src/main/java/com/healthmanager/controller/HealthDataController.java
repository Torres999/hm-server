package com.healthmanager.controller;

import com.healthmanager.common.Result;
import com.healthmanager.entity.HealthData;
import com.healthmanager.service.HealthDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 健康数据Controller
 */
@Slf4j
@Api(tags = "健康数据相关接口")
@RestController
@RequestMapping("/health")
public class HealthDataController {
    
    @Autowired
    private HealthDataService healthDataService;
    
    @ApiOperation("获取健康数据")
    @GetMapping("/data")
    public Result<HealthData> getHealthData(@ApiParam(value = "用户ID", required = true) @RequestParam Long userId) {
        log.info("获取健康数据，用户ID: {}", userId);
        HealthData healthData = healthDataService.getLatestHealthDataByUserId(userId);
        return Result.success(healthData);
    }
    
    @ApiOperation("保存健康数据")
    @PostMapping("/data")
    public Result<HealthData> saveHealthData(@ApiParam(value = "健康数据", required = true) @RequestBody HealthData healthData) {
        log.info("保存健康数据，用户ID: {}", healthData.getUserId());
        HealthData savedHealthData = healthDataService.saveHealthData(healthData);
        return Result.success(savedHealthData);
    }
    
    @ApiOperation("更新健康数据")
    @PutMapping("/data/{id}")
    public Result<HealthData> updateHealthData(
            @ApiParam(value = "健康数据ID", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "健康数据", required = true) @RequestBody HealthData healthData) {
        log.info("更新健康数据，健康数据ID: {}, 用户ID: {}", id, healthData.getUserId());
        healthData.setId(id);
        HealthData updatedHealthData = healthDataService.updateHealthData(healthData);
        return Result.success(updatedHealthData);
    }
    
    @ApiOperation("删除健康数据")
    @DeleteMapping("/data/{id}")
    public Result<Boolean> deleteHealthData(@ApiParam(value = "健康数据ID", required = true) @PathVariable("id") Long id) {
        log.info("删除健康数据，健康数据ID: {}", id);
        boolean result = healthDataService.deleteHealthData(id);
        return Result.success(result);
    }
} 