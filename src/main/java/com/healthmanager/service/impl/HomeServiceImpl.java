package com.healthmanager.service.impl;

import com.healthmanager.dto.ActivityStatsDTO;
import com.healthmanager.dto.HomeOverviewDTO;
import com.healthmanager.entity.*;
import com.healthmanager.exception.ApiException;
import com.healthmanager.mapper.*;
import com.healthmanager.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 首页Service实现类
 */
@Slf4j
@Service
public class HomeServiceImpl implements HomeService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private HealthDataMapper healthDataMapper;
    
    @Autowired
    private TaskMapper taskMapper;
    
    @Autowired
    private ExerciseRecordMapper exerciseRecordMapper;
    
    @Autowired
    private MeditationRecordMapper meditationRecordMapper;
    
    @Autowired
    private ActivityStatsMapper activityStatsMapper;
    
    @Override
    public HomeOverviewDTO getHomeOverview(Long userId) {
        // 检查用户是否存在
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new ApiException("用户不存在");
        }
        
        HomeOverviewDTO homeOverviewDTO = new HomeOverviewDTO();
        
        // 设置用户信息
        HomeOverviewDTO.UserInfoDTO userInfoDTO = new HomeOverviewDTO.UserInfoDTO();
        userInfoDTO.setNickName(user.getNickName());
        userInfoDTO.setAvatarUrl(user.getAvatarUrl());
        homeOverviewDTO.setUserInfo(userInfoDTO);
        
        // 设置健康数据
        HealthData healthData = healthDataMapper.selectLatestByUserId(userId);
        HomeOverviewDTO.HealthDataDTO healthDataDTO = new HomeOverviewDTO.HealthDataDTO();
        if (healthData != null) {
            healthDataDTO.setSteps(healthData.getSteps());
            healthDataDTO.setHeartRate(healthData.getHeartRate());
            healthDataDTO.setCalories(healthData.getCalories());
            healthDataDTO.setSleep(healthData.getSleepHours() != null ? healthData.getSleepHours().doubleValue() : null);
            
            // 计算步数变化百分比
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(healthData.getRecordDate());
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            Date yesterdayDate = calendar.getTime();
            
            HealthData yesterdayHealthData = healthDataMapper.selectByUserIdAndDate(userId, yesterdayDate);
            if (yesterdayHealthData != null && yesterdayHealthData.getSteps() > 0) {
                int change = (int) (((double) healthData.getSteps() / yesterdayHealthData.getSteps() - 1) * 100);
                healthDataDTO.setStepsChange(change);
            } else {
                healthDataDTO.setStepsChange(0);
            }
        } else {
            healthDataDTO.setSteps(0);
            healthDataDTO.setHeartRate(0);
            healthDataDTO.setCalories(0);
            healthDataDTO.setSleep(0.0);
            healthDataDTO.setStepsChange(0);
        }
        homeOverviewDTO.setHealthData(healthDataDTO);
        
        // 设置活动图表数据
        HomeOverviewDTO.ActivityChartDTO activityChartDTO = new HomeOverviewDTO.ActivityChartDTO();
        List<String> dates = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        
        // 获取最近7天的数据
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        
        Date endDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, -6);
        Date startDate = calendar.getTime();
        
        List<HealthData> healthDataList = healthDataMapper.selectByUserIdAndDateRange(userId, startDate, endDate);
        Map<String, Integer> dataMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        
        // 初始化日期和数据
        calendar.setTime(startDate);
        for (int i = 0; i < 7; i++) {
            String dateStr = sdf.format(calendar.getTime());
            dates.add(dateStr);
            dataMap.put(dateStr, 0);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        // 填充实际数据
        for (HealthData data : healthDataList) {
            String dateStr = sdf.format(data.getRecordDate());
            dataMap.put(dateStr, data.getSteps());
        }
        
        // 按日期顺序添加数据
        for (String date : dates) {
            values.add(dataMap.get(date));
        }
        
        activityChartDTO.setDates(dates);
        activityChartDTO.setValues(values);
        homeOverviewDTO.setActivityChart(activityChartDTO);
        
        return homeOverviewDTO;
    }
    
    @Override
    public List<Task> getTodayTasks(Long userId) {
        // 检查用户是否存在
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new ApiException("用户不存在");
        }
        
        // 获取今日日期
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date today = calendar.getTime();
        
        return taskMapper.selectByUserIdAndDate(userId, today);
    }
    
    @Override
    public Task updateTaskStatus(Long taskId, Boolean completed) {
        if (taskId == null) {
            throw new ApiException("任务ID不能为空");
        }
        
        Task task = taskMapper.selectById(taskId);
        if (task == null) {
            throw new ApiException("任务不存在");
        }
        
        int result = taskMapper.updateCompletedStatus(taskId, completed);
        if (result > 0) {
            return taskMapper.selectById(taskId);
        } else {
            throw new ApiException("更新任务状态失败");
        }
    }
    
    @Override
    public ActivityStatsDTO getActivityStats(Long userId, String period) {
        // 检查用户是否存在
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new ApiException("用户不存在");
        }
        
        // 默认为周期统计
        if (period == null || period.isEmpty()) {
            period = "week";
        }
        
        // 获取统计周期的开始和结束日期
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endDate = calendar.getTime();
        
        Date startDate;
        String periodValue;
        SimpleDateFormat sdf;
        
        switch (period) {
            case "week":
                calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                startDate = calendar.getTime();
                sdf = new SimpleDateFormat("yyyy-'W'ww");
                periodValue = sdf.format(calendar.getTime());
                break;
            case "month":
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                startDate = calendar.getTime();
                sdf = new SimpleDateFormat("yyyy-MM");
                periodValue = sdf.format(calendar.getTime());
                break;
            case "year":
                calendar.set(Calendar.DAY_OF_YEAR, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                startDate = calendar.getTime();
                sdf = new SimpleDateFormat("yyyy");
                periodValue = sdf.format(calendar.getTime());
                break;
            default:
                throw new ApiException("无效的统计周期");
        }
        
        // 查询活动统计数据
        ActivityStats activityStats = activityStatsMapper.selectByUserIdAndPeriod(userId, period, periodValue);
        
        // 如果没有统计数据，则计算并保存
        if (activityStats == null) {
            activityStats = calculateActivityStats(userId, period, periodValue, startDate, endDate);
        }
        
        // 转换为DTO
        return convertToActivityStatsDTO(activityStats);
    }
    
    /**
     * 计算活动统计数据
     */
    private ActivityStats calculateActivityStats(Long userId, String periodType, String periodValue, Date startDate, Date endDate) {
        ActivityStats activityStats = new ActivityStats();
        activityStats.setUserId(userId);
        activityStats.setPeriodType(periodType);
        activityStats.setPeriodValue(periodValue);
        
        // 计算运动统计数据
        int exerciseSessions = exerciseRecordMapper.countByUserIdAndDateRange(userId, startDate, endDate);
        int exerciseMinutes = exerciseRecordMapper.sumDurationByUserIdAndDateRange(userId, startDate, endDate);
        
        activityStats.setExerciseSessions(exerciseSessions);
        activityStats.setExerciseMinutes(exerciseMinutes);
        
        // 计算冥想统计数据
        int meditationSessions = meditationRecordMapper.countByUserIdAndDateRange(userId, startDate, endDate);
        int meditationMinutes = meditationRecordMapper.sumDurationByUserIdAndDateRange(userId, startDate, endDate);
        int meditationStreak = meditationRecordMapper.selectMeditationStreak(userId);
        
        activityStats.setMeditationSessions(meditationSessions);
        activityStats.setMeditationMinutes(meditationMinutes);
        activityStats.setMeditationStreak(meditationStreak);
        
        // 计算步数统计数据
        List<HealthData> healthDataList = healthDataMapper.selectByUserIdAndDateRange(userId, startDate, endDate);
        int stepsTotal = 0;
        int maxSteps = 0;
        String bestDay = null;
        SimpleDateFormat sdf = new SimpleDateFormat("E", Locale.CHINESE);
        
        for (HealthData healthData : healthDataList) {
            stepsTotal += healthData.getSteps();
            if (healthData.getSteps() > maxSteps) {
                maxSteps = healthData.getSteps();
                bestDay = sdf.format(healthData.getRecordDate());
            }
        }
        
        int dayCount = healthDataList.size() > 0 ? healthDataList.size() : 1;
        int stepsDailyAverage = stepsTotal / dayCount;
        
        activityStats.setStepsTotal(stepsTotal);
        activityStats.setStepsDailyAverage(stepsDailyAverage);
        activityStats.setStepsBestDay(bestDay);
        
        // 保存统计数据
        activityStatsMapper.insert(activityStats);
        
        return activityStats;
    }
    
    /**
     * 将ActivityStats转换为ActivityStatsDTO
     */
    private ActivityStatsDTO convertToActivityStatsDTO(ActivityStats activityStats) {
        ActivityStatsDTO activityStatsDTO = new ActivityStatsDTO();
        
        // 设置运动统计数据
        ActivityStatsDTO.ExerciseStatsDTO exerciseStatsDTO = new ActivityStatsDTO.ExerciseStatsDTO();
        exerciseStatsDTO.setTotalSessions(activityStats.getExerciseSessions());
        exerciseStatsDTO.setTotalMinutes(activityStats.getExerciseMinutes());
        exerciseStatsDTO.setTotalCalories(activityStats.getExerciseCalories());
        exerciseStatsDTO.setTotalDistance(activityStats.getExerciseDistance());
        activityStatsDTO.setExercise(exerciseStatsDTO);
        
        // 设置冥想统计数据
        ActivityStatsDTO.MeditationStatsDTO meditationStatsDTO = new ActivityStatsDTO.MeditationStatsDTO();
        meditationStatsDTO.setTotalSessions(activityStats.getMeditationSessions());
        meditationStatsDTO.setTotalMinutes(activityStats.getMeditationMinutes());
        meditationStatsDTO.setStreak(activityStats.getMeditationStreak());
        activityStatsDTO.setMeditation(meditationStatsDTO);
        
        // 设置步数统计数据
        ActivityStatsDTO.StepsStatsDTO stepsStatsDTO = new ActivityStatsDTO.StepsStatsDTO();
        stepsStatsDTO.setTotal(activityStats.getStepsTotal());
        stepsStatsDTO.setDailyAverage(activityStats.getStepsDailyAverage());
        stepsStatsDTO.setBestDay(activityStats.getStepsBestDay());
        activityStatsDTO.setSteps(stepsStatsDTO);
        
        return activityStatsDTO;
    }
} 