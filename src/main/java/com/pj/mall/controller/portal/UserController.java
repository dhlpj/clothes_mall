package com.pj.mall.controller.portal;

import com.baomidou.kaptcha.Kaptcha;
import com.pj.mall.common.PageResult;
import com.pj.mall.pojo.Comment;
import com.pj.mall.pojo.Order;
import com.pj.mall.pojo.Shipping;
import com.pj.mall.pojo.User;
import com.pj.mall.service.CommentService;
import com.pj.mall.service.OrderService;
import com.pj.mall.service.ShippingService;
import com.pj.mall.service.UserService;
import com.pj.mall.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author Jack
 * @create 2019-04-02 10:28
 */
@Controller
@RequestMapping("/portal/user")
public class UserController {
    @Autowired
    private Kaptcha kaptcha;
    @Autowired
    private UserService userService;
    @Autowired
    private ShippingService shippingService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CommentService commentService;

    /**
     * 校验数据
     * @param data 需要检验的数据
     * @param type 1-用户名    2-手机号
     * @return 不可用就抛出对应的异常
     */
    @GetMapping("/check/{data}/{type}")
    @ResponseBody
    public PageResult<Void> checkData(
            @PathVariable("data") String data, @PathVariable("type") Integer type){
        userService.checkData(data, type);
        return new PageResult<>();
    }

    /**
     * 用户注册
     * @param user
     * @param verifyCode
     * @return
     */
    @PostMapping("/register")
    @ResponseBody
    public PageResult<Void> register(@Valid User user,
                                     @RequestParam("verifyCode") String verifyCode){//可以在user后面加上BindResult来获取校验结果，自定义返回异常
        //校验验证码
        kaptcha.validate(verifyCode);
        userService.checkData(user.getUsername(),1);
        userService.checkData(user.getPhone(),2);
        userService.register(user);
        return new PageResult<>();
    }

    /**
     * 用户名密码验证
     * @param username
     * @param password
     * @param verifyCode
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public PageResult<Void> login(@RequestParam("username") String username,
                                  @RequestParam("password") String password,
                                  @RequestParam("verifyCode") String verifyCode,
                                  HttpSession session){
        //校验验证码
        kaptcha.validate(verifyCode);
        //校验用户名及密码
        User user = userService.queryUserByUsernameAndPassword(username, password);
        session.setAttribute("userId",user.getId());
        session.setAttribute("username",user.getUsername());
        return new PageResult<>();
    }

    @DeleteMapping("/logout")
    @ResponseBody
    public PageResult<Void> logout(HttpSession session){
        //remove session username and userId
        session.removeAttribute("username");
        session.removeAttribute("userId");
        return new PageResult<>();
    }

    /**
     * 添加收货地址
     * @param shipping
     * @param session
     * @return
     */
    @PostMapping("/shipping")
    @ResponseBody
    public PageResult<Void> addShipping(Shipping shipping,HttpSession session){
        Long userId = UserUtil.getUserId(session);
        shipping.setUserId(userId);
        shippingService.addShipping(shipping);
        return new PageResult<>();
    }

    /**
     * 更新收货地址
     * @param id
     * @param shipping
     * @return
     */
    @PutMapping("/shipping/{id}")
    @ResponseBody
    public PageResult<Void> updateShipping(@PathVariable("id")Long id, Shipping shipping){
        shippingService.updateShipping(id,shipping);
        return new PageResult<>();
    }

    /**
     * 删除收货地址
     * @param id
     * @return
     */
    @DeleteMapping("/shipping/{id}")
    @ResponseBody
    public PageResult<Void> deleteShipping(@PathVariable("id")Long id){
        shippingService.deleteShipping(id);
        return new PageResult<>();
    }


    /**
     * 到达个人信息的首页
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/toHomePage")
    public String toHomePage(HttpSession session, Model model){
        //查找待支付订单，待收货订单的，待评价订单的数量
        Long userId = UserUtil.getUserId(session);
        Map<String,Integer> attributes = orderService.queryOrderCountByUserId(userId);
        model.addAllAttributes(attributes);
        return "/portal/home";
    }

    /**
     * 到达订单页面
     * @param page
     * @param limit
     * @param status
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/toOrderPage")
    public String toOrderPage(@RequestParam(value = "page",defaultValue = "1") Integer page,
                              @RequestParam(value = "limit",defaultValue = "5") Integer limit,
                              @RequestParam(value = "status",required = false) Integer status,
                              HttpSession session,
                              Model model){
        Long userId = UserUtil.getUserId(session);
        PageResult<List<Order>> pageResult= orderService.queryOrderByPage(page,limit,status,userId,null);
        //填充orderDetail
        orderService.loadOrderDetail(pageResult.getData());
        //标记查找的订单类型
        model.addAttribute("status",status);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPage",pageResult.getCount()%limit==0 ? pageResult.getCount()/limit:pageResult.getCount()/limit+1);
        model.addAttribute("pageResult",pageResult);
        return "/portal/myOrder";
    }

    /**
     * 到达订单详情页面
     * @param orderId
     * @param model
     * @return
     */
    @GetMapping("/toOrderDetailPage")
    public String toOrderDetailPage(@RequestParam("orderId") Long orderId,Model model){
        Order order = orderService.queryOrderById(orderId);
        model.addAttribute("order",order);
        return "/portal/orderDetail";
    }

    /**
     * 到达收货地址页面
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/toShippingPage")
    public String toShippingPage(HttpSession session,Model model){
        Long userId = UserUtil.getUserId(session);
        List<Shipping> shippings = shippingService.queryShippingByUserId(userId);
        model.addAttribute("shippings",shippings);
        return "/portal/myShipping";
    }

    /**
     * 到修改密码页面
     * @return
     */
    @GetMapping("/toChangePwdPage")
    public String toChangePwdPage(){
        return "/portal/changePwd";
    }

    /**
     * 修改密码操作
     * @param oldPassword
     * @param newPassword
     * @param session
     * @return
     */
    @PutMapping("/changePwd")
    @ResponseBody
    public PageResult<Void> changePwd(@RequestParam("oldPassword") String oldPassword,
                                    @RequestParam("newPassword") String newPassword,
                                    HttpSession session){
        Long userId = UserUtil.getUserId(session);
        userService.changePwd(userId,oldPassword,newPassword);
        //移除用户名及id
        session.removeAttribute("username");
        session.removeAttribute("userId");
        return new PageResult<>();
    }

    /**
     * 添加评论
     * @param comments
     * @return
     */
    @PostMapping("/comment/{orderId}")
    @ResponseBody
    public PageResult<Void> addComment(@PathVariable("orderId") Long orderId,
                                       @RequestBody List<Comment> comments,
                                       HttpSession session){
        Long userId = UserUtil.getUserId(session);
        commentService.addComment(orderId,userId,comments);
        return new PageResult<>();
    }

    @GetMapping("/comment/{orderId}")
    @ResponseBody
    public PageResult<List<Comment>> queryCommentByOrderId(@PathVariable("orderId") Long orderId){
        List<Comment> comments = commentService.queryCommentByOrderId(orderId);
        return new PageResult<>(comments);
    }
}
