package com.healthmanager.mapper;

import com.healthmanager.entity.MeditationCourse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 冥想课程Mapper接口
 */
@Repository
public interface MeditationCourseMapper {
    /**
     * 根据ID查询冥想课程
     * @param id 课程ID
     * @return 冥想课程对象
     */
    MeditationCourse selectById(@Param("id") Long id);
    
    /**
     * 查询所有冥想课程
     * @return 冥想课程列表
     */
    List<MeditationCourse> selectAll();
    
    /**
     * 根据分类ID查询冥想课程列表
     * @param categoryId 分类ID
     * @return 冥想课程列表
     */
    List<MeditationCourse> selectByCategoryId(@Param("categoryId") Long categoryId);
    
    /**
     * 插入冥想课程
     * @param meditationCourse 冥想课程对象
     * @return 影响行数
     */
    int insert(MeditationCourse meditationCourse);
    
    /**
     * 更新冥想课程
     * @param meditationCourse 冥想课程对象
     * @return 影响行数
     */
    int update(MeditationCourse meditationCourse);
    
    /**
     * 删除冥想课程
     * @param id 课程ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
} 