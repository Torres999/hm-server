package com.healthmanager.mapper;

import com.healthmanager.entity.Task;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 任务Mapper接口
 */
@Repository
public interface TaskMapper {
    /**
     * 根据ID查询任务
     * @param id 任务ID
     * @return 任务对象
     */
    Task selectById(@Param("id") Long id);
    
    /**
     * 根据用户ID和日期查询任务列表
     * @param userId 用户ID
     * @param taskDate 任务日期
     * @return 任务列表
     */
    List<Task> selectByUserIdAndDate(@Param("userId") Long userId, @Param("taskDate") Date taskDate);
    
    /**
     * 根据用户ID查询任务列表
     * @param userId 用户ID
     * @return 任务列表
     */
    List<Task> selectByUserId(@Param("userId") Long userId);
    
    /**
     * 插入任务
     * @param task 任务对象
     * @return 影响行数
     */
    int insert(Task task);
    
    /**
     * 更新任务
     * @param task 任务对象
     * @return 影响行数
     */
    int update(Task task);
    
    /**
     * 更新任务完成状态
     * @param id 任务ID
     * @param completed 完成状态
     * @return 影响行数
     */
    int updateCompletedStatus(@Param("id") Long id, @Param("completed") Boolean completed);
    
    /**
     * 删除任务
     * @param id 任务ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
} 