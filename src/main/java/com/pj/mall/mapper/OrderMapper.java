package com.pj.mall.mapper;

import com.pj.mall.pojo.Order;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Jack
 * @create 2019-04-17 21:12
 */
public interface OrderMapper extends Mapper<Order>,IdListMapper<Order,Long> {
}
