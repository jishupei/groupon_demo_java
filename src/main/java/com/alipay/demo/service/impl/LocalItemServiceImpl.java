/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.alipay.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.*;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.alipay.demo.service.AntMerchantShopService;
import com.alipay.demo.service.LocalItemService;
import com.alipay.demo.util.AlipaySdkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jishupei.jsp
 * @version : LocalItemServiceImpl, v0.1 2024年03月04日 2:55 下午 jishupei.jsp Exp $
 */
@Service
public class LocalItemServiceImpl implements LocalItemService {
    @Autowired
    private AlipaySdkUtil alipaySdkUtil;

    @Autowired
    private AntMerchantShopService antMerchantShopService;

    @Override
    public String getCategoryId(String itemType) {
        // 构造请求参数以调用接口
        AlipayOpenAppLocalitemAllcategoryQueryRequest request = new AlipayOpenAppLocalitemAllcategoryQueryRequest();
        AlipayOpenAppLocalitemAllcategoryQueryModel model = new AlipayOpenAppLocalitemAllcategoryQueryModel();

        // 设置商品类型
        model.setItemType(itemType);

        request.setBizModel(model);
        try {
            AlipayOpenAppLocalitemAllcategoryQueryResponse response = alipaySdkUtil.getAlipayClient().execute(request);
            System.out.println(response.getBody());

            if (response.isSuccess()) {
                System.out.println("alipay.open.app.localitem.allcategory.query调用成功");
                if (response.getCats() != null && !response.getCats().isEmpty()) {
                    List<LocalAppxCategoryVO> catAndParent = response.getCats().get(0).getCatAndParent();
                    if (catAndParent != null && !catAndParent.isEmpty()) {
                        return catAndParent.get(0).getCatId();
                    }
                }
            } else {
                System.out.println("alipay.open.app.localitem.allcategory.query调用失败");
                System.out.println(JSON.toJSONString(response));
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<AttributeVO> queryTemplateAttrList(String categoryId) {

        // 构造请求参数以调用接口
        AlipayOpenAppLocalitemTemplateQueryRequest request = new AlipayOpenAppLocalitemTemplateQueryRequest();
        AlipayOpenAppLocalitemTemplateQueryModel model = new AlipayOpenAppLocalitemTemplateQueryModel();

        // 设置类目id
        model.setCategoryId(categoryId);

        // 设置商品类型
        model.setItemType("1");

        request.setBizModel(model);
        try {
            AlipayOpenAppLocalitemTemplateQueryResponse response = alipaySdkUtil.getAlipayClient().execute(request);
            System.out.println(response.getBody());

            if (response.isSuccess()) {
                System.out.println("alipay.open.app.localitem.template.query调用成功");
                return response.getAttr().getItemAttrList();
            } else {
                System.out.println("alipay.open.app.localitem.template.query调用失败: " + JSON.toJSONString(response));
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String createLocalItem(String categoryId, String productName, String outItemId) {
        // 构造请求参数以调用接口
        AlipayOpenAppLocalitemCreateRequest request = new AlipayOpenAppLocalitemCreateRequest();
        AlipayOpenAppLocalitemCreateModel model = new AlipayOpenAppLocalitemCreateModel();

        // 设置类目ID
        model.setCategoryId(categoryId);

        // 设置商品模版类型
        model.setItemType("1");

        // 设置商家侧商品ID
        model.setOutItemId(outItemId);

        // 设置C端详情页模式
        model.setItemDetailsPageModel("1");

        // 设置售卖时间
        TimeRangeStructVO soldTime = new TimeRangeStructVO();
        soldTime.setEndTime("2024-06-01 10:00:00");
        soldTime.setStartTime("2024-01-01 10:00:00");
        model.setSoldTime(soldTime);

        // 设置商家名称
        model.setMerchantName("merchant_name");

        // 设置商品名称
        model.setTitle(productName);

        // 设置客服电话
        PhoneStructVO customerServiceMobile = new PhoneStructVO();
        customerServiceMobile.setPhoneNumber("18888888888");
        customerServiceMobile.setPhoneType("1");
        model.setCustomerServiceMobile(customerServiceMobile);

        // 设置sku列表
        List<LocalItemSkuCreateVO> skus = new java.util.ArrayList<>();
        LocalItemSkuCreateVO skus0 = new LocalItemSkuCreateVO();
        skus0.setOriginalPrice(100L);
        skus0.setSalePrice(100L);
        skus0.setSaleStatus("AVAILABLE");
        skus0.setStockNum(9999L);
        skus.add(skus0);
        model.setSkus(skus);

        // 设置属性列表
        model.setAttrs(getItemAttrs(categoryId));

        // 设置商品主图
        model.setHeadImg("A*Z-V2To1nD9wAAAAAAAAAAAAAAVR1AQ");

        // 设置商品图片的文件id列表
        List<String> imageList = new java.util.ArrayList<>();
        imageList.add("A*Z-V2To1nD9wAAAAAAAAAAAAAAVR1AQ");
        model.setImageList(imageList);

        request.setBizModel(model);
        try {
            AlipayOpenAppLocalitemCreateResponse response = alipaySdkUtil.getAlipayClient().execute(request);
            System.out.println(response.getBody());

            if (response.isSuccess()) {
                System.out.println("alipay.open.app.localitem.create调用成功");
            } else {
                System.out.println("alipay.open.app.localitem.create调用失败");
            }
            return JSON.toJSONString(response);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return "alipay.open.app.localitem.create调用失败";
    }

    @Override
    public String deleteLocalItem(String itemId, String outItemId) {
        // 构造请求参数以调用接口
        AlipayOpenAppItemDeleteRequest request = new AlipayOpenAppItemDeleteRequest();
        AlipayOpenAppItemDeleteModel model = new AlipayOpenAppItemDeleteModel();

        if (outItemId != null && !"".equals(outItemId)) {
            // 设置商家侧商品ID列表
            List<String> outItemIdList = new java.util.ArrayList<>();
            outItemIdList.add(outItemId);
            model.setOutItemIdList(outItemIdList);
        }
        if (itemId != null && !"".equals(itemId)) {
            // 设置支付宝平台侧商品ID
            List<String> itemIdList = new java.util.ArrayList<>();
            itemIdList.add(itemId);
            model.setItemIdList(itemIdList);
        }

        request.setBizModel(model);
        try {
            AlipayOpenAppItemDeleteResponse response = alipaySdkUtil.getAlipayClient().execute(request);
            System.out.println(response.getBody());

            if (response.isSuccess()) {
                System.out.println("调用成功");
            } else {
                System.out.println("alipay.open.app.item.delete调用失败");
            }
            return JSON.toJSONString(response);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return "alipay.open.app.item.delete调用失败";
    }

    @Override
    public String queryItemDetail(String itemId, String outItemId) {
        // 构造请求参数以调用接口
        AlipayOpenAppLocalitemQueryRequest request = new AlipayOpenAppLocalitemQueryRequest();
        AlipayOpenAppLocalitemQueryModel model = new AlipayOpenAppLocalitemQueryModel();

        if (outItemId != null && !"".equals(outItemId)) {
            // 设置支付宝侧商品id
            model.setItemId(itemId);
        }

        if (outItemId != null && !"".equals(outItemId)) {
            // 设置商家侧商品id
            model.setOutItemId(outItemId);
        }

        // 设置是否查询编辑版本
        model.setNeedEditSpu("1");

        request.setBizModel(model);
        try {
            AlipayOpenAppLocalitemQueryResponse response = alipaySdkUtil.getAlipayClient().execute(request);
            System.out.println(response.getBody());

            if (response.isSuccess()) {
                System.out.println("调用成功");
            } else {
                System.out.println("alipay.open.app.localitem.query调用失败");
            }
            return JSON.toJSONString(response);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return "alipay.open.app.localitem.query调用失败";
    }

    private List<AppItemAttrVO> getItemAttrs(String categoryId) {
        List<AppItemAttrVO> attrs = new java.util.ArrayList<>();
        List<AttributeVO> attributes = queryTemplateAttrList(categoryId);
        if (attributes == null || attributes.isEmpty()) {
            return attrs;
        }
        for (AttributeVO attr : attributes) {
            if (!attr.getIsRequired()) {
                continue;
            }
            AppItemAttrVO appItemAttrVO = new AppItemAttrVO();
            appItemAttrVO.setAttrKey(attr.getKey());
            switch (attr.getKey()) {
                case "original_price_source":
                    appItemAttrVO.setAttrValue("1");
                    break;
                case "commodity":
                    appItemAttrVO.setAttrValue("[{\"group_name\":\"商品组1\",\"total_count\":2,\"option_count\":1,\"item_list\":[{\"name\":\"单品1.1\",\"count\":1,\"price\":5155,\"unit\":\"元\"},{\"name\":\"单品1.2\",\"count\":1,\"price\":30000,\"unit\":\"元\"}]},{\"group_name\":\"商品组2\",\"total_count\":1,\"option_count\":1,\"item_list\":[{\"name\":\"单品2.1\",\"count\":1,\"price\":12050,\"unit\":\"元\"}]}]");
                    break;
                case "settle_type":
                    appItemAttrVO.setAttrValue("{\"settle_type\":\"1\",\"settle_pid_info\":\"2088641404859410\"}");
                    break;
                case "use_shop":
                    appItemAttrVO.setAttrValue(antMerchantShopService.queryShopIdByPage(1, 10));
                    break;
                case "sold_time":
                    appItemAttrVO.setAttrValue("{\"start_time\": \"2024-02-25 18:00:00\",\"end_time\": \"2024-04-20 18:00:00\"}");
                    break;
                case "limit_stock_rule":
                    appItemAttrVO.setAttrValue("{\"limit\":\"1\",\"num\":\"200\"}");
                    break;
                case "limit_buy_rule":
                    appItemAttrVO.setAttrValue("{\"limit\":\"1\",\"num\":\"200\"}");
                    break;
                case "coupon_usage_rule":
                    appItemAttrVO.setAttrValue("1");
                    break;
                case "contact_info_type":
                    appItemAttrVO.setAttrValue("1");
                    break;
                case "usage_type":
                    appItemAttrVO.setAttrValue("{\"usage_list\":[{\"use_type\":\"1\"}]}");
                    break;
                case "code_source_type":
                    appItemAttrVO.setAttrValue("2");
                    break;
                case "verify_type":
                    appItemAttrVO.setAttrValue("[\"1\",\"2\"]");
                    break;
                case "use_date":
                    appItemAttrVO.setAttrValue("{\"use_date_type\":\"1\",\"use_start_date\":\"2024-03-05\",\"use_end_date\":\"2024-09-06\"}");
                    break;
                case "use_limit":
                    appItemAttrVO.setAttrValue("{\"use_time_type\":\"2\",\"use_date_list\":[{\"days_of_week\":[\"1\",\"2\"],\"start_time\":\"09:00:00\",\"end_time\":\"20:30:00\",\"end_time_type\":\"SAME_DAY\"}],\"can_no_use_date_list\":[{\"holidays\":[{\"start_time\":\"09:00:00\",\"end_time\":\"21:30:00\",\"end_time_type\":\"SAME_DAY\"}],\"date_list\":[{\"start_date\":\"2024-03-22\",\"end_date\":\"2024-06-25\",\"start_time\":\"09:00:00\",\"end_time\":\"23:00:00\",\"end_time_type\":\"SAME_DAY\"}]}]}");
                    break;
                case "preorder_rule":
                    appItemAttrVO.setAttrValue("{\"need_appointment\":true,\"appointment_instruction\":\"预约说明：请至少提前一天预约。\"}");
                    break;
                case "refund_rule":
                    appItemAttrVO.setAttrValue("{\"refund_policy\": [\"1\", \"2\"]}");
                    break;
                case "merchant_refund_confirm":
                    appItemAttrVO.setAttrValue("1");
                    break;
                case "item_details_page_model":
                    appItemAttrVO.setAttrValue("1");
                    break;
            }
            attrs.add(appItemAttrVO);
        }
        return attrs;
    }
}
