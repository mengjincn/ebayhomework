# 用户管理系统

这是一个基于 Spring Boot 的简单用户管理系统，实现了基于角色的访问控制。它允许管理员添加用户访问权限，并允许用户根据其权限访问特定资源。

## 功能特性

- 使用 Base64 编码的头部进行基于角色的身份验证
- 管理员功能：添加用户访问权限
- 用户对特定资源的访问控制
- 使用文件持久化存储用户访问信息
- 标准化的 API 响应，便于客户端处理

## 系统要求

- JDK 21
- Maven 3.6 或更高版本


## API 端点

### 1. 添加用户访问权限（仅限管理员）

- **URL**: `/admin/addUser`
- **方法**: POST
- **需要认证**: 是（管理员角色）
- **请求头**:
  - `Authorization`: Base64 编码的用户信息
- **请求体**:
  ```json
  {
    "userId": 123456,
    "endpoints": ["资源A", "资源B", "资源C"]
  }
  ```
- **成功响应**:
  - **状态码**: 200
  - **内容**: 
    ```json
    {
      "code": 200,
      "message": "用户访问权限添加成功"
    }
    ```
- **错误响应**:
  - **状态码**: 403
  - **内容**: 
    ```json
    {
      "code": 403,
      "message": "访问被拒绝：需要管理员角色"
    }
    ```

### 2. 访问资源（用户）

- **URL**: `/user/{resource}`
- **方法**: GET
- **需要认证**: 是
- **请求头**:
  - `Authorization`: Base64 编码的用户信息
- **URL 参数**: 
  - `resource`: 用户尝试访问的资源
- **成功响应**:
  - **状态码**: 200
  - **内容**: 
    ```json
    {
      "code": 200,
      "message": "已授予对 {resource} 的访问权限"
    }
    ```
- **错误响应**:
  - **状态码**: 403
  - **内容**: 
    ```json
    {
      "code": 403,
      "message": "拒绝访问 {resource}"
    }
    ```

## 身份验证

所有请求都必须包含一个 `Authorization` 请求头，其中包含 Base64 编码的 JSON 字符串，包含用户信息。解码后的 JSON 应具有以下格式：

```json
{
  "userId": 123456,
  "accountName": "用户名",
  "role": "admin" 或 "user"
}
```

## 错误处理

所有 API 响应都遵循标准格式：

```json
{
  "code": 400,
  "message": "错误描述"
}
```

`code` 字段包含 HTTP 状态码，`message` 字段提供错误的描述。

## 设置和运行

1. 确保您的系统上安装了 Java 11 和 Maven。
2. 将仓库克隆到您的本地机器。
3. 导航到项目目录。
4. 运行以下命令启动应用程序：
   ```
   mvn spring-boot:run
   ```
5. 应用程序将启动并在 `http://localhost:8080` 上可用。

## 测试

您可以使用 cURL、Postman 或任何 HTTP 客户端来测试 API 端点。记得在您的请求中包含带有 Base64 编码用户信息的 `Authorization` 头。

curl测试命令：
```shell
# 测试添加用户访问权限
curl -X POST --location "http://localhost:8080/admin/addUser" \
    -H "Authorization: ewoidXNlcklkIjoxMjM0NTYsCiJhY2NvdW50TmFtZSI6ICJYWFhYWFhYIiwKInJvbGUiOiAiYWRtaW4iCn0=" \
    -H "Content-Type: application/json" \
    -d '{
          "userId": 42,
          "endpoint": [
            "resource D",
            "resource B",
            "resource C"
          ]
        }'
# 测试访问资源resource B
curl -X GET --location "http://localhost:8080/user/resource%20B" \
    -H "Authorization: ewoidXNlcklkIjo0MiwKImFjY291bnROYW1lIjogIlhYWFhYWFgiLAoicm9sZSI6ICJhZG1pbiIKfQ=="
```

## 注意事项

- 本系统使用基于文件的持久化来存储用户访问信息。在生产环境中，您可能需要考虑使用数据库以获得更好的可扩展性和性能。
- 确保在您的客户端应用程序中安全地处理 Base64 编码的头部，以防止未经授权的访问。

如有任何问题或功能请求，请在项目仓库中开启一个 issue。
