package com.pj.mall.service.impl;

import com.pj.mall.enums.OrderStatusEnum;
import com.pj.mall.exception.ExceptionEnum;
import com.pj.mall.exception.MallException;
import com.pj.mall.pojo.Order;
import com.pj.mall.pojo.OrderDetail;
import com.pj.mall.service.AlipayService;
import com.pj.mall.service.OrderService;
import com.pj.mall.util.PayHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * @author Jack
 * @create 2019-04-19 15:01
 */
@Slf4j
@Service
public class AlipayServiceImpl implements AlipayService {
    @Autowired
    private OrderService orderService;
    @Autowired
    private PayHelper payHelper;

    @Override
    public String toAlipayPage(Long orderId) {
        //查询订单详情
        Order order = orderService.queryOrderById(orderId);
        Integer status = order.getStatus();
        if (status.intValue()!=OrderStatusEnum.UN_PAY.value()) {
            throw new MallException(ExceptionEnum.ORDER_STATUS_ERROR);
        }
        //取第一个订单项作为标题
        OrderDetail orderDetail = order.getOrderDetails().get(0);
        String productName = orderDetail.getName();
        String content = payHelper.createOrder(orderId, order.getTotalPay(), productName);
        if(StringUtils.isBlank(content)){
            throw new MallException(ExceptionEnum.JUMP_TO_ALIPAY_ERROR);
        }
        return content;
    }


    //TODO 是否需要移除该方法
    @Transactional
    @Override
    public void handleReturn(Map<String, String> params) {
        //验签
        boolean signVerified = payHelper.checkSignature(params);
        if(signVerified){
            //商户订单号
            Long orderId = Long.valueOf(params.get("out_trade_no"));
            //支付宝交易号
            String trade_no = params.get("trade_no");
            //交易状态
            String trade_status = params.get("trade_status");
            //付款金额
            Double total_amount = Double.parseDouble(params.get("total_amount"))*100;//单位为分
            //查询订单
            Order order = orderService.queryOrderById(orderId);
            //订单还是未付款状态才会修改订单
            if (order.getStatus().intValue()==OrderStatusEnum.UN_PAY.value()) {
                //校验订单金额,防止被中间人攻击
                if(total_amount.longValue()!=order.getTotalPay().longValue()){
                    throw new MallException(ExceptionEnum.INVALID_ORDER_PARAM);
                }
                if (trade_status.equals("TRADE_SUCCESS")){
                    //更新订单状态
                    order.setStatus(OrderStatusEnum.PAYED.value());
                    orderService.updateOrder(order);
                    //TODO 插入到订单信息表
                    log.info("【支付包同步调用】修改订单状态，订单ID:{}",orderId);
                }
            }
        }else{
            throw new MallException(ExceptionEnum.ALIPAY_RETURN_ERROR);
        }
    }

    @Transactional
    @Override
    public String handleNotification(Map<String, String> params) {
        boolean signVerified = payHelper.checkSignature(params);
        if(signVerified){
            //商户订单号
            Long orderId = Long.valueOf(params.get("out_trade_no"));
            //支付宝交易号
            String trade_no = params.get("trade_no");
            //交易状态
            String trade_status = params.get("trade_status");
            //付款金额
            Double total_amount = Double.parseDouble(params.get("total_amount"))*100;//单位为分
            //查询订单
            Order order = orderService.queryOrderById(orderId);
            //订单还是未付款状态才会修改订单
            if (order.getStatus().intValue()== OrderStatusEnum.UN_PAY.value()) {
                //校验订单金额,防止被中间人攻击
                if (total_amount.longValue()==order.getTotalPay().longValue()&&trade_status.equals("TRADE_SUCCESS")){
                    //更新订单状态
                    order.setStatus(OrderStatusEnum.PAYED.value());
                    //设置支付时间
                    order.setPaymentTime(new Date());
                    orderService.updateOrder(order);
                    //TODO 插入到订单信息表
                    log.info("【支付包异步调用】修改订单状态，订单ID:{}",orderId);
                }
            }
            return "success";
        }else{
            return "fail";
        }
    }
}
