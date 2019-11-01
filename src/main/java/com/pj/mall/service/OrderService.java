package com.pj.mall.service;

import com.pj.mall.common.PageResult;
import com.pj.mall.pojo.Order;
import com.pj.mall.pojo.OrderDetail;
import com.pj.mall.vo.OrderVO;

import java.util.List;
import java.util.Map;

/**
 * @author Jack
 * @create 2019-04-17 21:00
 */
public interface OrderService {

    /**
     * 根据购物车id和用户id到订单确认页面
     * @param itemIds
     * @param userId
     * @return
     */
    Map<String,Object> getOrderInfo(List<Long> itemIds, Long userId);

    /**
     * 创建订单
     * @param orderVO
     * @param userId
     * @return
     */
    Long createOrder(OrderVO orderVO, Long userId);

    /**
     * 根据订单id查询订单
     * @param orderId
     * @return
     */
    Order queryOrderById(Long orderId);

    /**
     * 更新订单
     * @param order
     */
    void updateOrder(Order order);

    /**
     * 后台查询订单列表
     * @param page
     * @param limit
     * @param status
     * @param orderId
     * @return
     */
    PageResult<List<Order>> queryOrderByPage(Integer page, Integer limit,Integer status,Long userId,Long orderId);

    /**
     * 后台发货
     * @param orderId
     */
    void consign(Long orderId);

    /**
     * 删除订单
     * @param orderIds
     */
    void deleteOrder(List<Long> orderIds);

    /**
     * 根据用户的id查找用户的订单数量
     * @param userId
     * @return
     */
    Map<String,Integer> queryOrderCountByUserId(Long userId);

    /**
     * 加载订单中的订单详情
     * @param data
     */
    void loadOrderDetail(List<Order> data);

    /**
     * 确认收货
     * @param orderId
     */
    void confirmOrder(Long orderId);

    /**
     *
     * @param orderId
     */
    void commentOrder(Long orderId);

    /**
     * 根据订单详情id查询订单
     * @param orderDetailId
     * @return
     */
    OrderDetail queryOrderDetailById(Long orderDetailId);
}
