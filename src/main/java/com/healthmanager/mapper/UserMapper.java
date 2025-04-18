package com.healthmanager.mapper;

import com.healthmanager.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 用户Mapper接口
 */
@Mapper
public interface UserMapper {
    
    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return 用户信息
     */
    User selectById(@Param("id") Long id);
    
    /**
     * 根据OpenID查询用户
     * @param openId 微信OpenID
     * @return 用户信息
     */
    User selectByOpenId(@Param("openId") String openId);
    
    /**
     * 插入用户
     * @param user 用户信息
     * @return 影响行数
     */
    int insert(User user);
    
    /**
     * 更新用户
     * @param user 用户信息
     * @return 影响行数
     */
    int update(User user);
    
    /**
     * 删除用户
     * @param id 用户ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 分页查询用户列表
     * @param offset 偏移量
     * @param limit 限制数
     * @return 用户列表
     */
    List<User> selectByPage(@Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 查询用户总数
     * @return 用户总数
     */
    int selectCount();
} 