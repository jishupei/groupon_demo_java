/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.alipay.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AntMerchantExpandShopPageQueryModel;
import com.alipay.api.request.AntMerchantExpandShopPageQueryRequest;
import com.alipay.api.response.AntMerchantExpandShopPageQueryResponse;
import com.alipay.demo.service.AntMerchantShopService;
import com.alipay.demo.util.AlipaySdkUtil;
import com.alipay.demo.vo.UserShopVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jishupei.jsp
 * @version : AntMerchantShopServiceImpl, v0.1 2024年03月06日 3:10 下午 jishupei.jsp Exp $
 */
@Service
public class AntMerchantShopServiceImpl implements AntMerchantShopService {
    @Autowired
    private AlipaySdkUtil alipaySdkUtil;

    @Override
    public String queryShopIdByPage(long pageNum, long pageSize) {
        AntMerchantExpandShopPageQueryRequest request = new AntMerchantExpandShopPageQueryRequest();
        AntMerchantExpandShopPageQueryModel model = new AntMerchantExpandShopPageQueryModel();
        model.setPageSize(pageSize);
        model.setPageNum(pageNum);
        model.setIpRoleId("2088641404859410");
        request.setBizModel(model);
        try {
            AntMerchantExpandShopPageQueryResponse response = alipaySdkUtil.getAlipayClient().execute(request);
            System.out.println(response.getBody());
            if (response.isSuccess()) {
                System.out.println("ant.merchant.expand.shop.page.query调用成功");
                if (response.getShopInfos() != null) {
                    List<UserShopVo> collect = response.getShopInfos().stream()
                            .filter(info -> "01".equals(info.getShopStatus()))
                            .map(info -> new UserShopVo(info.getShopId())).collect(Collectors.toList());
                    return JSONArray.toJSONString(collect);
                }
            } else {
                System.out.println("ant.merchant.expand.shop.page.query调用失败");
                System.out.println(JSON.toJSONString(response));
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }
}
