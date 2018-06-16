# spring-cloud-base
微服务基础框架，基于 SpringCloud 及 SpringBoot 开发。 使用 Oauth2 统一授权、认证, Oauth示例客户端使用 Vue 开发，具有用户管理、 资源管理、 角色管理等模块，后端包括授权中心、 基础数据中心（资源服务器）等应用，可作为微服务快速开发脚手架。  可通过 docker 快速构建部署。
  
Demo website：http://112.74.60.248:8080/   username: test  password: 123456  


## Model
图片待续  
  
## 相关博文
* consul 注册中心  
* auth-center 授权中心  
* api-gateway 网关  
* main-data 基础数据模块  
* common  
* db-spring-boot-starter   
* auth-spring-boot-starter  


## Quick Start  
### 项目结构  
```
├─spring-cloud-base
│  │  
│  ├─api-gateway--------------网关负载中心
│  │ 
│  ├─auth-center-----------------服务授权中心
│  │  ├─auth-center-api----------------授权中心公共模块
│  │  ├─auth-center-provider----------------授权中心服务端
│  │  ├─auth-spring-boot-autoconfigure----------------授权中心autoconfigure
│  │  └─auth-spring-boot-starter----------------授权中心starter模块
│  │ 
│  ├─common----------------通用脚手架
│  │ 
│  ├─config-git-----------------配置中心
│  │ 
│  ├─db-mybatis-pagehelper---------------Mybatis通用Mapper、分页模块
│  │  ├─db-spring-boot-autoconfigure----------------autoconfigure
│  │  ├─db-spring-boot-samples----------------db模块使用示例
│  │  └─db-spring-boot-starter----------------db starter
│  │ 
│  ├─docker-compose---------------docker-compose编排
│  │   
│  ├─main-data--------------基础数据模块
│  │  ├─main-data-api----------------基础数据公共模块
│  │  ├─main-data-client----------------基础数据模块客户端
│  │  ├─main-data-provider----------------基础数据模块服务端
│  │
│  └─web-app--------------vue前端应用
│
```

### 依赖环境    
JDK8、 Maven、 Mysql、 Redis、Consul(暂不支持0.7.3以上)、 Nodejs、 Docker(可选)、 docker-compose(可选)  
### 运行步骤  
1. Mysql导入数据： 运行main-data/schema.sql 文件  
2. 更改 application配置文件（服务授权中心、网关负载中心、基础数据模块），jdbcUrl、 consul、 redis、 key-uri(服务授权中心公钥，资源服务器需配置)  
3. 依此启动 mysql、 redis、 consul、 auth-center/auth-center-provider/AuthCenterProviderApplication.main(服务授权中心)、 api-gateway/ApiGatewayApplication.main()(网关负载中心)、 main-data/main-data-provider/MainDataApplication.main()(基础数据模块)、 web-app(前端vue项目)  

### 通过 docker-compose 快速启动  
1. 需要 docker-compose 环境，windows 可安装 DockerToolbox。  
需先通过 Maven 插件 docker-maven-plugin 打包镜像，  
```
<build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>docker-maven-plugin</artifactId>
                <version>${docker.plugin.version}</version>
                <configuration>
                    <imageName>${project.artifactId}</imageName>
                    <dockerDirectory>src/main/docker</dockerDirectory>
                    <resources>
                        <resource>
                            <targetPath>/</targetPath>
                            <directory>${project.build.directory}</directory>
                            <include>${project.build.finalName}.jar</include>
                        </resource>
                    </resources>
                </configuration>
            </plugin>
        </plugins>
</build>
```
  
2. 分别构建 auth-center-provider、 main-data-provider、 api-gateway：  
```
mvn clean
mvn package docker:build
```
构建 node-app:  
```
cd /spring-cloud-base/web-app
docker build -t node-app .
```
  
3. 更改配置 docker-compose.yml：  
```
     node-app:
     image: node-app
     ports:
      - '8080:8080'
     depends_on:
      - 'auth-center'
      - 'api-gateway'
      # 基础路径，授权时的redirectUrl，为 宿主机IP:本容器映射端口
      - BASE_URL=http://192.168.99.100:8080
      # 后台接口，对于网关
      - SERVER_URL=http://192.168.99.100:18000
      # 授权中心
      - AUTH_URL=http://192.168.99.100:18001/auth
```
  
4. 运行  
```
cd /spring-cloud-base/docker-compose
docker-compose up [-d]
```

### 通过共享镜像启动  

## Preview  
* 登陆页：   
![avatar](http://112.74.60.248:8080/image/login.png)
  
* 用户管理：  
![avatar](http://112.74.60.248:8080/image/user.png)
  
* 角色管理：  
![avatar](http://112.74.60.248:8080/image/role.png)
  
* 应用管理：  
![avatar](http://112.74.60.248:8080/image/client.png)
  
* 系统管理：  
![avatar](http://112.74.60.248:8080/image/system.png)
  
* 模块管理：  
![avatar](http://112.74.60.248:8080/image/module.png)
  
