package com.pj.mall.mapper;

        import com.pj.mall.pojo.Product;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Jack
 * @create 2019-03-14 11:33
 */
public interface ProductMapper extends Mapper<Product>,IdListMapper<Product,Long>{

}
