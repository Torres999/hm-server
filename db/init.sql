-- 创建数据库
CREATE DATABASE IF NOT EXISTS health_manager DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE health_manager;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `open_id` varchar(64) NOT NULL COMMENT '微信OpenID',
  `nick_name` varchar(64) DEFAULT NULL COMMENT '昵称',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '头像URL',
  `gender` tinyint(1) DEFAULT 0 COMMENT '性别：0-未知，1-男，2-女',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_open_id` (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 健康数据表
CREATE TABLE IF NOT EXISTS `health_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '健康数据ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `steps` int(11) DEFAULT 0 COMMENT '步数',
  `heart_rate` int(11) DEFAULT NULL COMMENT '心率',
  `sleep_hours` decimal(3,1) DEFAULT NULL COMMENT '睡眠时长(小时)',
  `calories` int(11) DEFAULT 0 COMMENT '卡路里消耗',
  `record_date` date NOT NULL COMMENT '记录日期',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_date` (`user_id`, `record_date`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_record_date` (`record_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='健康数据表';

-- 任务表
CREATE TABLE IF NOT EXISTS `task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `title` varchar(100) NOT NULL COMMENT '任务标题',
  `description` varchar(255) DEFAULT NULL COMMENT '任务描述',
  `type` varchar(20) NOT NULL COMMENT '任务类型：exercise-运动，meditation-冥想',
  `completed` tinyint(1) DEFAULT 0 COMMENT '是否完成：0-未完成，1-已完成',
  `task_date` date NOT NULL COMMENT '任务日期',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_task_date` (`task_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务表';

-- 运动记录表
CREATE TABLE IF NOT EXISTS `exercise_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '运动记录ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `type` varchar(20) NOT NULL COMMENT '运动类型：running-跑步，cycling-骑行，yoga-瑜伽等',
  `name` varchar(100) NOT NULL COMMENT '运动名称',
  `duration` int(11) NOT NULL COMMENT '运动时长(分钟)',
  `distance` decimal(10,2) DEFAULT NULL COMMENT '运动距离(公里)',
  `calories` int(11) DEFAULT NULL COMMENT '卡路里消耗',
  `pace` varchar(20) DEFAULT NULL COMMENT '配速',
  `exercise_date` date NOT NULL COMMENT '运动日期',
  `exercise_time` time NOT NULL COMMENT '运动时间',
  `notes` text DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_exercise_date` (`exercise_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='运动记录表';

-- 运动路线表
CREATE TABLE IF NOT EXISTS `exercise_route` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '路线ID',
  `exercise_id` bigint(20) NOT NULL COMMENT '运动记录ID',
  `start_lat` decimal(10,6) NOT NULL COMMENT '起点纬度',
  `start_lng` decimal(10,6) NOT NULL COMMENT '起点经度',
  `end_lat` decimal(10,6) NOT NULL COMMENT '终点纬度',
  `end_lng` decimal(10,6) NOT NULL COMMENT '终点经度',
  `polyline` text NOT NULL COMMENT '路线坐标点集合，JSON格式',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_exercise_id` (`exercise_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='运动路线表';

-- 心率记录表
CREATE TABLE IF NOT EXISTS `heart_rate_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '心率记录ID',
  `exercise_id` bigint(20) NOT NULL COMMENT '运动记录ID',
  `time_point` int(11) NOT NULL COMMENT '时间点(秒)',
  `value` int(11) NOT NULL COMMENT '心率值',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_exercise_id` (`exercise_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='心率记录表';

-- 冥想分类表
CREATE TABLE IF NOT EXISTS `meditation_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `name` varchar(50) NOT NULL COMMENT '分类名称',
  `description` varchar(255) DEFAULT NULL COMMENT '分类描述',
  `image` varchar(255) DEFAULT NULL COMMENT '分类图片',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='冥想分类表';

-- 冥想课程表
CREATE TABLE IF NOT EXISTS `meditation_course` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '课程ID',
  `title` varchar(100) NOT NULL COMMENT '课程标题',
  `description` text DEFAULT NULL COMMENT '课程描述',
  `duration` int(11) NOT NULL COMMENT '课程时长(分钟)',
  `image` varchar(255) DEFAULT NULL COMMENT '课程图片',
  `audio_url` varchar(255) NOT NULL COMMENT '音频URL',
  `category_id` bigint(20) NOT NULL COMMENT '分类ID',
  `steps` text DEFAULT NULL COMMENT '课程步骤，JSON格式',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_category_id` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='冥想课程表';

-- 冥想记录表
CREATE TABLE IF NOT EXISTS `meditation_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '冥想记录ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `course_id` bigint(20) NOT NULL COMMENT '课程ID',
  `duration` int(11) NOT NULL COMMENT '实际冥想时长(分钟)',
  `meditation_date` date NOT NULL COMMENT '冥想日期',
  `meditation_time` time NOT NULL COMMENT '冥想时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_course_id` (`course_id`),
  KEY `idx_meditation_date` (`meditation_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='冥想记录表';

-- 活动统计表
CREATE TABLE IF NOT EXISTS `activity_stats` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '统计ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `period_type` varchar(10) NOT NULL COMMENT '周期类型：week-周，month-月，year-年',
  `period_value` varchar(20) NOT NULL COMMENT '周期值，如：2023-01，2023-W01',
  `exercise_sessions` int(11) DEFAULT 0 COMMENT '运动次数',
  `exercise_minutes` int(11) DEFAULT 0 COMMENT '运动总时长(分钟)',
  `exercise_calories` int(11) DEFAULT 0 COMMENT '运动消耗卡路里',
  `exercise_distance` decimal(10,2) DEFAULT 0 COMMENT '运动总距离(公里)',
  `meditation_sessions` int(11) DEFAULT 0 COMMENT '冥想次数',
  `meditation_minutes` int(11) DEFAULT 0 COMMENT '冥想总时长(分钟)',
  `meditation_streak` int(11) DEFAULT 0 COMMENT '冥想连续天数',
  `steps_total` int(11) DEFAULT 0 COMMENT '步数总计',
  `steps_daily_average` int(11) DEFAULT 0 COMMENT '日均步数',
  `steps_best_day` varchar(10) DEFAULT NULL COMMENT '步数最佳日',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_period` (`user_id`, `period_type`, `period_value`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动统计表'; 