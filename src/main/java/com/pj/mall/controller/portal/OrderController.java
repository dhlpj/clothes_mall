package com.pj.mall.controller.portal;

import com.pj.mall.common.PageResult;
import com.pj.mall.service.CartService;
import com.pj.mall.service.OrderService;
import com.pj.mall.service.ShippingService;
import com.pj.mall.util.UserUtil;
import com.pj.mall.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Jack
 * @create 2019-04-16 10:46
 */
@Controller
@RequestMapping("/portal/order")
public class OrderController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ShippingService shippingService;
    @Autowired
    private OrderService orderService;

    /**
     * 跳转到结算页面
     * @param itemIds 购物车中的id
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/getOrderInfo")
    public String getOrderInfo(@RequestParam("itemIds") List<Long>itemIds, Model model, HttpSession session){
        Long userId = UserUtil.getUserId(session);
        Map<String,Object> attributes = orderService.getOrderInfo(itemIds,userId);
        model.addAllAttributes(attributes);
        return "/portal/getOrderInfo";
    }

    /**
     * 生成订单
     * @param orderVO
     * @param session
     * @return
     */
    @PostMapping("/createOrder")
    @ResponseBody
    public PageResult<String> createOrder(@RequestBody OrderVO orderVO,HttpSession session){
        Long userId = UserUtil.getUserId(session);
        Long orderId = orderService.createOrder(orderVO,userId);
        return new PageResult(orderId.toString());
    }

    /**
     * 删除指定订单
     * @param orderId
     * @return
     */
    @DeleteMapping("/{orderId}")
    @ResponseBody
    public PageResult<Void> deleteOrder(@PathVariable("orderId") Long orderId){
        orderService.deleteOrder(Arrays.asList(orderId));
        return new PageResult<>();
    }

    /**
     * 确认收货
     * @param orderId
     * @return
     */
    @PutMapping("/confirmOrder/{orderId}")
    @ResponseBody
    public PageResult<Void> confirmOrder(@PathVariable("orderId") Long orderId){
        orderService.confirmOrder(orderId);
        return new PageResult<>();
    }
}
