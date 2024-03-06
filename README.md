## Alipay Demo

支付宝API集成Java示例工程，实现OpenAPI调用、消息MSGAPI回调处理、支付异步通知处理、SPI回调处理等功能。

## 本地调试
#### 1.下载代码
下载代码至本地。
#### 2.替换代码变量
替换示例工程中controller/AlipayDemoController.java 以下几处变量:
* ${environment}替换为sandbox 或 prod，根据实际环境设置
* ${appId} 替换为应用appId
* ${sandboxAlipayPublicKey} 替换为沙箱环境支付宝公钥
* ${sandboxAppPrivateKey} 替换为沙箱环境应用私钥
* ${prodAlipayPublicKey} 替换为线上环境支付宝公钥
* ${prodAppPrivateKey} 替换为线上环境应用私钥
```java
    public static Map<String, Map<String, String>> CONFIG_MAG = new HashMap<String, Map<String, String>>();

    static {
        // 沙箱环境配置
        Map<String, String> sandbox = new HashMap<String, String>();
        // AppId
        sandbox.put("appId", "${appId}");
        // 支付宝沙箱网关地址
        sandbox.put("gatewayUrl", "https://openapi-sandbox.dl.alipaydev.com/gateway.do");
        //  支付宝沙箱公钥
        sandbox.put("alipayPublicKey", "${sandboxAlipayPublicKey}");
        // 应用沙箱私钥
        sandbox.put("appPrivateKey", "${sandboxAppPrivateKey}");

        // 线上环境配置
        Map<String, String> prod = new HashMap<>();
        // AppId
        prod.put("appId", "${appId}");
        // 支付宝线上网关地址
        prod.put("gatewayUrl", "https://openapi.alipay.com/gateway.do");
        // 支付宝线上公钥
        prod.put("alipayPublicKey", "${prodAlipayPublicKey}");
        // 应用线上私钥，需要替换为线上环境使用的应用私钥，注意 Java 语言需要 PKCS8 格式的密钥。
        prod.put("appPrivateKey", "${prodAppPrivateKey}");

        CONFIG_MAG.put("sandbox", sandbox);
        CONFIG_MAG.put("prod", prod);
        }
```
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