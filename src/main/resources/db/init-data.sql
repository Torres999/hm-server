-- 创建用户表
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `open_id` varchar(64) NOT NULL COMMENT '微信OpenID',
  `nick_name` varchar(64) DEFAULT NULL COMMENT '用户昵称',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '用户头像URL',
  `gender` tinyint(4) DEFAULT '0' COMMENT '性别（0：未知，1：男，2：女）',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_open_id` (`open_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 插入测试用户数据
INSERT INTO `user` (`open_id`, `nick_name`, `avatar_url`, `gender`, `phone`, `create_time`, `update_time`) VALUES
('oWx5s5JFIwNJYOPNg5JFIwNJYOPN', '张三', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIbWFEIJj8IyPmGXOdq0nKoibiaZlYS3vHXGu0icPcYqRYfic2BXibmJfomNnqiaicGVay8jcGdOJnr12Fw/132', 1, '13800138001', '2023-01-01 12:00:00', '2023-01-01 12:00:00'),
('oWx5s5KLMNOPQRSTUVWXYZabcdef', '李四', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIbWFEIJj8IyPmGXOdq0nKoibiaZlYS3vHXGu0icPcYqRYfic2BXibmJfomNnqiaicGVay8jcGdOJnr12Fw/132', 1, '13800138002', '2023-01-02 12:00:00', '2023-01-02 12:00:00'),
('oWx5s5ABCDEFGHIJKLMNOPQRSTUv', '王五', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIbWFEIJj8IyPmGXOdq0nKoibiaZlYS3vHXGu0icPcYqRYfic2BXibmJfomNnqiaicGVay8jcGdOJnr12Fw/132', 1, '13800138003', '2023-01-03 12:00:00', '2023-01-03 12:00:00'),
('oWx5s5abcdefghijklmnopqrstuv', '赵六', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIbWFEIJj8IyPmGXOdq0nKoibiaZlYS3vHXGu0icPcYqRYfic2BXibmJfomNnqiaicGVay8jcGdOJnr12Fw/132', 1, '13800138004', '2023-01-04 12:00:00', '2023-01-04 12:00:00'),
('oWx5s512345678901234567890123', '小红', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIbWFEIJj8IyPmGXOdq0nKoibiaZlYS3vHXGu0icPcYqRYfic2BXibmJfomNnqiaicGVay8jcGdOJnr12Fw/132', 2, '13800138005', '2023-01-05 12:00:00', '2023-01-05 12:00:00'),
('oWx5s5ABCDEFG1234567890HIJKL', '小明', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIbWFEIJj8IyPmGXOdq0nKoibiaZlYS3vHXGu0icPcYqRYfic2BXibmJfomNnqiaicGVay8jcGdOJnr12Fw/132', 1, '13800138006', '2023-01-06 12:00:00', '2023-01-06 12:00:00'),
('oWx5s5MNOPQRST1234567890UVWX', '小芳', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIbWFEIJj8IyPmGXOdq0nKoibiaZlYS3vHXGu0icPcYqRYfic2BXibmJfomNnqiaicGVay8jcGdOJnr12Fw/132', 2, '13800138007', '2023-01-07 12:00:00', '2023-01-07 12:00:00'),
('oWx5s5YZABCDEF1234567890GHIJ', '小李', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIbWFEIJj8IyPmGXOdq0nKoibiaZlYS3vHXGu0icPcYqRYfic2BXibmJfomNnqiaicGVay8jcGdOJnr12Fw/132', 1, '13800138008', '2023-01-08 12:00:00', '2023-01-08 12:00:00'),
('oWx5s5KLMNOPQR1234567890STUV', '小张', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIbWFEIJj8IyPmGXOdq0nKoibiaZlYS3vHXGu0icPcYqRYfic2BXibmJfomNnqiaicGVay8jcGdOJnr12Fw/132', 1, '13800138009', '2023-01-09 12:00:00', '2023-01-09 12:00:00'),
('oWx5s5WXYZABCD1234567890EFGH', '小王', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIbWFEIJj8IyPmGXOdq0nKoibiaZlYS3vHXGu0icPcYqRYfic2BXibmJfomNnqiaicGVay8jcGdOJnr12Fw/132', 1, '13800138010', '2023-01-10 12:00:00', '2023-01-10 12:00:00');

-- 创建健康记录表
CREATE TABLE IF NOT EXISTS `health_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `record_date` date NOT NULL COMMENT '记录日期',
  `weight` decimal(5,2) DEFAULT NULL COMMENT '体重(kg)',
  `height` decimal(5,2) DEFAULT NULL COMMENT '身高(cm)',
  `bmi` decimal(5,2) DEFAULT NULL COMMENT 'BMI指数',
  `blood_pressure_high` int(11) DEFAULT NULL COMMENT '收缩压(mmHg)',
  `blood_pressure_low` int(11) DEFAULT NULL COMMENT '舒张压(mmHg)',
  `heart_rate` int(11) DEFAULT NULL COMMENT '心率(次/分)',
  `blood_sugar` decimal(5,2) DEFAULT NULL COMMENT '血糖(mmol/L)',
  `sleep_hours` decimal(4,1) DEFAULT NULL COMMENT '睡眠时长(小时)',
  `steps` int(11) DEFAULT NULL COMMENT '步数',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_record_date` (`record_date`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='健康记录表';

-- 插入测试健康记录数据
INSERT INTO `health_record` (`user_id`, `record_date`, `weight`, `height`, `bmi`, `blood_pressure_high`, `blood_pressure_low`, `heart_rate`, `blood_sugar`, `sleep_hours`, `steps`, `remark`, `create_time`, `update_time`) VALUES
(1, '2023-01-01', 70.5, 175.0, 23.0, 120, 80, 75, 5.6, 7.5, 8000, '今天感觉不错', '2023-01-01 20:00:00', '2023-01-01 20:00:00'),
(1, '2023-01-02', 70.3, 175.0, 22.9, 118, 78, 72, 5.5, 8.0, 10000, '多喝水', '2023-01-02 20:00:00', '2023-01-02 20:00:00'),
(1, '2023-01-03', 70.0, 175.0, 22.9, 122, 82, 76, 5.7, 6.5, 7500, '有点疲劳', '2023-01-03 20:00:00', '2023-01-03 20:00:00'),
(2, '2023-01-01', 65.0, 170.0, 22.5, 115, 75, 68, 5.2, 8.5, 12000, '运动后感觉很好', '2023-01-01 21:00:00', '2023-01-01 21:00:00'),
(2, '2023-01-02', 64.8, 170.0, 22.4, 116, 76, 70, 5.3, 7.0, 9000, '工作有点累', '2023-01-02 21:00:00', '2023-01-02 21:00:00'),
(3, '2023-01-01', 80.0, 180.0, 24.7, 130, 85, 80, 6.0, 6.0, 5000, '需要加强锻炼', '2023-01-01 22:00:00', '2023-01-01 22:00:00'),
(4, '2023-01-01', 55.0, 160.0, 21.5, 110, 70, 65, 5.0, 9.0, 15000, '今天走路很多', '2023-01-01 19:00:00', '2023-01-01 19:00:00'),
(5, '2023-01-01', 58.0, 165.0, 21.3, 112, 72, 68, 5.1, 8.0, 11000, '饮食均衡', '2023-01-01 18:00:00', '2023-01-01 18:00:00');

-- 创建饮食记录表
CREATE TABLE IF NOT EXISTS `diet_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `record_date` date NOT NULL COMMENT '记录日期',
  `meal_type` tinyint(4) NOT NULL COMMENT '餐食类型（1：早餐，2：午餐，3：晚餐，4：加餐）',
  `food_name` varchar(100) NOT NULL COMMENT '食物名称',
  `calories` int(11) DEFAULT NULL COMMENT '卡路里(kcal)',
  `protein` decimal(5,2) DEFAULT NULL COMMENT '蛋白质(g)',
  `fat` decimal(5,2) DEFAULT NULL COMMENT '脂肪(g)',
  `carbohydrate` decimal(5,2) DEFAULT NULL COMMENT '碳水化合物(g)',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_record_date` (`record_date`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='饮食记录表';

-- 插入测试饮食记录数据
INSERT INTO `diet_record` (`user_id`, `record_date`, `meal_type`, `food_name`, `calories`, `protein`, `fat`, `carbohydrate`, `remark`, `create_time`, `update_time`) VALUES
(1, '2023-01-01', 1, '牛奶燕麦粥', 300, 10.0, 5.0, 50.0, '加了一点蜂蜜', '2023-01-01 08:00:00', '2023-01-01 08:00:00'),
(1, '2023-01-01', 2, '鸡胸肉沙拉', 450, 30.0, 15.0, 40.0, '少放了沙拉酱', '2023-01-01 12:00:00', '2023-01-01 12:00:00'),
(1, '2023-01-01', 3, '清蒸鱼+米饭+青菜', 500, 25.0, 10.0, 70.0, '鱼很新鲜', '2023-01-01 18:00:00', '2023-01-01 18:00:00'),
(1, '2023-01-02', 1, '全麦面包+鸡蛋', 350, 15.0, 10.0, 45.0, '两片面包', '2023-01-02 08:00:00', '2023-01-02 08:00:00'),
(2, '2023-01-01', 1, '豆浆+包子', 400, 15.0, 8.0, 60.0, '两个肉包', '2023-01-01 07:30:00', '2023-01-01 07:30:00'),
(2, '2023-01-01', 2, '牛肉面', 600, 25.0, 15.0, 80.0, '加了辣椒', '2023-01-01 12:30:00', '2023-01-01 12:30:00'),
(3, '2023-01-01', 1, '水煮蛋+牛奶+香蕉', 350, 20.0, 12.0, 35.0, '两个鸡蛋', '2023-01-01 08:00:00', '2023-01-01 08:00:00'),
(3, '2023-01-01', 3, '烤鸡腿+土豆泥+沙拉', 700, 35.0, 30.0, 60.0, '晚上吃得有点多', '2023-01-01 19:00:00', '2023-01-01 19:00:00');

-- 创建运动记录表
CREATE TABLE IF NOT EXISTS `exercise_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `record_date` date NOT NULL COMMENT '记录日期',
  `exercise_type` varchar(50) NOT NULL COMMENT '运动类型',
  `duration` int(11) NOT NULL COMMENT '运动时长(分钟)',
  `calories_burned` int(11) DEFAULT NULL COMMENT '消耗卡路里(kcal)',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_record_date` (`record_date`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='运动记录表';

-- 插入测试运动记录数据
INSERT INTO `exercise_record` (`user_id`, `record_date`, `exercise_type`, `duration`, `calories_burned`, `remark`, `create_time`, `update_time`) VALUES
(1, '2023-01-01', '跑步', 30, 300, '慢跑5公里', '2023-01-01 18:00:00', '2023-01-01 18:00:00'),
(1, '2023-01-02', '力量训练', 45, 250, '主要锻炼上肢', '2023-01-02 17:00:00', '2023-01-02 17:00:00'),
(1, '2023-01-03', '游泳', 60, 400, '自由泳', '2023-01-03 16:00:00', '2023-01-03 16:00:00'),
(2, '2023-01-01', '瑜伽', 60, 200, '舒缓压力', '2023-01-01 20:00:00', '2023-01-01 20:00:00'),
(2, '2023-01-02', '骑行', 45, 350, '室外骑行', '2023-01-02 18:00:00', '2023-01-02 18:00:00'),
(3, '2023-01-01', '篮球', 90, 600, '和朋友一起打球', '2023-01-01 16:00:00', '2023-01-01 16:00:00'),
(4, '2023-01-01', '健走', 60, 250, '公园散步', '2023-01-01 17:00:00', '2023-01-01 17:00:00'),
(5, '2023-01-01', '跳绳', 20, 200, '高强度间歇', '2023-01-01 07:00:00', '2023-01-01 07:00:00');

-- 创建健康目标表
CREATE TABLE IF NOT EXISTS `health_goal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '目标ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `goal_type` tinyint(4) NOT NULL COMMENT '目标类型（1：体重，2：运动，3：饮食，4：睡眠）',
  `target_value` decimal(10,2) NOT NULL COMMENT '目标值',
  `current_value` decimal(10,2) DEFAULT NULL COMMENT '当前值',
  `unit` varchar(20) DEFAULT NULL COMMENT '单位',
  `start_date` date NOT NULL COMMENT '开始日期',
  `end_date` date NOT NULL COMMENT '结束日期',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（0：进行中，1：已完成，2：已放弃）',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='健康目标表';

-- 插入测试健康目标数据
INSERT INTO `health_goal` (`user_id`, `goal_type`, `target_value`, `current_value`, `unit`, `start_date`, `end_date`, `status`, `remark`, `create_time`, `update_time`) VALUES
(1, 1, 68.00, 70.50, 'kg', '2023-01-01', '2023-03-31', 0, '减重目标', '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
(1, 2, 10000.00, 8000.00, '步/天', '2023-01-01', '2023-01-31', 0, '每天步数目标', '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
(1, 3, 2000.00, 2200.00, 'kcal/天', '2023-01-01', '2023-01-31', 0, '控制每日摄入热量', '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
(1, 4, 8.00, 7.50, '小时/天', '2023-01-01', '2023-01-31', 0, '保证充足睡眠', '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
(2, 1, 60.00, 65.00, 'kg', '2023-01-01', '2023-02-28', 0, '减重目标', '2023-01-01 11:00:00', '2023-01-01 11:00:00'),
(2, 2, 12000.00, 12000.00, '步/天', '2023-01-01', '2023-01-31', 1, '每天步数目标', '2023-01-01 11:00:00', '2023-01-01 11:00:00'),
(3, 2, 5.00, 3.00, '次/周', '2023-01-01', '2023-03-31', 0, '每周运动5次', '2023-01-01 12:00:00', '2023-01-01 12:00:00'),
(4, 3, 1800.00, 1900.00, 'kcal/天', '2023-01-01', '2023-01-31', 0, '控制每日摄入热量', '2023-01-01 13:00:00', '2023-01-01 13:00:00'),
(5, 4, 7.50, 8.00, '小时/天', '2023-01-01', '2023-01-31', 1, '保证充足睡眠', '2023-01-01 14:00:00', '2023-01-01 14:00:00'); 