# Shop-Demo 电商项目需求与技术方案

## 一、项目概述

### 1.1 项目名称
Shop-Demo - 在线电商购物平台

### 1.2 项目目标
构建一个功能完整的B2C电商平台，支持用户浏览商品、购物车管理、订单处理、在线支付等核心电商功能。

### 1.3 目标用户
- 普通消费者（前台用户）
- 商家/管理员（后台管理）

---

## 二、功能需求

### 2.1 用户模块
- [ ] 用户注册（手机号/邮箱）
- [ ] 用户登录/登出
- [ ] 密码找回/修改
- [ ] 用户信息管理
- [ ] 收货地址管理

### 2.2 商品模块
- [ ] 商品分类管理（多级分类）
- [ ] 商品列表展示
- [ ] 商品详情页
- [ ] 商品搜索（关键词、分类筛选）
- [ ] 商品SKU管理
- [ ] 商品库存管理

### 2.3 购物车模块
- [ ] 添加商品到购物车
- [ ] 修改购物车商品数量
- [ ] 删除购物车商品
- [ ] 购物车商品选择
- [ ] 购物车价格计算

### 2.4 订单模块
- [ ] 订单创建
- [ ] 订单支付
- [ ] 订单状态管理（待支付、待发货、待收货、已完成、已取消）
- [ ] 订单查询
- [ ] 订单详情

### 2.5 支付模块
- [ ] 支付宝支付集成
- [ ] 微信支付集成
- [ ] 支付回调处理
- [ ] 退款处理

### 2.6 后台管理模块
- [ ] 管理员登录
- [ ] 商品管理（CRUD）
- [ ] 分类管理
- [ ] 订单管理
- [ ] 用户管理
- [ ] 数据统计

---

## 三、技术方案

### 3.1 技术栈选型

| 层次 | 技术选型 | 版本 |
|------|---------|------|
| 开发语言 | Java | 17 |
| 开发框架 | Spring Boot | 3.2.x |
| ORM框架 | MyBatis-Plus | 3.5.x |
| 数据库 | MySQL | 8.0 |
| 缓存 | Redis | 7.x |
| 权限框架 | Spring Security + JWT | - |
| API文档 | Swagger/OpenAPI | 3.0 |
| 构建工具 | Maven | 3.9.x |
| 前端框架 | Vue 3 + Element Plus | 3.x |

### 3.2 项目架构

```
shop-demo/
├── shop-common/          # 公共模块（工具类、常量、异常）
├── shop-model/           # 实体模块（Entity、DTO、VO）
├── shop-mapper/          # 数据访问层
├── shop-service/         # 业务逻辑层
├── shop-web/             # Web接口层（Controller）
├── shop-admin/           # 后台管理接口
└── shop-gateway/         # API网关（可选）
```

### 3.3 数据库设计

#### 核心表结构

**用户表 (t_user)**
- id, username, password, phone, email, nickname, avatar, status, create_time, update_time

**商品分类表 (t_category)**
- id, parent_id, name, level, sort, icon, status, create_time, update_time

**商品表 (t_product)**
- id, category_id, name, subtitle, main_image, sub_images, detail, price, stock, status, create_time, update_time

**商品SKU表 (t_product_sku)**
- id, product_id, sku_code, price, stock, specs, create_time, update_time

**购物车表 (t_cart)**
- id, user_id, product_id, sku_id, quantity, checked, create_time, update_time

**订单表 (t_order)**
- id, order_no, user_id, total_amount, pay_amount, freight_amount, pay_type, status, payment_time, delivery_time, receive_time, create_time, update_time

**订单明细表 (t_order_item)**
- id, order_id, order_no, product_id, product_name, product_image, sku_id, sku_specs, price, quantity, total_price

**收货地址表 (t_user_address)**
- id, user_id, name, phone, province, city, district, detail_address, is_default, create_time, update_time

### 3.4 接口规范

#### RESTful API 设计
- GET /api/products - 获取商品列表
- GET /api/products/{id} - 获取商品详情
- POST /api/cart - 添加购物车
- PUT /api/cart/{id} - 更新购物车
- DELETE /api/cart/{id} - 删除购物车
- POST /api/orders - 创建订单
- GET /api/orders - 获取订单列表
- GET /api/orders/{id} - 获取订单详情

#### 统一响应格式
```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

### 3.5 安全方案
- 密码加密：BCrypt
- 接口认证：JWT Token
- 敏感数据：AES加密
- SQL注入防护：MyBatis参数绑定
- XSS防护：输入过滤

---

## 四、开发计划

### Phase 1: 基础框架搭建
1. 项目骨架搭建（Maven多模块）
2. 数据库设计与初始化脚本
3. 公共模块开发（工具类、统一异常、统一响应）
4. 用户模块开发

### Phase 2: 核心业务开发
5. 商品模块开发
6. 购物车模块开发
7. 订单模块开发

### Phase 3: 支付与完善
8. 支付模块集成
9. 后台管理模块
10. 接口文档完善
11. 单元测试

### Phase 4: 优化上线
12. 性能优化
13. 安全加固
14. 部署上线

---

## 五、目录结构约定

```
shop-demo/
├── pom.xml                           # 父POM
├── plan.md                           # 项目计划文档
├── CODE_STYLE.md                     # 代码规范文档
├── sql/                              # SQL脚本
│   └── init.sql
├── shop-common/                      # 公共模块
│   ├── pom.xml
│   └── src/main/java/com/shop/common/
│       ├── config/                   # 配置类
│       ├── constant/                 # 常量
│       ├── enums/                    # 枚举
│       ├── exception/                # 异常
│       ├── result/                   # 统一响应
│       └── utils/                    # 工具类
├── shop-model/                       # 实体模块
│   ├── pom.xml
│   └── src/main/java/com/shop/model/
│       ├── entity/                   # 数据库实体
│       ├── dto/                      # 数据传输对象
│       ├── vo/                       # 视图对象
│       └── query/                    # 查询参数
├── shop-mapper/                      # Mapper模块
│   ├── pom.xml
│   └── src/main/java/com/shop/mapper/
├── shop-service/                     # Service模块
│   ├── pom.xml
│   └── src/main/java/com/shop/service/
│       ├── impl/                     # 实现类
│       └── *Service.java             # 接口
└── shop-web/                         # Web模块
    ├── pom.xml
    └── src/
        ├── main/
        │   ├── java/com/shop/web/
        │   │   ├── controller/       # 控制器
        │   │   ├── interceptor/      # 拦截器
        │   │   └── ShopApplication.java
        │   └── resources/
        │       ├── application.yml
        │       └── mapper/           # MyBatis XML
        └── test/                     # 测试
```

---

## 六、参考资料

- Spring Boot 官方文档: https://spring.io/projects/spring-boot
- MyBatis-Plus 官方文档: https://baomidou.com/
- Vue 3 官方文档: https://vuejs.org/
