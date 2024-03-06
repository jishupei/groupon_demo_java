/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.alipay.demo.service;

import com.alipay.api.domain.AttributeVO;

import java.util.List;

/**
 * @author jishupei.jsp
 * @version : LocalItemService, v0.1 2024年03月04日 2:55 下午 jishupei.jsp Exp $
 */
public interface LocalItemService {

    /**
     * 获取商品类目ID（第一个)
     */
    String getCategoryId(String itemType);

    /**
     * 获取本地商品模板信息
     */
    List<AttributeVO> queryTemplateAttrList(String categoryId);

    /**
     * 创建商品
     */
    String createLocalItem(String categoryId, String productName);
}
