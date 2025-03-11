package com.healthmanager.service.impl;

import com.healthmanager.dto.ExerciseDetailDTO;
import com.healthmanager.entity.ExerciseRecord;
import com.healthmanager.entity.ExerciseRoute;
import com.healthmanager.entity.HeartRateRecord;
import com.healthmanager.exception.ApiException;
import com.healthmanager.mapper.ExerciseRecordMapper;
import com.healthmanager.mapper.ExerciseRouteMapper;
import com.healthmanager.mapper.HeartRateRecordMapper;
import com.healthmanager.service.ExerciseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 运动Service实现类
 */
@Slf4j
@Service
public class ExerciseServiceImpl implements ExerciseService {

    @Autowired
    private ExerciseRecordMapper exerciseRecordMapper;

    @Autowired
    private ExerciseRouteMapper exerciseRouteMapper;

    @Autowired
    private HeartRateRecordMapper heartRateRecordMapper;

    @Override
    public List<ExerciseRecord> getExerciseRecords(Long userId) {
        return exerciseRecordMapper.selectByUserId(userId);
    }

    @Override
    public ExerciseDetailDTO getExerciseDetail(Long id) {
        ExerciseRecord exerciseRecord = exerciseRecordMapper.selectById(id);
        if (exerciseRecord == null) {
            throw new ApiException("运动记录不存在");
        }

        ExerciseDetailDTO exerciseDetailDTO = new ExerciseDetailDTO();
        exerciseDetailDTO.setId(String.valueOf(exerciseRecord.getId()));
        exerciseDetailDTO.setType(exerciseRecord.getType());
        exerciseDetailDTO.setName(exerciseRecord.getName());
        exerciseDetailDTO.setDuration(exerciseRecord.getDuration());
        exerciseDetailDTO.setDistance(exerciseRecord.getDistance() != null ? exerciseRecord.getDistance().toString() : null);
        exerciseDetailDTO.setCalories(exerciseRecord.getCalories());
        exerciseDetailDTO.setPace(exerciseRecord.getPace());
        
        // 格式化日期和时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        exerciseDetailDTO.setDate(exerciseRecord.getExerciseDate() != null ? dateFormat.format(exerciseRecord.getExerciseDate()) : null);
        exerciseDetailDTO.setTime(exerciseRecord.getExerciseTime() != null ? timeFormat.format(exerciseRecord.getExerciseTime()) : null);
        
        exerciseDetailDTO.setNotes(exerciseRecord.getNotes());

        // 获取心率记录
        List<HeartRateRecord> heartRateRecords = heartRateRecordMapper.selectByExerciseId(id);
        if (heartRateRecords != null && !heartRateRecords.isEmpty()) {
            List<ExerciseDetailDTO.HeartRateDTO> heartRateDTOs = heartRateRecords.stream()
                    .map(record -> {
                        ExerciseDetailDTO.HeartRateDTO heartRateDTO = new ExerciseDetailDTO.HeartRateDTO();
                        heartRateDTO.setTime(record.getTimePoint());
                        heartRateDTO.setValue(record.getValue());
                        return heartRateDTO;
                    })
                    .collect(Collectors.toList());
            exerciseDetailDTO.setHeartRate(heartRateDTOs);
        }

        // 获取运动路线
        ExerciseRoute exerciseRoute = exerciseRouteMapper.selectByExerciseId(id);
        if (exerciseRoute != null) {
            ExerciseDetailDTO.RouteDTO routeDTO = new ExerciseDetailDTO.RouteDTO();
            routeDTO.setStartLat(exerciseRoute.getStartLat());
            routeDTO.setStartLng(exerciseRoute.getStartLng());
            
            // 这里假设polyline字段存储的是JSON格式的路线数据
            // 实际项目中可能需要使用JSON库进行解析
            // 这里简化处理，仅创建示例数据
            List<ExerciseDetailDTO.MarkerDTO> markers = new ArrayList<>();
            ExerciseDetailDTO.MarkerDTO startMarker = new ExerciseDetailDTO.MarkerDTO();
            startMarker.setId(1);
            startMarker.setLatitude(exerciseRoute.getStartLat());
            startMarker.setLongitude(exerciseRoute.getStartLng());
            startMarker.setTitle("起点");
            markers.add(startMarker);
            
            ExerciseDetailDTO.MarkerDTO endMarker = new ExerciseDetailDTO.MarkerDTO();
            endMarker.setId(2);
            endMarker.setLatitude(exerciseRoute.getEndLat());
            endMarker.setLongitude(exerciseRoute.getEndLng());
            endMarker.setTitle("终点");
            markers.add(endMarker);
            
            routeDTO.setMarkers(markers);
            
            List<ExerciseDetailDTO.PolylineDTO> polylines = new ArrayList<>();
            ExerciseDetailDTO.PolylineDTO polylineDTO = new ExerciseDetailDTO.PolylineDTO();
            polylineDTO.setColor("#4F6EF6");
            polylineDTO.setWidth(4);
            
            List<ExerciseDetailDTO.PointDTO> points = new ArrayList<>();
            ExerciseDetailDTO.PointDTO startPoint = new ExerciseDetailDTO.PointDTO();
            startPoint.setLatitude(exerciseRoute.getStartLat());
            startPoint.setLongitude(exerciseRoute.getStartLng());
            points.add(startPoint);
            
            ExerciseDetailDTO.PointDTO endPoint = new ExerciseDetailDTO.PointDTO();
            endPoint.setLatitude(exerciseRoute.getEndLat());
            endPoint.setLongitude(exerciseRoute.getEndLng());
            points.add(endPoint);
            
            polylineDTO.setPoints(points);
            polylines.add(polylineDTO);
            
            routeDTO.setPolyline(polylines);
            exerciseDetailDTO.setRoute(routeDTO);
        }

        return exerciseDetailDTO;
    }

