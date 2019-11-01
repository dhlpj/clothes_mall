package com.pj.mall.vo;

import com.pj.mall.common.PageResult;
import com.pj.mall.pojo.Comment;
import lombok.Data;

import java.util.List;

/**
 * @author Jack
 * @create 2019-04-23 20:13
 */
@Data
public class CommentResult extends PageResult<List<Comment>> {
    private Integer totalPage;

    public CommentResult(){
        super();
    }

    /**
     * 有返回数据时,且为集合
     * @param total
     * @param data
     */
    public CommentResult(Long total, List<Comment> data, Integer totalPage){
        super(total,data);
        this.totalPage = totalPage;
    }
}
