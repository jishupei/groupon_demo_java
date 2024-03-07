## Alipay Demo

支付宝API集成Java示例工程，实现OpenAPI调用、消息MSGAPI回调处理、支付异步通知处理、SPI回调处理等功能。

## 本地调试
#### 1.下载代码
下载代码至本地。
#### 2.替换变量
application.yaml下替换公私钥等变量
#### 2.服务访问
[安装 Postman](https://www.getpostman.com/downloads/)
运行dotnet run命令，快速启动，启动 Postman测试以下接口
[OpenAPI请求](http://localhost/api/openapi) http://localhost/api/openapi POST 可以通过Postman直接发起调用
[消息MSGAPI回调](http://localhost/api/msgCallback) http://localhost/api/msgCallback POST 可以通过Postman直接发起调用
[支付异步通知回调](http://localhost/api/payNotify) http://localhost/api/payNotify POST 可以通过Postman直接发起调用
[SPI回调](http://localhost/api/spi) http://localhost/api/spi POST 可以通过Postman直接发起调用
#### 3.查看日志
在项目路径下，执行如下命令：
```powershell
cd runtime/log
```
即可看到相关日志文件。

## 小程序云调试
打开右侧OpenPort，开通公网访问。选择的端口必须与application.yaml中配置的server.port一致。

## 目录结构说明

```
.
├── Dockerfile                      Dockerfile文件
├── LICENSE                         LICENSE文件
├── README.md                       README.md文件
├── src                             源文件目录,包含mvc目录、文件
```

### LICENSE
MIT