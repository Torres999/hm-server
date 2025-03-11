package com.healthmanager.controller;

import com.healthmanager.common.Result;
import com.healthmanager.dto.ActivityStatsDTO;
import com.healthmanager.dto.HomeOverviewDTO;
import com.healthmanager.entity.Task;
import com.healthmanager.service.HomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 首页Controller
 */
@Slf4j
@Api(tags = "首页相关接口")
@RestController
@RequestMapping("/home")
public class HomeController {
    
    @Autowired
    private HomeService homeService;
    
    @ApiOperation("获取首页概览数据")
    @GetMapping("/overview")
    public Result<HomeOverviewDTO> getHomeOverview(@ApiParam(value = "用户ID", required = true) @RequestParam Long userId) {
        log.info("获取首页概览数据，用户ID: {}", userId);
        HomeOverviewDTO homeOverviewDTO = homeService.getHomeOverview(userId);
        return Result.success(homeOverviewDTO);
    }
    
    @ApiOperation("获取今日任务")
    @GetMapping("/tasks")
    public Result<List<Task>> getTodayTasks(@ApiParam(value = "用户ID", required = true) @RequestParam Long userId) {
        log.info("获取今日任务，用户ID: {}", userId);
        List<Task> tasks = homeService.getTodayTasks(userId);
        return Result.success(tasks);
    }
    
    @ApiOperation("更新任务状态")
    @PutMapping("/tasks/{id}")
    public Result<Task> updateTaskStatus(
            @ApiParam(value = "任务ID", required = true) @PathVariable("id") Long taskId,
            @ApiParam(value = "任务状态", required = true) @RequestBody Map<String, Boolean> statusMap) {
        Boolean completed = statusMap.get("completed");
        log.info("更新任务状态，任务ID: {}，完成状态: {}", taskId, completed);
        if (completed == null) {
            return Result.error(400, "任务状态不能为空");
        }
        Task task = homeService.updateTaskStatus(taskId, completed);
        return Result.success(task);
    }
    
    @ApiOperation("获取活动统计数据")
    @GetMapping("/activity-stats")
    public Result<ActivityStatsDTO> getActivityStats(
            @ApiParam(value = "用户ID", required = true) @RequestParam Long userId,
            @ApiParam(value = "时间周期，可选值：week(默认)、month、year") @RequestParam(required = false) String period) {
        log.info("获取活动统计数据，用户ID: {}，时间周期: {}", userId, period);
        ActivityStatsDTO activityStatsDTO = homeService.getActivityStats(userId, period);
        return Result.success(activityStatsDTO);
    }
} 