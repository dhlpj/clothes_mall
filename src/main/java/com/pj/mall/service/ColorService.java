package com.pj.mall.service;

import com.pj.mall.pojo.Color;
import com.pj.mall.pojo.Product;

import java.util.List; /**
 * @author Jack
 * @create 2019-03-26 10:40
 */
public interface ColorService {
    /**
     * 根据product添加商品集合
     * @param product
     */
    void addColors(Product product);

    /**
     * 根据商品id查询颜色信息
     * @param id
     * @return
     */
    List<Color> queryColorByProductId(Long id);

    /**
     * 根据商品id删除颜色信息
     * @param productId
     */
    void deleteColorByProductId(Long productId);

    /**
     * 根据colorId查询商品
     * @param colorId
     * @return
     */
    Color queryColorById(Long colorId);

    /**
     * 根据colorId查询商品，但不会抛出异常
     * @param colorId
     * @return
     */
    Color queryOnlyColorById(Long colorId);
}
