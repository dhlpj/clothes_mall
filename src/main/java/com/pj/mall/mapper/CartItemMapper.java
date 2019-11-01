package com.pj.mall.mapper;

import com.pj.mall.pojo.CartItem;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Jack
 * @create 2019-04-15 19:18
 */
public interface CartItemMapper extends Mapper<CartItem>,IdListMapper<CartItem,Long> {
}
