package com.healthmanager.service;

import com.healthmanager.dto.ActivityStatsDTO;
import com.healthmanager.dto.HomeOverviewDTO;
import com.healthmanager.entity.Task;

import java.util.List;

/**
 * 首页Service接口
 */
public interface HomeService {
    /**
     * 获取首页概览数据
     * @param userId 用户ID
     * @return 首页概览数据
     */
    HomeOverviewDTO getHomeOverview(Long userId);
    
    /**
     * 获取今日任务
     * @param userId 用户ID
     * @return 任务列表
     */
    List<Task> getTodayTasks(Long userId);
    
    /**
     * 更新任务状态
     * @param taskId 任务ID
     * @param completed 完成状态
     * @return 任务对象
     */
    Task updateTaskStatus(Long taskId, Boolean completed);
    
    /**
     * 获取活动统计数据
     * @param userId 用户ID
     * @param period 时间周期，可选值：week(默认)、month、year
     * @return 活动统计数据
     */
    ActivityStatsDTO getActivityStats(Long userId, String period);
} 