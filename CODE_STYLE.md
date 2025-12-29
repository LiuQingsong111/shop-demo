# Shop-Demo Java 代码规范

本文档定义了本项目 Java 代码必须遵循的规范和最佳实践。

---

## 一、通用编码规范

### 1.1 命名规范

| 类型 | 规范 | 示例 |
|------|------|------|
| 包名 | 全小写，用点分隔 | `com.shop.service` |
| 类名 | 大驼峰（PascalCase） | `UserService`, `OrderController` |
| 方法名 | 小驼峰（camelCase） | `getUserById`, `createOrder` |
| 变量名 | 小驼峰（camelCase） | `userName`, `orderList` |
| 常量名 | 全大写，下划线分隔 | `MAX_RETRY_COUNT`, `DEFAULT_PAGE_SIZE` |
| 数据库表名 | 小写，下划线分隔，t_前缀 | `t_user`, `t_order` |
| 数据库字段 | 小写，下划线分隔 | `user_name`, `create_time` |

### 1.2 类命名约定

```
Controller层：XxxController
Service接口：XxxService
Service实现：XxxServiceImpl
Mapper层：XxxMapper
实体类：Xxx (与表名对应，去掉t_前缀)
DTO类：XxxDTO
VO类：XxxVO
Query类：XxxQuery
枚举类：XxxEnum
常量类：XxxConstant
工具类：XxxUtils
异常类：XxxException
配置类：XxxConfig
```

---

## 二、代码结构规范

### 2.1 类结构顺序

```java
public class Example {
    // 1. 静态常量
    private static final String CONSTANT = "value";

    // 2. 静态变量
    private static int staticVar;

    // 3. 实例变量（按访问修饰符排序：public -> protected -> default -> private）
    private Long id;
    private String name;

    // 4. 构造方法
    public Example() {}

    // 5. 公共方法
    public void publicMethod() {}

    // 6. 私有方法
    private void privateMethod() {}

    // 7. Getter/Setter（使用Lombok时可省略）
    // 8. equals/hashCode/toString
}
```

### 2.2 方法规范

- 方法长度不超过 80 行
- 方法参数不超过 5 个，超过时使用对象封装
- 每个方法只做一件事
- 避免过深的嵌套（最多3层）

---

## 三、分层架构规范

### 3.1 Controller层

```java
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "用户管理", description = "用户相关接口")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取用户")
    public Result<UserVO> getById(@PathVariable Long id) {
        return Result.success(userService.getById(id));
    }

    @PostMapping
    @Operation(summary = "创建用户")
    public Result<Long> create(@RequestBody @Valid UserDTO userDTO) {
        return Result.success(userService.create(userDTO));
    }
}
```

**Controller 规范：**
- 使用 `@RestController` 注解
- 使用 `@RequiredArgsConstructor` 注入依赖（构造器注入）
- 不写业务逻辑，只做参数校验和调用Service
- 统一返回 `Result<T>` 对象
- 使用 Swagger 注解描述接口

### 3.2 Service层

```java
// 接口
public interface UserService {
    UserVO getById(Long id);
    Long create(UserDTO userDTO);
    void update(UserDTO userDTO);
    void delete(Long id);
}

// 实现
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public UserVO getById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }
        return UserConverter.INSTANCE.toVO(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(UserDTO userDTO) {
        // 业务校验
        checkUserNameExists(userDTO.getUserName());

        User user = UserConverter.INSTANCE.toEntity(userDTO);
        user.setCreateTime(LocalDateTime.now());
        userMapper.insert(user);

        log.info("创建用户成功, userId={}", user.getId());
        return user.getId();
    }
}
```

**Service 规范：**
- 接口与实现分离
- 实现类使用 `@Service` 注解
- 涉及多表操作时使用 `@Transactional`
- 业务异常使用自定义异常抛出
- 使用 `@Slf4j` 记录日志

### 3.3 Mapper层

```java
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名查询用户
     */
    @Select("SELECT * FROM t_user WHERE user_name = #{userName}")
    User selectByUserName(@Param("userName") String userName);

    /**
     * 复杂查询使用XML配置
     */
    List<User> selectByCondition(UserQuery query);
}
```

**Mapper 规范：**
- 继承 MyBatis-Plus 的 `BaseMapper<T>`
- 简单 SQL 使用注解
- 复杂 SQL 使用 XML 文件
- 参数使用 `@Param` 注解

---

## 四、实体类规范

### 4.1 Entity（数据库实体）

```java
@Data
@TableName("t_user")
public class User {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String userName;

    private String password;

    private String phone;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
```

### 4.2 DTO（数据传输对象）

```java
@Data
public class UserDTO {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 4, max = 20, message = "用户名长度4-20位")
    private String userName;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度6-20位")
    private String password;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;
}
```

### 4.3 VO（视图对象）

```java
@Data
public class UserVO {

    private Long id;

    private String userName;

    private String phone;

    private String nickname;

    private String avatar;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
```

### 4.4 Query（查询参数）

```java
@Data
public class UserQuery extends PageQuery {

    private String userName;

    private String phone;

    private Integer status;
}

@Data
public class PageQuery {

    @Min(value = 1, message = "页码最小为1")
    private Integer pageNum = 1;

    @Max(value = 100, message = "每页最多100条")
    private Integer pageSize = 10;
}
```

---

## 五、统一响应规范

