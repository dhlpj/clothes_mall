package com.pj.mall.controller.portal;

import com.pj.mall.common.PageResult;
import com.pj.mall.pojo.CartItem;
import com.pj.mall.service.CartService;
import com.pj.mall.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Jack
 * @create 2019-04-13 10:09
 */
@Controller
@RequestMapping("/portal/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/toCartPage")
    public String toCartPage(Model model,HttpSession session){
        Long userId = UserUtil.getUserId(session);
        List<CartItem> cartItems = cartService.getCartByUserId(userId);
        model.addAttribute("cartItems",cartItems);
        return "/portal/cart";
    }

    /**
     * 添加成功，前台自动跳转到cart.html页面
     * @param cartItem
     * @return
     */
    @ResponseBody
    @PostMapping("/addProduct")
    public PageResult<Void> addCartItem(CartItem cartItem, HttpSession session){
        Long userId = UserUtil.getUserId(session);
        cartItem.setUserId(userId);
        cartService.addCartItem(cartItem);
        return new PageResult<>();
    }

    @ResponseBody
    @PutMapping("/updateCartItemNum")
    public PageResult<Void> updateCartItemNum(Long itemId,Integer num){
        cartService.updateCartItemNum(itemId,num);
        return new PageResult<>();
    }

    @ResponseBody
    @DeleteMapping("/deleteCartItem")
    public PageResult<Void> deleteCartItem(@RequestParam("itemIds") List<Long> itemIds){
        cartService.deleteCartItem(itemIds);
        return new PageResult<>();
    }
}
