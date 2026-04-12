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
  <img src="https://img.shields.io/badge/JDK-8-green?logo=java">
  <img src="https://img.shields.io/badge/Spring%20Boot-2.7-brightgreen?logo=springboot">
  <img src="https://img.shields.io/badge/License-MIT-yellow">
</p>

## 项目简介

Hello Bill 是一款轻量级个人财务管理系统，专注于日常收支的精细化管理。通过智能账单导入、多级分类体系和可视化报表，帮助用户全面掌握个人财务状况。

## 技术栈

| 层级   | 技术              | 说明     |
|------|-----------------|--------|
| 后端框架 | Spring Boot 2.7 | 核心框架   |
| ORM  | MyBatis-Plus    | 数据库操作  |
| 安全认证 | JWT             | 用户身份验证 |
| 前端框架 | LayUI           | UI 组件库 |
| 图表   | ECharts         | 数据可视化  |
| 数据库  | MySQL           | 数据存储   |
| 构建工具 | Maven           | 项目构建   |

## 项目结构

```
hello-bill
├── hello-bill-boot              # 启动模块（Controller、Service、静态资源）
├── hello-bill-common-core       # 公共核心模块（工具类、常量、DTO）
├── hello-bill-common-security   # 安全模块（JWT 认证）
└── hello-bill-db                # 数据库模块（Entity、Mapper、Repository）
```

## 主要功能

### 📊 收支分类管理

- 收入/支出多级分类体系
- 支持一级分类和二级分类的层级结构
- 分类状态管理和排序自定义

### 📥 智能账单导入

- **多平台支持**：支付宝、微信、京东金融账单导入
- **智能学习**：自动学习历史账单分类和详情模式
- **批量处理**：支持 CSV 格式批量导入

### 📈 丰富报表统计

- 月度/年度收支统计与趋势分析
- 各类支出月度变化追踪
- 支持钻取查看分类明细
- ECharts 可视化图表

### 💰 资产管理

- 实时查看账户资产结余
- 每月资产变动追踪

## 快速开始

### 环境要求

- JDK 8
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
    url: jdbc:mysql://localhost:3306/hellobill?useUnicode=true&characterEncoding=utf-8
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
