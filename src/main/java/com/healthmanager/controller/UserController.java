package com.healthmanager.controller;

import com.healthmanager.common.Result;
import com.healthmanager.entity.User;
import com.healthmanager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 用户控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
@Api(tags = "用户管理", description = "用户信息管理相关接口")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 获取用户信息
     */
    @ApiOperation(value = "获取用户信息", notes = "根据用户ID获取用户详细信息")
    @GetMapping("/{id}")
    public Result<User> getUserById(@ApiParam(value = "用户ID") @PathVariable Long id) {
        log.info("获取用户信息，用户ID: {}", id);
        return Result.success(userService.getUserById(id));
    }
    
    /**
     * 获取用户信息（通过OpenID）
     */
    @ApiOperation(value = "通过OpenID获取用户", notes = "根据微信OpenID获取用户信息")
    @GetMapping("/openid/{openId}")
    public Result<User> getUserByOpenId(@ApiParam(value = "微信OpenID") @PathVariable String openId) {
        log.info("通过OpenID获取用户信息，OpenID: {}", openId);
        return Result.success(userService.getUserByOpenId(openId));
    }
    
    /**
     * 更新用户信息
     */
    @ApiOperation(value = "更新用户信息", notes = "更新指定用户的信息")
    @PutMapping("/{id}")
    public Result<User> updateUser(@ApiParam(value = "用户ID") @PathVariable Long id, @RequestBody User user) {
        log.info("更新用户信息，用户ID: {}", id);
        user.setId(id);
        return Result.success(userService.updateUser(user));
    }
    
    /**
     * 分页查询用户
     */
    @ApiOperation(value = "分页查询用户", notes = "分页获取用户列表")
    @GetMapping("/page")
    public Result<Object> getUsersByPage(
            @ApiParam(value = "页码，从1开始") @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页记录数") @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("分页查询用户，页码: {}，每页记录数: {}", pageNum, pageSize);
        return Result.success(userService.getUsersByPage(pageNum, pageSize));
    }
    
    /**
     * 删除用户
     */
    @ApiOperation(value = "删除用户", notes = "根据用户ID删除用户")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteUser(@ApiParam(value = "用户ID") @PathVariable Long id) {
        log.info("删除用户，用户ID: {}", id);
        return Result.success(userService.deleteUser(id));
    }
} 