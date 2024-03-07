/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alipay.demo.controller;

import com.alipay.api.domain.AttributeVO;
import com.alipay.demo.service.AntMerchantShopService;
import com.alipay.demo.service.LocalItemService;
import com.alipay.demo.util.AlipaySdkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(value = "/item")
public class LocalItemController {

    @Autowired
    private AlipaySdkUtil alipaySdkUtil;

    @Autowired
    private LocalItemService localItemService;


    @PostMapping(value = "/create")
    public String create(@RequestBody Map<String, String> params) {

        String categoryId = localItemService.getCategoryId(params.get("itemType"));
        if (categoryId == null) {
            return "error";
        }
        return localItemService.createLocalItem(categoryId, params.get("productName"), params.get("outItemId"));
    }

    @PostMapping(value = "/delete")
    public String delete(@RequestBody Map<String, String> params) {
        return localItemService.deleteLocalItem(params.get("itemId"), params.get("outItemId"));
    }

    @PostMapping(value = "/query")
    public String query(@RequestBody Map<String, String> params) {
        return localItemService.queryItemDetail(params.get("itemId"), params.get("outItemId"));
    }

    /**
     * 在接收支付宝 MsgAPI 异步消息前需要配置应用网关地址，示例 Demo 中的端点为：<p>
     * http(s)://{domain}/api/msgCallback，其中 domain 需要为公网域名。
     */
    /*@ResponseBody
    @PostMapping(value = "/msgCallback")
    public String msgCallback(@RequestParam Map<String, String> params) throws AlipayApiException {
        System.out.println("收到支付宝 demo 测试消息, msg 接口: " + params.get("msg_method"));

        if (CollectionUtils.isEmpty(params)) {
            return "fail";
        }

        String alipayPublicKey = CONFIG_MAG.get(environment).get("alipayPublicKey");

        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayPublicKey, "UTF-8", "RSA2");
        //验签成功
        if (signVerified) {
            System.out.println("success");
            System.out.println("收到支付宝消息通知, 消息内容: " + params.get("biz_content"));
            return "success";
        } else {
            System.out.println("验签失败");
            return "fail";
        }
    }*/

    /**
     * 支付通知处理函数，示例 Demo 中的端点为：<p>
     * http(s)://{domain}/api/payNotify，其中 domain 需要为公网域名。可以在一站式研发平台回调地址栏填入该端点信息用于触发测试支付回调。
     */
    /*@ResponseBody
    @PostMapping(value = "/payNotify")
    public String payNotify(HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException {
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        //异步验签：切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        //普通公钥模式验签，切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        boolean signVerified = AlipaySignature.rsaCheckV1(params, CONFIG_MAG.get(environment).get("alipayPublicKey"), "UTF-8", "RSA2");
        if (signVerified) {
            //验证成功
            String notify_id = new String(request.getParameter("notify_id").getBytes("ISO-8859-1"), "UTF-8");
            System.out.println("收到支付宝异步通知, 消息内容: notify_id：" + notify_id);

            String msg_method = new String(request.getParameter("msg_method").getBytes("ISO-8859-1"), "UTF-8");
            System.out.println("收到支付宝异步通知, 消息内容: test_field_two：" + msg_method);

            String biz_content = new String(request.getParameter("biz_content").getBytes("ISO-8859-1"), "UTF-8");
            System.out.println("收到支付宝异步通知, 消息内容: biz_content：" + biz_content);
            return "success";
        } else {
            return "fail";
        }

    }*/

    /**
     * SPI回调处理函数，示例 Demo 中的端点为：<p>
     * http(s)://{domain}/api/spi，其中 domain 需要为公网域名。可以在一站式研发平台回调地址栏填入该端点信息用于触发测试SPI回调。
     */
    /*@ResponseBody
    @PostMapping(value = "/spi")
    public String spi(@RequestParam Map<String, String> params) throws AlipayApiException {
        System.out.println("收到支付宝 spi 测试接口调用, spi 接口: " + params.get("method"));

        JSONObject response = new JSONObject();
        boolean signVerified = AlipaySignature.rsaCheckV1(params, CONFIG_MAG.get(environment).get("alipayPublicKey"), "UTF-8", "RSA2");
        //验签成功
        if (signVerified) {
            // demo 业务参数
            response.put("demo_callback_field_one", "demo示例 code");
            response.put("demo_callback_field_two", "demo 示例 msg ");

            //spi 固定响应参数
            response.put("code", 10000);
            response.put("msg", "Success");
        } else {
            // 验签失败：构造错误码
            response.put("code", 40004);
            response.put("msg", "Business Failed");
            response.put("sub_code", "ISV-VERIFICATION-FAILED");
            response.put("sub_msg", "验签失败");
            System.out.println("验签失败");
        }
        String contentToSign = response.toJSONString();
        JSONObject spiResult = new JSONObject();
        String sign = AlipaySignature.rsaSign(contentToSign, CONFIG_MAG.get(environment).get("appPrivateKey"), "UTF-8",
                "RSA2");
        spiResult.put("response", response);
        spiResult.put("sign", sign);
        return spiResult.toJSONString();
    }*/
}