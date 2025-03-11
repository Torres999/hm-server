package com.healthmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthmanager.dto.ActivityStatsDTO;
import com.healthmanager.dto.HomeOverviewDTO;
import com.healthmanager.entity.Task;
import com.healthmanager.service.HomeService;
import com.healthmanager.exception.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * HomeController测试类
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HomeService homeService;

    private HomeOverviewDTO homeOverviewDTO;
    private List<Task> taskList;
    private Task task;
    private ActivityStatsDTO activityStatsDTO;

    @BeforeEach
    void setUp() {
        // 准备HomeOverviewDTO测试数据
        homeOverviewDTO = new HomeOverviewDTO();
        
        HomeOverviewDTO.UserInfoDTO userInfoDTO = new HomeOverviewDTO.UserInfoDTO();
        userInfoDTO.setNickName("测试用户");
        userInfoDTO.setAvatarUrl("http://example.com/avatar.jpg");
        homeOverviewDTO.setUserInfo(userInfoDTO);
        
        HomeOverviewDTO.HealthDataDTO healthDataDTO = new HomeOverviewDTO.HealthDataDTO();
        healthDataDTO.setSteps(8000);
        healthDataDTO.setStepsChange(5);
        healthDataDTO.setHeartRate(75);
        healthDataDTO.setCalories(300);
        healthDataDTO.setSleep(7.5);
        homeOverviewDTO.setHealthData(healthDataDTO);
        
        HomeOverviewDTO.ActivityChartDTO activityChartDTO = new HomeOverviewDTO.ActivityChartDTO();
        activityChartDTO.setDates(Arrays.asList("05-01", "05-02", "05-03", "05-04", "05-05", "05-06", "05-07"));
        activityChartDTO.setValues(Arrays.asList(6000, 7000, 8000, 7500, 9000, 8500, 8000));
        homeOverviewDTO.setActivityChart(activityChartDTO);
        
        // 准备Task测试数据
        taskList = new ArrayList<>();
        
        task = new Task();
        task.setId(1L);
        task.setUserId(1L);
        task.setTitle("晨跑30分钟");
        task.setDescription("在小区内慢跑30分钟");
        task.setType("exercise");
        task.setCompleted(false);
        task.setTaskDate(new Date());
        task.setCreateTime(new Date());
        task.setUpdateTime(new Date());
        taskList.add(task);
        
        Task task2 = new Task();
        task2.setId(2L);
        task2.setUserId(1L);
        task2.setTitle("冥想15分钟");
        task2.setDescription("专注呼吸冥想15分钟");
        task2.setType("meditation");
        task2.setCompleted(true);
        task2.setTaskDate(new Date());
        task2.setCreateTime(new Date());
        task2.setUpdateTime(new Date());
        taskList.add(task2);
        
        // 准备ActivityStatsDTO测试数据
        activityStatsDTO = new ActivityStatsDTO();
        
        ActivityStatsDTO.ExerciseStatsDTO exerciseStatsDTO = new ActivityStatsDTO.ExerciseStatsDTO();
        exerciseStatsDTO.setTotalSessions(10);
        exerciseStatsDTO.setTotalMinutes(300);
        exerciseStatsDTO.setTotalCalories(1500);
        exerciseStatsDTO.setTotalDistance(new BigDecimal("15.5"));
        activityStatsDTO.setExercise(exerciseStatsDTO);
        
        ActivityStatsDTO.MeditationStatsDTO meditationStatsDTO = new ActivityStatsDTO.MeditationStatsDTO();
        meditationStatsDTO.setTotalSessions(5);
        meditationStatsDTO.setTotalMinutes(75);
        meditationStatsDTO.setStreak(3);
        activityStatsDTO.setMeditation(meditationStatsDTO);
        
        ActivityStatsDTO.StepsStatsDTO stepsStatsDTO = new ActivityStatsDTO.StepsStatsDTO();
        stepsStatsDTO.setTotal(56000);
        stepsStatsDTO.setDailyAverage(8000);
        stepsStatsDTO.setBestDay("05-05");
        activityStatsDTO.setSteps(stepsStatsDTO);
    }

    /**
     * 测试获取首页概览数据
     */
    @Test
    @WithMockUser(username = "test_user", roles = {"USER"})
    void getHomeOverview_ShouldReturnHomeOverviewDTO_WhenUserExists() throws Exception {
        // 准备
        Long userId = 1L;
        when(homeService.getHomeOverview(anyLong())).thenReturn(homeOverviewDTO);

        // 执行和验证
        mockMvc.perform(get("/home/overview")
                .param("userId", userId.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.userInfo.nickName").value("测试用户"))
                .andExpect(jsonPath("$.data.userInfo.avatarUrl").value("http://example.com/avatar.jpg"))
                .andExpect(jsonPath("$.data.healthData.steps").value(8000))
                .andExpect(jsonPath("$.data.healthData.stepsChange").value(5))
                .andExpect(jsonPath("$.data.healthData.heartRate").value(75))
                .andExpect(jsonPath("$.data.healthData.calories").value(300))
                .andExpect(jsonPath("$.data.healthData.sleep").value(7.5))
                .andExpect(jsonPath("$.data.activityChart.dates").isArray())
                .andExpect(jsonPath("$.data.activityChart.dates[0]").value("05-01"))
                .andExpect(jsonPath("$.data.activityChart.values").isArray())
                .andExpect(jsonPath("$.data.activityChart.values[0]").value(6000));
    }

    /**
     * 测试获取今日任务
     */
    @Test
    @WithMockUser(username = "test_user", roles = {"USER"})
    void getTodayTasks_ShouldReturnTaskList_WhenUserExists() throws Exception {
        // 准备
        Long userId = 1L;
        when(homeService.getTodayTasks(anyLong())).thenReturn(taskList);

        // 执行和验证
        mockMvc.perform(get("/home/tasks")
                .param("userId", userId.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].userId").value(1))
                .andExpect(jsonPath("$.data[0].title").value("晨跑30分钟"))
                .andExpect(jsonPath("$.data[0].description").value("在小区内慢跑30分钟"))
                .andExpect(jsonPath("$.data[0].type").value("exercise"))
                .andExpect(jsonPath("$.data[0].completed").value(false))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].title").value("冥想15分钟"))
                .andExpect(jsonPath("$.data[1].type").value("meditation"))
                .andExpect(jsonPath("$.data[1].completed").value(true));
    }

    /**
     * 测试更新任务状态 - 成功情况
     */
    @Test
    @WithMockUser(username = "test_user", roles = {"USER"})
    void updateTaskStatus_ShouldReturnUpdatedTask_WhenTaskExists() throws Exception {
        // 准备
        Long taskId = 1L;
        Map<String, Boolean> statusMap = new HashMap<>();
        statusMap.put("completed", true);
        
        // 更新任务状态
        Task updatedTask = new Task();
        updatedTask.setId(taskId);
        updatedTask.setUserId(1L);
        updatedTask.setTitle("晨跑30分钟");
        updatedTask.setDescription("在小区内慢跑30分钟");
        updatedTask.setType("exercise");
        updatedTask.setCompleted(true); // 已更新为完成状态
        updatedTask.setTaskDate(new Date());
        updatedTask.setCreateTime(new Date());
        updatedTask.setUpdateTime(new Date());
        
        when(homeService.updateTaskStatus(anyLong(), anyBoolean())).thenReturn(updatedTask);

        // 执行和验证
        mockMvc.perform(put("/home/tasks/{id}", taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(statusMap)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.userId").value(1))
                .andExpect(jsonPath("$.data.title").value("晨跑30分钟"))
                .andExpect(jsonPath("$.data.type").value("exercise"))
                .andExpect(jsonPath("$.data.completed").value(true));
    }

    /**
     * 测试更新任务状态 - 状态为空情况
     */
    @Test
    @WithMockUser(username = "test_user", roles = {"USER"})
    void updateTaskStatus_ShouldReturnError_WhenCompletedIsNull() throws Exception {
        // 准备
        Long taskId = 1L;
        Map<String, Boolean> statusMap = new HashMap<>();
        // 不设置completed字段

        // 执行和验证
        mockMvc.perform(put("/home/tasks/{id}", taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(statusMap)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("任务状态不能为空"));
    }

    /**
     * 测试获取活动统计数据 - 默认周期(week)
     */
    @Test
    @WithMockUser(username = "test_user", roles = {"USER"})
    void getActivityStats_ShouldReturnActivityStatsDTO_WithDefaultPeriod() throws Exception {
        // 准备
        Long userId = 1L;
        // 明确指定当传入任何userId和任何period参数时，返回activityStatsDTO
        when(homeService.getActivityStats(eq(userId), any())).thenReturn(activityStatsDTO);

        // 执行和验证
        mockMvc.perform(get("/home/activity-stats")
                .param("userId", userId.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.exercise").exists())
                .andExpect(jsonPath("$.data.meditation").exists())
                .andExpect(jsonPath("$.data.steps").exists())
                .andExpect(jsonPath("$.data.exercise.totalSessions").value(10))
                .andExpect(jsonPath("$.data.exercise.totalMinutes").value(300))
                .andExpect(jsonPath("$.data.exercise.totalCalories").value(1500))
                .andExpect(jsonPath("$.data.exercise.totalDistance").value(15.5))
                .andExpect(jsonPath("$.data.meditation.totalSessions").value(5))
                .andExpect(jsonPath("$.data.meditation.totalMinutes").value(75))
                .andExpect(jsonPath("$.data.meditation.streak").value(3))
                .andExpect(jsonPath("$.data.steps.total").value(56000))
                .andExpect(jsonPath("$.data.steps.dailyAverage").value(8000))
                .andExpect(jsonPath("$.data.steps.bestDay").value("05-05"));
    }

    /**
     * 测试获取活动统计数据 - 指定周期(month)
     */
    @Test
    @WithMockUser(username = "test_user", roles = {"USER"})
    void getActivityStats_ShouldReturnActivityStatsDTO_WithSpecifiedPeriod() throws Exception {
        // 准备
        Long userId = 1L;
        String period = "month";
        // 明确指定当传入特定userId和特定period参数时，返回activityStatsDTO
        when(homeService.getActivityStats(eq(userId), eq(period))).thenReturn(activityStatsDTO);

        // 执行和验证
        mockMvc.perform(get("/home/activity-stats")
                .param("userId", userId.toString())
                .param("period", period)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.exercise").exists())
                .andExpect(jsonPath("$.data.meditation").exists())
                .andExpect(jsonPath("$.data.steps").exists())
                .andExpect(jsonPath("$.data.exercise.totalSessions").value(10))
                .andExpect(jsonPath("$.data.exercise.totalMinutes").value(300))
                .andExpect(jsonPath("$.data.exercise.totalCalories").value(1500))
                .andExpect(jsonPath("$.data.exercise.totalDistance").value(15.5))
                .andExpect(jsonPath("$.data.meditation.totalSessions").value(5))
                .andExpect(jsonPath("$.data.meditation.totalMinutes").value(75))
                .andExpect(jsonPath("$.data.meditation.streak").value(3))
                .andExpect(jsonPath("$.data.steps.total").value(56000))
                .andExpect(jsonPath("$.data.steps.dailyAverage").value(8000))
                .andExpect(jsonPath("$.data.steps.bestDay").value("05-05"));
    }

    /**
     * 测试获取首页概览数据 - 用户不存在情况
     */
    @Test
    @WithMockUser(username = "test_user", roles = {"USER"})
    void getHomeOverview_ShouldReturnError_WhenUserNotExists() throws Exception {
        // 准备
        Long userId = 999L;
        when(homeService.getHomeOverview(eq(userId))).thenThrow(new ApiException("用户不存在"));

        // 执行和验证
        mockMvc.perform(get("/home/overview")
                .param("userId", userId.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("用户不存在"));
    }

    /**
     * 测试获取今日任务 - 用户不存在情况
     */
    @Test
    @WithMockUser(username = "test_user", roles = {"USER"})
    void getTodayTasks_ShouldReturnError_WhenUserNotExists() throws Exception {
        // 准备
        Long userId = 999L;
        when(homeService.getTodayTasks(eq(userId))).thenThrow(new ApiException("用户不存在"));

        // 执行和验证
        mockMvc.perform(get("/home/tasks")
                .param("userId", userId.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("用户不存在"));
    }

    /**
     * 测试更新任务状态 - 任务不存在情况
     */
    @Test
    @WithMockUser(username = "test_user", roles = {"USER"})
    void updateTaskStatus_ShouldReturnError_WhenTaskNotExists() throws Exception {
        // 准备
        Long taskId = 999L;
        Map<String, Boolean> statusMap = new HashMap<>();
        statusMap.put("completed", true);
        
        when(homeService.updateTaskStatus(eq(taskId), anyBoolean())).thenThrow(new ApiException("任务不存在"));

        // 执行和验证
        mockMvc.perform(put("/home/tasks/{id}", taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(statusMap)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("任务不存在"));
    }

    /**
     * 测试获取活动统计数据 - 无效周期情况
     */
    @Test
    @WithMockUser(username = "test_user", roles = {"USER"})
    void getActivityStats_ShouldReturnError_WhenInvalidPeriod() throws Exception {
        // 准备
        Long userId = 1L;
        String period = "invalid_period";
        when(homeService.getActivityStats(anyLong(), eq(period))).thenThrow(new ApiException("无效的统计周期"));

        // 执行和验证
        mockMvc.perform(get("/home/activity-stats")
                .param("userId", userId.toString())
                .param("period", period)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("无效的统计周期"));
    }
} 