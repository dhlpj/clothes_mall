package com.pj.mall.mapper;

import com.pj.mall.pojo.OrderDetail;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Jack
 * @create 2019-04-17 21:13
 */
public interface OrderDetailMapper extends Mapper<OrderDetail>,InsertListMapper<OrderDetail>,IdListMapper<OrderDetail,Long> {
}
