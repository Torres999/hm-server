package com.healthmanager.controller;

import com.healthmanager.common.Result;
import com.healthmanager.entity.User;
import com.healthmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "用户管理", description = "用户信息管理相关接口")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 获取用户信息
     */
    @Operation(summary = "获取用户信息", description = "根据用户ID获取用户详细信息")
    @GetMapping("/{id}")
    public Result<User> getUserById(@Parameter(description = "用户ID") @PathVariable Long id) {
        return Result.success(userService.getUserById(id));
    }
    
    /**
     * 获取用户信息（通过OpenID）
     */
    @Operation(summary = "通过OpenID获取用户", description = "根据微信OpenID获取用户信息")
    @GetMapping("/openid/{openId}")
    public Result<User> getUserByOpenId(@Parameter(description = "微信OpenID") @PathVariable String openId) {
        return Result.success(userService.getUserByOpenId(openId));
    }
    
    /**
     * 更新用户信息
     */
    @Operation(summary = "更新用户信息", description = "更新指定用户的信息")
    @PutMapping("/{id}")
    public Result<User> updateUser(@Parameter(description = "用户ID") @PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        return Result.success(userService.updateUser(user));
    }
    
    /**
     * 分页查询用户列表
     */
    @Operation(summary = "分页查询用户", description = "分页获取用户列表")
    @GetMapping("/page")
    public Result<Object> getUsersByPage(
            @Parameter(description = "页码，从1开始") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页记录数") @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(userService.getUsersByPage(pageNum, pageSize));
    }
    
    /**
     * 删除用户
     */
    @Operation(summary = "删除用户", description = "根据用户ID删除用户")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteUser(@Parameter(description = "用户ID") @PathVariable Long id) {
        return Result.success(userService.deleteUser(id));
    }
} 