### 5.1 Result 类

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> fail(ResultCode resultCode) {
        return new Result<>(resultCode.getCode(), resultCode.getMessage(), null);
    }

    public static <T> Result<T> fail(Integer code, String message) {
        return new Result<>(code, message, null);
    }
}
```

### 5.2 ResultCode 枚举

```java
@Getter
@AllArgsConstructor
public enum ResultCode {

    SUCCESS(200, "操作成功"),
    FAIL(500, "操作失败"),

    // 参数错误 4xx
    PARAM_ERROR(400, "参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),

    // 业务错误 5xxx
    USER_NOT_FOUND(5001, "用户不存在"),
    USER_EXISTS(5002, "用户已存在"),
    PASSWORD_ERROR(5003, "密码错误"),

    // 订单错误 6xxx
    ORDER_NOT_FOUND(6001, "订单不存在"),
    ORDER_STATUS_ERROR(6002, "订单状态异常"),
    STOCK_NOT_ENOUGH(6003, "库存不足");

    private final Integer code;
    private final String message;
}
```

---

## 六、异常处理规范

### 6.1 自定义异常

```java
@Getter
public class BusinessException extends RuntimeException {

    private final Integer code;
    private final String message;

    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
```

### 6.2 全局异常处理

```java
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        log.warn("业务异常: code={}, message={}", e.getCode(), e.getMessage());
        return Result.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return Result.fail(ResultCode.PARAM_ERROR.getCode(), message);
    }

    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("系统异常", e);
        return Result.fail(ResultCode.FAIL);
    }
}
```

---

## 七、日志规范

### 7.1 日志级别使用

```java
@Slf4j
public class Example {

    public void process() {
        // DEBUG: 调试信息
        log.debug("处理开始, params={}", params);

        // INFO: 关键业务节点
        log.info("订单创建成功, orderId={}, userId={}", orderId, userId);

        // WARN: 警告信息，不影响业务
        log.warn("重试第{}次", retryCount);

        // ERROR: 错误信息，需要关注
        log.error("支付回调处理失败, orderNo={}", orderNo, e);
    }
}
```

### 7.2 日志格式

- 使用占位符 `{}` 而非字符串拼接
- 记录关键业务参数
- 异常日志带上异常对象

---

## 八、注释规范

### 8.1 类注释

```java
/**
 * 用户服务实现类
 *
 * @author shop-demo
 * @since 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {
}
```

### 8.2 方法注释

```java
/**
 * 根据用户ID获取用户信息
 *
 * @param id 用户ID
 * @return 用户信息VO
 * @throws BusinessException 用户不存在时抛出
 */
public UserVO getById(Long id) {
}
```

### 8.3 代码注释

```java
// 检查库存是否充足
if (stock < quantity) {
    throw new BusinessException(ResultCode.STOCK_NOT_ENOUGH);
}
```

---

## 九、安全规范

### 9.1 密码处理

```java
// 使用BCrypt加密
@Component
public class PasswordEncoder {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String encode(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
```

### 9.2 SQL注入防护

```java
// 正确：使用参数绑定
@Select("SELECT * FROM t_user WHERE user_name = #{userName}")
User selectByUserName(@Param("userName") String userName);

// 错误：拼接SQL（禁止）
@Select("SELECT * FROM t_user WHERE user_name = '" + userName + "'")
```

### 9.3 敏感数据处理

```java
// VO中隐藏敏感字段
@Data
public class UserVO {
    private Long id;
    private String userName;

    @JsonIgnore
    private String password;  // 不返回给前端

    @JsonSerialize(using = PhoneDesensitizer.class)
    private String phone;     // 脱敏处理
}
```

---

## 十、配置文件规范

### 10.1 application.yml

```yaml
server:
  port: 8080

spring:
  application:
    name: shop-demo

  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/shop_demo?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: root
    password: ${DB_PASSWORD:root}

  redis:
    host: localhost
    port: 6379
    password: ${REDIS_PASSWORD:}

mybatis-plus:
  mapper-locations: classpath*:mapper/**/*.xml
  configuration:
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  global-config:
    db-config:
      id-type: assign_id
      logic-delete-field: deleted
      logic-delete-value: 1
      logic-not-delete-value: 0

logging:
  level:
    com.shop: debug
```

---

## 十一、Git 提交规范

### 11.1 提交信息格式

```
<type>(<scope>): <subject>

<body>

<footer>
```

### 11.2 Type 类型

- `feat`: 新功能
- `fix`: 修复bug
- `docs`: 文档更新
- `style`: 代码格式（不影响功能）
- `refactor`: 重构
- `test`: 测试相关
- `chore`: 构建/工具相关

### 11.3 示例

```
feat(user): 添加用户注册功能

- 实现手机号注册
- 添加短信验证码校验
- 密码使用BCrypt加密

Closes #123
```

---

## 十二、代码生成检查清单

生成代码前，请确认：

- [ ] 遵循命名规范
- [ ] 使用 Lombok 简化代码
- [ ] Controller 只做转发，不写业务逻辑
- [ ] Service 接口与实现分离
- [ ] 使用 DTO/VO/Query 进行数据传输
- [ ] 参数使用 `@Valid` 校验
- [ ] 异常使用自定义 BusinessException
- [ ] 事务使用 `@Transactional(rollbackFor = Exception.class)`
- [ ] 日志使用 `@Slf4j` + 占位符
- [ ] 敏感信息不返回给前端
- [ ] SQL 使用参数绑定防止注入
