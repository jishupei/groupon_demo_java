/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.alipay.demo.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author jishupei.jsp
 * @version : AlipaySdkUtil, v0.1 2024年03月04日 3:18 下午 jishupei.jsp Exp $
 */
@Component
public class AlipaySdkUtil {
    private AlipayClient alipayClient;

    @Value("${alipay.appid}")
    private String appId;

    @Value("${alipay.privatekey}")
    private String privateKey;

    @Value("${alipay.alipaypublickey}")
    private String alipayPublicKey;

    @PostConstruct
    public void init() throws AlipayApiException {
        // 初始化v2 SDK
        this.alipayClient = new DefaultAlipayClient(getAlipayConfig());
    }

    public AlipayClient getAlipayClient() {
        return alipayClient;
    }

    private AlipayConfig getAlipayConfig() {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl("https://openapi.alipay.com/gateway.do");
        alipayConfig.setAppId(appId);
        alipayConfig.setPrivateKey(privateKey);
        alipayConfig.setAlipayPublicKey(alipayPublicKey);
        alipayConfig.setFormat("json");
        alipayConfig.setCharset("UTF-8");
        alipayConfig.setSignType("RSA2");
        return alipayConfig;
    }
}
