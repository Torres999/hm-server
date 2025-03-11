package com.healthmanager.controller;

import com.healthmanager.common.Result;
import com.healthmanager.entity.User;
import com.healthmanager.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户Controller
 */
@Slf4j
@Api(tags = "用户相关接口")
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @ApiOperation("根据ID获取用户信息")
    @GetMapping("/{id}")
    public Result<User> getUserById(@ApiParam(value = "用户ID", required = true) @PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        return Result.success(user);
    }
    
    @ApiOperation("根据OpenID获取用户信息")
    @GetMapping("/openid/{openId}")
    public Result<User> getUserByOpenId(@ApiParam(value = "微信OpenID", required = true) @PathVariable("openId") String openId) {
        User user = userService.getUserByOpenId(openId);
        return Result.success(user);
    }
    
    @ApiOperation("保存用户信息")
    @PostMapping
    public Result<User> saveUser(@ApiParam(value = "用户信息", required = true) @RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return Result.success(savedUser);
    }
    
    @ApiOperation("更新用户信息")
    @PutMapping("/{id}")
    public Result<User> updateUser(
            @ApiParam(value = "用户ID", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "用户信息", required = true) @RequestBody User user) {
        user.setId(id);
        User updatedUser = userService.updateUser(user);
        return Result.success(updatedUser);
    }
    
    @ApiOperation("删除用户")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteUser(@ApiParam(value = "用户ID", required = true) @PathVariable("id") Long id) {
        boolean result = userService.deleteUser(id);
        return Result.success(result);
    }
} 