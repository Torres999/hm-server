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
        return healthDataMapper.selectById(id);
    }
    
    @Override
    public HealthData getHealthDataByUserIdAndDate(Long userId, Date recordDate) {
        return healthDataMapper.selectByUserIdAndDate(userId, recordDate);
    }
    
    @Override
    public HealthData getLatestHealthDataByUserId(Long userId) {
        return healthDataMapper.selectLatestByUserId(userId);
    }
    
    @Override
    public List<HealthData> getHealthDataByUserIdAndDateRange(Long userId, Date startDate, Date endDate) {
        return healthDataMapper.selectByUserIdAndDateRange(userId, startDate, endDate);
    }
    
    @Override
    public HealthData saveHealthData(HealthData healthData) {
        if (healthData.getId() != null) {
            HealthData existHealthData = healthDataMapper.selectById(healthData.getId());
            if (existHealthData != null) {
                return updateHealthData(healthData);
            }
        }
        
        // 检查是否已存在相同用户和日期的健康数据
        HealthData existHealthData = healthDataMapper.selectByUserIdAndDate(healthData.getUserId(), healthData.getRecordDate());
        if (existHealthData != null) {
            healthData.setId(existHealthData.getId());
            return updateHealthData(healthData);
        }
        
        int result = healthDataMapper.insert(healthData);
        if (result > 0) {
            return healthData;
        } else {
            throw new ApiException("保存健康数据失败");
        }
    }
    
    @Override
    public HealthData updateHealthData(HealthData healthData) {
        if (healthData.getId() == null) {
            throw new ApiException("健康数据ID不能为空");
        }
        
        HealthData existHealthData = healthDataMapper.selectById(healthData.getId());
        if (existHealthData == null) {
            throw new ApiException("健康数据不存在");
        }
        
        int result = healthDataMapper.update(healthData);
        if (result > 0) {
            return healthDataMapper.selectById(healthData.getId());
        } else {
            throw new ApiException("更新健康数据失败");
        }
    }
    
    @Override
    public boolean deleteHealthData(Long id) {
        if (id == null) {
            throw new ApiException("健康数据ID不能为空");
        }
        
        HealthData existHealthData = healthDataMapper.selectById(id);
        if (existHealthData == null) {
            throw new ApiException("健康数据不存在");
        }
        
        int result = healthDataMapper.deleteById(id);
        return result > 0;
    }
} 