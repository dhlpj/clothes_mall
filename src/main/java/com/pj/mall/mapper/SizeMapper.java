package com.pj.mall.mapper;

import com.pj.mall.pojo.Size;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;

/**
 * @author Jack
 * @create 2019-03-26 10:14
 */
public interface SizeMapper extends Mapper<Size>,IdListMapper<Size,Long>,InsertListMapper<Size> {
    @Update({"UPDATE tb_size SET stock_count = stock_count - #{num},last_update_time=#{last_update_time} WHERE id = #{sizeId} and stock_count>=#{num}"})
    int decreaseStock(@Param("sizeId") Long sizeId, @Param("num") Integer num, @Param("last_update_time")Date date);
}
