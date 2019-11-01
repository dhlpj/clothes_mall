package com.pj.mall.mapper;

import com.pj.mall.pojo.Color;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Jack
 * @create 2019-03-26 10:13
 */
public interface ColorMapper extends Mapper<Color>,IdListMapper<Color,Long> {
}
