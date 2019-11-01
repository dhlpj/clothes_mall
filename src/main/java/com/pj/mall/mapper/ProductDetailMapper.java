package com.pj.mall.mapper;

import com.pj.mall.pojo.ProductDetail;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Jack
 * @create 2019-03-26 10:12
 */
public interface ProductDetailMapper extends Mapper<ProductDetail>,IdListMapper<ProductDetail,Long>{
}
