package com.healthmanager.mapper;

import com.healthmanager.entity.ExerciseRoute;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 运动路线Mapper接口
 */
@Repository
public interface ExerciseRouteMapper {
    /**
     * 根据ID查询运动路线
     * @param id 路线ID
     * @return 运动路线对象
     */
    ExerciseRoute selectById(@Param("id") Long id);
    
    /**
     * 根据运动记录ID查询运动路线
     * @param exerciseId 运动记录ID
     * @return 运动路线对象
     */
    ExerciseRoute selectByExerciseId(@Param("exerciseId") Long exerciseId);
    
    /**
     * 插入运动路线
     * @param exerciseRoute 运动路线对象
     * @return 影响行数
     */
    int insert(ExerciseRoute exerciseRoute);
    
    /**
     * 更新运动路线
     * @param exerciseRoute 运动路线对象
     * @return 影响行数
     */
    int update(ExerciseRoute exerciseRoute);
    
    /**
     * 删除运动路线
     * @param id 路线ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 根据运动记录ID删除运动路线
     * @param exerciseId 运动记录ID
     * @return 影响行数
     */
    int deleteByExerciseId(@Param("exerciseId") Long exerciseId);
} 