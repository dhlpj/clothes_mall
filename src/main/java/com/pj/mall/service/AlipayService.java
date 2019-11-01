package com.pj.mall.service;

import java.util.Map;

/**
 * @author Jack
 * @create 2019-04-19 15:01
 */
public interface AlipayService {
    /**
     * 根据orderId跳转到支付宝页面
     * @param orderId
     * @return 支付宝返回的页面
     */
    String toAlipayPage(Long orderId);


    /**
     * 处理支付宝同步返回结果通知
     * @param params
     */
    void handleReturn(Map<String, String> params);

    /**
     * 处理支付宝异步通知
     * 返回success或者fail
     * @param params
     */
    String handleNotification(Map<String, String> params);
}