    @Override
    @Transactional
    public Long saveExerciseRecord(ExerciseDetailDTO exerciseDetailDTO) {
        ExerciseRecord exerciseRecord = new ExerciseRecord();
        
        // 设置基本信息
        if (exerciseDetailDTO.getId() != null) {
            exerciseRecord.setId(Long.valueOf(exerciseDetailDTO.getId()));
        }
        
        // 用户ID需要从请求中获取，这里假设已经设置在DTO中
        // 实际项目中可能需要从安全上下文中获取
        exerciseRecord.setUserId(1L); // 示例用户ID，实际应用中需要替换
        
        exerciseRecord.setType(exerciseDetailDTO.getType());
        exerciseRecord.setName(exerciseDetailDTO.getName());
        exerciseRecord.setDuration(exerciseDetailDTO.getDuration());
        
        if (exerciseDetailDTO.getDistance() != null) {
            exerciseRecord.setDistance(new BigDecimal(exerciseDetailDTO.getDistance()));
        }
        
        exerciseRecord.setCalories(exerciseDetailDTO.getCalories());
        exerciseRecord.setPace(exerciseDetailDTO.getPace());
        exerciseRecord.setNotes(exerciseDetailDTO.getNotes());
        
        // 解析日期和时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        
        try {
            if (exerciseDetailDTO.getDate() != null) {
                exerciseRecord.setExerciseDate(dateFormat.parse(exerciseDetailDTO.getDate()));
            }
            
            if (exerciseDetailDTO.getTime() != null) {
                exerciseRecord.setExerciseTime(timeFormat.parse(exerciseDetailDTO.getTime()));
            }
        } catch (ParseException e) {
            throw new ApiException("日期或时间格式错误");
        }
        
        // 保存运动记录
        exerciseRecordMapper.insert(exerciseRecord);
        Long exerciseId = exerciseRecord.getId();
        
        // 保存运动路线
        if (exerciseDetailDTO.getRoute() != null) {
            ExerciseRoute exerciseRoute = new ExerciseRoute();
            exerciseRoute.setExerciseId(exerciseId);
            exerciseRoute.setStartLat(exerciseDetailDTO.getRoute().getStartLat());
            exerciseRoute.setStartLng(exerciseDetailDTO.getRoute().getStartLng());
            
            // 获取终点坐标
            if (exerciseDetailDTO.getRoute().getMarkers() != null && exerciseDetailDTO.getRoute().getMarkers().size() > 1) {
                ExerciseDetailDTO.MarkerDTO endMarker = exerciseDetailDTO.getRoute().getMarkers().get(1);
                exerciseRoute.setEndLat(endMarker.getLatitude());
                exerciseRoute.setEndLng(endMarker.getLongitude());
            } else {
                // 如果没有终点标记，使用起点坐标作为终点
                exerciseRoute.setEndLat(exerciseDetailDTO.getRoute().getStartLat());
                exerciseRoute.setEndLng(exerciseDetailDTO.getRoute().getStartLng());
            }
            
            // 这里应该将路线数据转换为JSON字符串存储
            // 简化处理，仅存储示例数据
            exerciseRoute.setPolyline("[]");
            
            exerciseRouteMapper.insert(exerciseRoute);
        }
        
        // 保存心率记录
        if (exerciseDetailDTO.getHeartRate() != null && !exerciseDetailDTO.getHeartRate().isEmpty()) {
            List<HeartRateRecord> heartRateRecords = exerciseDetailDTO.getHeartRate().stream()
                    .map(heartRateDTO -> {
                        HeartRateRecord heartRateRecord = new HeartRateRecord();
                        heartRateRecord.setExerciseId(exerciseId);
                        heartRateRecord.setTimePoint(heartRateDTO.getTime());
                        heartRateRecord.setValue(heartRateDTO.getValue());
                        return heartRateRecord;
                    })
                    .collect(Collectors.toList());
            
            heartRateRecordMapper.batchInsert(heartRateRecords);
        }
        
        return exerciseId;
    }

