package com.pj.mall.controller.admin;

import com.pj.mall.common.PageResult;
import com.pj.mall.pojo.Order;
import com.pj.mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Jack
 * @create 2019-04-19 17:23
 */
@Controller("adminOrderController")
@RequestMapping("/admin/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 分页查询订单
     * @param page
     * @param limit
     * @param status
     * @param orderId
     * @return
     */
    @RequestMapping("/page")
    @ResponseBody
    public PageResult<List<Order>> queryProductByPage(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "limit",defaultValue = "5") Integer limit,
            @RequestParam(value = "status",required = false) Integer status,
            @RequestParam(value = "orderId",required = false) Long orderId){
        PageResult<List<Order>> pageResult = orderService.queryOrderByPage(page,limit,status,null,orderId);
        return pageResult;
    }

    /**
     * 根据订单id查询订单
     * @param orderId
     * @return
     */
    @GetMapping("/{orderId}")
    @ResponseBody
    public PageResult<Order> queryOrderById(@PathVariable("orderId") Long orderId){
        Order order = orderService.queryOrderById(orderId);
        return new PageResult<>(order);
    }

    /**
     * 对指定订单进行发货
     * @param orderId
     * @return
     */
    @PutMapping("/consign/{orderId}")
    @ResponseBody
    public PageResult<Void> consign(@PathVariable("orderId") Long orderId){
        orderService.consign(orderId);
        return new PageResult<>();
    }

    /**
     * 删除订单
     * @param orderIds
     * @return
     */
    @DeleteMapping("/{orderIds}")
    @ResponseBody
    public PageResult<Void> deleteOrder(@PathVariable("orderIds") List<Long> orderIds){
        orderService.deleteOrder(orderIds);
        return new PageResult<>();
    }
}
