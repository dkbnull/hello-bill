<p align="center">
  <img src="hello-bill-boot/src/main/resources/static/img/logo.svg" width="120" height="120" alt="HelloBill Logo">
</p>

<h1 align="center">Hello Bill</h1>

<p align="center">
  <strong>个人智能记账系统</strong>
</p>

<p align="center">
  <a href="https://github.com/dkbnull/hello-bill" target="_blank">
    <img src="https://img.shields.io/badge/GitHub-访问地址-blue?logo=github">
  </a>
  <a href="https://gitee.com/dkbnull/hello-bill" target="_blank">
    <img src="https://img.shields.io/badge/Gitee-访问地址-red?logo=gitee">
  </a>
  <img src="https://img.shields.io/badge/JDK-1.8-green?logo=java">
  <img src="https://img.shields.io/badge/Spring%20Boot-2.7.18-brightgreen?logo=springboot">
  <img src="https://img.shields.io/badge/License-MIT-yellow">
</p>

## 项目简介

Hello Bill 是一款轻量级个人智能记账系统，专注于日常收支的精细化管理。通过智能账单导入、多级分类体系、可视化报表和资产管理，帮助用户全面掌握个人财务状况。

## 技术栈

| 层级    | 技术           | 版本     | 说明       |
|-------|--------------|--------|----------|
| 后端框架  | Spring Boot  | 2.7.18 | 核心框架     |
| ORM   | MyBatis-Plus | 3.5.9  | 数据库操作    |
| 安全认证  | java-jwt     | 4.4.0  | 用户身份验证   |
| 前端框架  | LayUI        | -      | UI 组件库   |
| 图表    | ECharts      | -      | 数据可视化    |
| 数据库   | MySQL        | 8.4    | 数据存储     |
| CSV解析 | OpenCSV      | 5.10   | 账单文件解析   |
| JSON  | FastJSON     | 1.2.83 | JSON 序列化 |
| 构建工具  | Maven        | 3.6+   | 项目构建     |

## 项目结构

```
hello-bill
├── hello-bill-boot                    # 启动模块
├── hello-bill-common-core             # 公共核心模块（工具类、常量、DTO、异常处理）
├── hello-bill-common-security         # 安全模块（JWT 认证、拦截器）
└── hello-bill-db                      # 数据库模块
```

## 主要功能

### 📈 收支报表

- **总收支报表**：月度/年度收支统计与趋势分析
- **净收支报表**：净收入趋势追踪
- **支出分类报表**：按一级/二级分类查看支出分布，支持钻取查看分类明细
- **收入分类报表**：按分类查看收入构成
- **专项报表**：日常支出、生活支出、子女支出、子女教育等专项报表
- ECharts 可视化图表展示

### 💰 收支管理

- **支出明细**：支出记账、查询、修改、删除
- **收入明细**：收入记账、查询、修改、删除
- 支出记账支持直接录入时间

### 📥 智能账单导入

- **多平台支持**：支付宝、微信、京东金融账单导入
- **智能识别**：自动识别账单来源平台并解析
- **智能学习**：自动学习历史账单分类和详情模式，自动匹配分类
- **批量处理**：支持 CSV 格式批量导入
- **账单管理**：支持查询、修改、删除、确认导入的账单

### 🏦 资产管理

- 实时查看账户资产结余
- 每月资产变动追踪
- 定时任务自动计算资产负债信息（每月1号执行）

### 📊 分类管理

- 收入/支出多级分类体系
- 支持一级分类和二级分类的层级结构
- 分类状态管理（启用/禁用）
- 排序自定义

## 快速开始

### 环境要求

- JDK 1.8
- Maven 3.6+
- MySQL 8.0+

### 安装步骤

1. **克隆项目**

```bash
git clone https://github.com/dkbnull/hello-bill.git
cd hello-bill
```

2. **配置数据库**

修改 `hello-bill-boot/src/main/resources/application.yml` 中的数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/hellobill?characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true
    username: your_username
    password: your_password
```

3. **构建运行**

```bash
mvn clean package -DskipTests
java -jar hello-bill-boot/target/hello-bill-boot.jar
```

4. **访问系统**

浏览器打开 `http://localhost:8080` 即可访问系统。

## 版本历史

详见 [CHANGELOG.md](CHANGELOG.md)

## 许可证

[MIT License](LICENSE)
