package com.pj.mall.service;

import com.pj.mall.pojo.Comment;
import com.pj.mall.vo.CommentResult;

import java.util.List;

/**
 * @author Jack
 * @create 2019-04-23 16:06
 */
public interface CommentService {
    /**
     * 添加评论
     * @param orderId
     * @param userId
     * @param comments
     */
    void addComment(Long orderId,Long userId,List<Comment> comments);

    /**
     * 根据订单id查询评价
     * @param orderId
     * @return
     */
    List<Comment> queryCommentByOrderId(Long orderId);

    /**
     * 根据商品id查询评论
     * @param productId
     * @param page
     * @param limit
     * @return
     */
    CommentResult queryCommentByProductId(Long productId, Integer page, Integer limit);

    /**
     * 根据ids删除评论
     * @param ids
     */
    void deleteComments(List<Long> ids);

    /**
     * 根据商品id删除评论
     * @param productId
     */
    void deleteCommentByProductId(Long productId);
}
