/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.alipay.demo.service;

/**
 * @author jishupei.jsp
 * @version : AntMerchantShopService, v0.1 2024年03月06日 3:09 下午 jishupei.jsp Exp $
 */
public interface AntMerchantShopService {
    /**
     * 查询某一个店铺id（通过分页查询）
     */
    String queryShopIdByPage(long pageNum, long pageSize);
}
