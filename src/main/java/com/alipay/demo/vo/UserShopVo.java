/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.alipay.demo.vo;

import java.io.Serializable;

/**
 * @author jishupei.jsp
 * @version : UserShopVo, v0.1 2024年03月06日 4:45 下午 jishupei.jsp Exp $
 */
public class UserShopVo implements Serializable {

    private static final long serialVersionUID = 714077787134000912L;

    private String shopId;

    public UserShopVo(String shopId) {
        this.shopId = shopId;
    }

    /**
     * Getter method for property <tt>shopId</tt>.
     *
     * @return property value of shopId
     */
    public String getShopId() {
        return shopId;
    }

    /**
     * Setter method for property <tt>shopId</tt>.
     *
     * @param shopId value to be assigned to property shopId
     */
    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
