package com.healthmanager.mapper;

import com.healthmanager.entity.MeditationCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 冥想分类Mapper接口
 */
@Repository
public interface MeditationCategoryMapper {
    /**
     * 根据ID查询冥想分类
     * @param id 分类ID
     * @return 冥想分类对象
     */
    MeditationCategory selectById(@Param("id") Long id);
    
    /**
     * 查询所有冥想分类
     * @return 冥想分类列表
     */
    List<MeditationCategory> selectAll();
    
    /**
     * 插入冥想分类
     * @param meditationCategory 冥想分类对象
     * @return 影响行数
     */
    int insert(MeditationCategory meditationCategory);
    
    /**
     * 更新冥想分类
     * @param meditationCategory 冥想分类对象
     * @return 影响行数
     */
    int update(MeditationCategory meditationCategory);
    
    /**
     * 删除冥想分类
     * @param id 分类ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
} 