    @Override
    @Transactional
    public boolean updateExerciseRecord(Long id, ExerciseDetailDTO exerciseDetailDTO) {
        ExerciseRecord exerciseRecord = exerciseRecordMapper.selectById(id);
        if (exerciseRecord == null) {
            throw new ApiException("运动记录不存在");
        }
        
        // 更新基本信息
        exerciseRecord.setType(exerciseDetailDTO.getType());
        exerciseRecord.setName(exerciseDetailDTO.getName());
        exerciseRecord.setDuration(exerciseDetailDTO.getDuration());
        
        if (exerciseDetailDTO.getDistance() != null) {
            exerciseRecord.setDistance(new BigDecimal(exerciseDetailDTO.getDistance()));
        }
        
        exerciseRecord.setCalories(exerciseDetailDTO.getCalories());
        exerciseRecord.setPace(exerciseDetailDTO.getPace());
        exerciseRecord.setNotes(exerciseDetailDTO.getNotes());
        
        // 解析日期和时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        
        try {
            if (exerciseDetailDTO.getDate() != null) {
                exerciseRecord.setExerciseDate(dateFormat.parse(exerciseDetailDTO.getDate()));
            }
            
            if (exerciseDetailDTO.getTime() != null) {
                exerciseRecord.setExerciseTime(timeFormat.parse(exerciseDetailDTO.getTime()));
            }
        } catch (ParseException e) {
            throw new ApiException("日期或时间格式错误");
        }
        
        // 更新运动记录
        exerciseRecordMapper.update(exerciseRecord);
        
        // 更新运动路线
        if (exerciseDetailDTO.getRoute() != null) {
            ExerciseRoute exerciseRoute = exerciseRouteMapper.selectByExerciseId(id);
            if (exerciseRoute == null) {
                exerciseRoute = new ExerciseRoute();
                exerciseRoute.setExerciseId(id);
            }
            
            exerciseRoute.setStartLat(exerciseDetailDTO.getRoute().getStartLat());
            exerciseRoute.setStartLng(exerciseDetailDTO.getRoute().getStartLng());
            
            // 获取终点坐标
            if (exerciseDetailDTO.getRoute().getMarkers() != null && exerciseDetailDTO.getRoute().getMarkers().size() > 1) {
                ExerciseDetailDTO.MarkerDTO endMarker = exerciseDetailDTO.getRoute().getMarkers().get(1);
                exerciseRoute.setEndLat(endMarker.getLatitude());
                exerciseRoute.setEndLng(endMarker.getLongitude());
            } else {
                // 如果没有终点标记，使用起点坐标作为终点
                exerciseRoute.setEndLat(exerciseDetailDTO.getRoute().getStartLat());
                exerciseRoute.setEndLng(exerciseDetailDTO.getRoute().getStartLng());
            }
            
            // 这里应该将路线数据转换为JSON字符串存储
            // 简化处理，仅存储示例数据
            exerciseRoute.setPolyline("[]");
            
            if (exerciseRoute.getId() == null) {
                exerciseRouteMapper.insert(exerciseRoute);
            } else {
                exerciseRouteMapper.update(exerciseRoute);
            }
        }
        
        // 更新心率记录
        if (exerciseDetailDTO.getHeartRate() != null) {
            // 删除旧的心率记录
            heartRateRecordMapper.deleteByExerciseId(id);
            
            // 添加新的心率记录
            if (!exerciseDetailDTO.getHeartRate().isEmpty()) {
                List<HeartRateRecord> heartRateRecords = exerciseDetailDTO.getHeartRate().stream()
                        .map(heartRateDTO -> {
                            HeartRateRecord heartRateRecord = new HeartRateRecord();
                            heartRateRecord.setExerciseId(id);
                            heartRateRecord.setTimePoint(heartRateDTO.getTime());
                            heartRateRecord.setValue(heartRateDTO.getValue());
                            return heartRateRecord;
                        })
                        .collect(Collectors.toList());
                
                heartRateRecordMapper.batchInsert(heartRateRecords);
            }
        }
        
        return true;
    }

    @Override
    @Transactional
    public boolean deleteExerciseRecord(Long id) {
        ExerciseRecord exerciseRecord = exerciseRecordMapper.selectById(id);
        if (exerciseRecord == null) {
            throw new ApiException("运动记录不存在");
        }
        
        // 删除心率记录
        heartRateRecordMapper.deleteByExerciseId(id);
        
        // 删除运动路线
        exerciseRouteMapper.deleteByExerciseId(id);
        
        // 删除运动记录
        exerciseRecordMapper.deleteById(id);
        
        return true;
    }
} 