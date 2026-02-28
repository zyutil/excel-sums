# Excel 数字求和工具
> 这是一个基于 **Spring Boot + Thymeleaf + Bootstrap** 的 Web 应用，用于从 Excel 文件中读取数字，计算组合和，满足用户自定义的目标值条件。支持 **小数数据**，并保证每个 Excel 单元格数字只使用一次。
## 功能简介
- 支持上传 Excel（`.xls` / `.xlsx`）文件
- Excel 第一列为数字，可包含小数
- 用户可以输入一个或多个目标值（用逗号分隔）
- 自动计算出所有可能的组合，满足：
    - 每个数字只能使用一次
    - 优先使用大数字，使组合尽量短
    - 每个目标值最多显示 5 条组合
- 前端显示组合和总和，直观易读
## 示例
### 输入
 - 假设 Excel 第一列为： 
    ```
    1.5
    2.3
    3.2
    7.0
    8.0
    ```
 - 目标值输入：
    ```
    10.2
    ```
### 可能输出结果：
| 组合                      | 和    |
|-------------------------|------|
| [8.0, 2.3]              | 10.3 |
| [7.0, 3.2]              | 10.2 |
| [3.2, 2.3, 1.5, 3.2...] | ...  |

## Docker 打包与上传

### 1. 本地构建镜像

```bash
docker build -t excel-sums:latest .
```

### 2. 打标签并推送到镜像仓库（推荐）

以 Docker Hub 为例：

```bash
docker login
docker tag excel-sums:latest <你的DockerHub用户名>/excel-sums:latest
docker push <你的DockerHub用户名>/excel-sums:latest
```

### 3. 服务器拉取并运行

在服务器执行：

```bash
docker pull <你的DockerHub用户名>/excel-sums:latest
docker run -d --name excel-sums -p 8080:8080 --restart unless-stopped <你的DockerHub用户名>/excel-sums:latest
```

访问地址：`http://<服务器IP>:8080`

### 4. 不经过镜像仓库，直接上传到服务器（可选）

```bash
docker save -o excel-sums.tar excel-sums:latest
scp excel-sums.tar <服务器用户>@<服务器IP>:/tmp/
ssh <服务器用户>@<服务器IP> "docker load -i /tmp/excel-sums.tar && docker run -d --name excel-sums -p 8080:8080 --restart unless-stopped excel-sums:latest"
```
