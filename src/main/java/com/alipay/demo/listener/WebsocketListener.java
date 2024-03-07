/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.alipay.demo.listener;

import com.alipay.api.msg.AlipayMsgClient;
import com.alipay.api.msg.MsgHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author jishupei.jsp
 * @version : WebsocketListener, v0.1 2024年03月07日 4:45 下午 jishupei.jsp Exp $
 */
@Component
public class WebsocketListener implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${alipay.appid}")
    private String appId;

    @Value("${alipay.privatekey}")
    private String privateKey;

    @Value("${alipay.alipaypublickey}")
    private String alipayPublicKey;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        String serverHost = "openchannel.alipay.com";
        boolean isSSL = true;
        String signType = "RSA2";
        String charset = "UTF-8";
        AlipayMsgClient alipayMsgClient = AlipayMsgClient.getInstance(appId);
        try {
            alipayMsgClient.setConnector(serverHost, isSSL);
            alipayMsgClient.setSecurityConfig(signType, privateKey, alipayPublicKey);
            alipayMsgClient.setCharset(charset);
            alipayMsgClient.setLoadTest(false);

            // 消息接收
            alipayMsgClient.setMessageHandler(new MsgHandler() {
                public void onMessage(String msgApi, String msgId, String bizContent) {
                    System.out.println("===========================================");
                    System.out.println("receive message. msgApi:" + msgApi + " msgId:" + msgId + " bizContent:" + bizContent);
                    System.out.println("===========================================");
                }
            });

            alipayMsgClient.connect();

            System.out.println("websocket listener start successfully......");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
