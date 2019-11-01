package com.pj.mall.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.pj.mall.config.AlipayProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jack
 * @create 2019-04-18 18:54
 */
@Slf4j
@Component
public class PayHelper {
    @Autowired
    private AlipayClient alipayClient;
    @Autowired
    private AlipayProperties alipayProperties;

    /**
     * 在alipay中创建订单，并且返回支付页面主体。
     * @param orderId
     * @param totalPay  单位为分
     * @param productName
     * @return
     */
    public String createOrder(Long orderId,Long totalPay,String productName){
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(alipayProperties.getReturnUrl());
        alipayRequest.setNotifyUrl(alipayProperties.getNotifyUrl());
        Map<String,String> data = new HashMap<>();
        //商户订单号，商户网站订单系统中唯一订单号，必填
        data.put("out_trade_no",orderId.toString());
        //销售产品码，与支付宝签约的产品码名称。目前仅仅支持FAST_INSTANT_TRADE_PAY
        data.put("product_code",alipayProperties.getProductCode());
        //付款金额，必填,单位为元
        data.put("total_amount",String.valueOf(totalPay/100));//单位从分转化为元
        //订单标题，必填
        data.put("subject",productName);
        //商品描述，可空
        //String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");
        alipayRequest.setBizContent(JsonUtil.serialize(data));
        //请求
        try {
            String body = alipayClient.pageExecute(alipayRequest).getBody();
            //TODO 保存支付宝的流水号
            //TODO 进行一些判断，判断该订单是否已支付，已关闭等等
            return body;
        } catch (AlipayApiException e) {
            log.error("【支付宝下单】创建订单失败,orderId:{}",orderId,e);
            return null;
        }
    }

    /**
     *
     */
    public boolean checkSignature(Map<String,String> params){
        boolean signVerified;
        try {
            signVerified = AlipaySignature.rsaCheckV1(params,
                    alipayProperties.getAlipayPublicKey(), alipayProperties.getCharset(), alipayProperties.getSignType()); //调用SDK验证签名
        } catch (AlipayApiException e) {
            log.error("【支付宝】验签失败",e);
            signVerified = false;
        }
        return signVerified;
    }
}
