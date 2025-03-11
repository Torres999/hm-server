package com.healthmanager.service.impl;

import com.healthmanager.entity.HealthData;
import com.healthmanager.exception.ApiException;
import com.healthmanager.mapper.HealthDataMapper;
import com.healthmanager.service.HealthDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 健康数据Service实现类
 */
@Slf4j
@Service
public class HealthDataServiceImpl implements HealthDataService {
    
    @Autowired
    private HealthDataMapper healthDataMapper;
    
    @Override
    public HealthData getHealthDataById(Long id) {
        log.info("获取健康数据，ID: {}", id);
        return healthDataMapper.selectById(id);
    }
    
    @Override
    public HealthData getHealthDataByUserIdAndDate(Long userId, Date recordDate) {
        log.info("获取健康数据，用户ID: {}, 记录日期: {}", userId, recordDate);
        return healthDataMapper.selectByUserIdAndDate(userId, recordDate);
    }
    
    @Override
    public HealthData getLatestHealthDataByUserId(Long userId) {
        log.info("获取最新健康数据，用户ID: {}", userId);
        return healthDataMapper.selectLatestByUserId(userId);
    }
    
    @Override
    public List<HealthData> getHealthDataByUserIdAndDateRange(Long userId, Date startDate, Date endDate) {
        log.info("获取健康数据，用户ID: {}, 开始日期: {}, 结束日期: {}", userId, startDate, endDate);
        return healthDataMapper.selectByUserIdAndDateRange(userId, startDate, endDate);
    }
    
    @Override
    public HealthData saveHealthData(HealthData healthData) {
        log.info("保存健康数据，用户ID: {}, 记录日期: {}", healthData.getUserId(), healthData.getRecordDate());
        if (healthData.getId() != null) {
            HealthData existHealthData = healthDataMapper.selectById(healthData.getId());
            if (existHealthData != null) {
                log.info("健康数据已存在，进行更新操作，ID: {}", healthData.getId());
                return updateHealthData(healthData);
            }
        }
        
        // 检查是否已存在相同用户和日期的健康数据
        HealthData existHealthData = healthDataMapper.selectByUserIdAndDate(healthData.getUserId(), healthData.getRecordDate());
        if (existHealthData != null) {
            log.info("同一用户同一日期的健康数据已存在，进行更新操作，ID: {}", existHealthData.getId());
            healthData.setId(existHealthData.getId());
            return updateHealthData(healthData);
        }
        
        int result = healthDataMapper.insert(healthData);
        if (result > 0) {
            log.info("健康数据保存成功，ID: {}", healthData.getId());
            return healthData;
        } else {
            log.error("保存健康数据失败，用户ID: {}", healthData.getUserId());
            throw new ApiException("保存健康数据失败");
        }
    }
    
    @Override
    public HealthData updateHealthData(HealthData healthData) {
        log.info("更新健康数据，ID: {}, 用户ID: {}", healthData.getId(), healthData.getUserId());
        if (healthData.getId() == null) {
            log.error("健康数据ID不能为空");
            throw new ApiException("健康数据ID不能为空");
        }
        
        HealthData existHealthData = healthDataMapper.selectById(healthData.getId());
        if (existHealthData == null) {
            log.error("健康数据不存在，ID: {}", healthData.getId());
            throw new ApiException("健康数据不存在");
        }
        
        int result = healthDataMapper.update(healthData);
        if (result > 0) {
            log.info("健康数据更新成功，ID: {}", healthData.getId());
            return healthDataMapper.selectById(healthData.getId());
        } else {
            log.error("更新健康数据失败，ID: {}", healthData.getId());
            throw new ApiException("更新健康数据失败");
        }
    }
    
    @Override
    public boolean deleteHealthData(Long id) {
        log.info("删除健康数据，ID: {}", id);
        if (id == null) {
            log.error("健康数据ID不能为空");
            throw new ApiException("健康数据ID不能为空");
        }
        
        HealthData existHealthData = healthDataMapper.selectById(id);
        if (existHealthData == null) {
            log.error("健康数据不存在，ID: {}", id);
            throw new ApiException("健康数据不存在");
        }
        
        int result = healthDataMapper.deleteById(id);
        log.info("删除健康数据结果，ID: {}, 结果: {}", id, result > 0 ? "成功" : "失败");
        return result > 0;
    }
} 