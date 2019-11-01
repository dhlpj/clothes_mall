package com.pj.mall.mapper;

import com.pj.mall.pojo.Comment;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Jack
 * @create 2019-04-23 16:05
 */
public interface CommentMapper extends Mapper<Comment>,InsertListMapper<Comment>,IdListMapper<Comment,Long> {
}
