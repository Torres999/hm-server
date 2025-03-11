# 健康管理小程序后端服务

## 项目概述
这是一个基于Spring Boot开发的健康管理小程序后端服务，为微信小程序提供API接口，支持用户健康数据管理、运动记录、冥想课程等功能。

## 技术栈
- Java 8
- Spring Boot 2.7.5
- MyBatis 2.2.2
- MySQL 5.7
- Swagger 3.0.0
- Protocol Buffers 3.21.9
- Maven

## 项目结构
```
hm-server/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── healthmanager/
│   │   │           ├── common/         # 通用类
│   │   │           ├── config/         # 配置类
│   │   │           ├── controller/     # 控制器
│   │   │           ├── dto/            # 数据传输对象
│   │   │           ├── entity/         # 实体类
│   │   │           ├── exception/      # 异常处理
│   │   │           ├── mapper/         # MyBatis Mapper接口
│   │   │           └── service/        # 服务接口和实现
│   │   └── resources/
│   │       ├── mapper/                 # MyBatis XML映射文件
│   │       ├── db/                     # 数据库脚本
│   │       └── application.yml         # 应用配置文件
│   └── test/                           # 测试代码
└── pom.xml                             # Maven配置文件
```

## 功能模块
1. **用户管理**：用户注册、登录、信息管理
2. **健康数据**：记录和查询用户的健康数据（步数、心率、睡眠等）
3. **运动记录**：记录和查询用户的运动数据（跑步、骑行等）
4. **冥想课程**：提供冥想课程和记录用户的冥想活动
5. **任务管理**：用户每日任务的管理
6. **数据统计**：用户活动数据的统计和分析

## 数据库设计
项目使用MySQL数据库，主要包含以下表：
- user：用户表
- health_data：健康数据表
- task：任务表
- exercise_record：运动记录表
- exercise_route：运动路线表
- heart_rate_record：心率记录表
- meditation_category：冥想分类表
- meditation_course：冥想课程表
- meditation_record：冥想记录表
- activity_stats：活动统计表

详细的数据库结构请参考`src/main/resources/db/init.sql`文件。

## 接口文档
项目集成了Swagger，启动应用后可以通过以下地址访问接口文档：
```
http://localhost:8080/api/swagger-ui/index.html
```

## 快速开始
### 环境要求
- JDK 1.8+
- Maven 3.6+
- MySQL 5.7+

### 安装步骤
1. 克隆项目
```bash
git clone https://github.com/yourusername/hm-server.git
cd hm-server
```

2. 创建数据库并导入初始数据
```bash
mysql -u root -p < src/main/resources/db/init.sql
```

3. 修改数据库配置
编辑`src/main/resources/application.yml`文件，修改数据库连接信息：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/health_manager?useUnicode=true&characterEncoding=utf-8&useSSL=false
    username: your_username
    password: your_password
```

4. 构建并运行项目
```bash
mvn clean package
java -jar target/hm-server-0.0.1-SNAPSHOT.jar
```

## 开发指南
### 添加新功能
1. 创建实体类
2. 创建Mapper接口和XML映射文件
3. 创建Service接口和实现类
4. 创建Controller类
5. 添加Swagger注解

### 代码规范
- 遵循阿里巴巴Java开发手册
- 使用Lombok简化代码
- 使用统一的异常处理
- 使用统一的返回结果格式

## 贡献指南
1. Fork 项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建Pull Request

## 许可证
MIT License 