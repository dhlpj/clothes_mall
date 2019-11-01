package com.pj.mall.service;

import com.pj.mall.pojo.CartItem;

import java.util.List;

/**
 * @author Jack
 * @create 2019-04-14 15:21
 */
public interface CartService {

    /**
     * 添加cart
     * @param cartItem
     */
    void addCartItem(CartItem cartItem);

    /**
     * 根据用户id获取购物车,并且判断购物车中的商品是否无效
     * @return
     */
    List<CartItem> getCartByUserId(Long userId);


    /**
     * 将指定商品id的valid修改为false
     * @param productId
     */
    void updateCartItemValid(Long productId);

    /**
     * 修改购物车中商品数量
     * @param itemId
     * @param num
     */
    void updateCartItemNum(Long itemId,Integer num);

    /**
     * 删除购物车中的商品
     * @param itemIds
     */
    void deleteCartItem(List<Long> itemIds);

    /**
     * 根据id集合查找cartItem
     * @param itemIds
     * @return
     */
    List<CartItem> queryCartByIds(List<Long> itemIds);
}
