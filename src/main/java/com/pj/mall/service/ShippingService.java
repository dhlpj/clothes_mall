package com.pj.mall.service;

import com.pj.mall.pojo.Shipping;

import java.util.List;

/**
 * @author Jack
 * @create 2019-04-17 8:30
 */
public interface ShippingService {
    /**
     * 根据用户id查询其收货地址
     * @param userId
     * @return
     */
    List<Shipping> queryShippingByUserId(Long userId);

    /**
     * 添加收货地址
     * @param shipping
     */
    void addShipping(Shipping shipping);

    /**
     * 更新收货地址
     * @param id
     * @param shipping
     */
    void updateShipping(Long id, Shipping shipping);

    /**
     * 删除收货地址
     * @param id
     */
    void deleteShipping(Long id);

    /**
     * 根据id查找收货地址
     * @param shippingId
     * @return
     */
    Shipping queryShippingById(Long shippingId);
}
