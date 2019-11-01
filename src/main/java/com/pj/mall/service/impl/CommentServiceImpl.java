package com.pj.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pj.mall.exception.ExceptionEnum;
import com.pj.mall.exception.MallException;
import com.pj.mall.mapper.CommentMapper;
import com.pj.mall.pojo.Comment;
import com.pj.mall.pojo.OrderDetail;
import com.pj.mall.pojo.User;
import com.pj.mall.service.CommentService;
import com.pj.mall.service.OrderService;
import com.pj.mall.service.UserService;
import com.pj.mall.vo.CommentResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author Jack
 * @create 2019-04-23 16:06
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    @Transactional
    @Override
    public void addComment(Long orderId,Long userId,List<Comment> comments) {
        User user = userService.queryUserById(userId);
        for (Comment comment : comments) {
            //查询订单详情
            OrderDetail orderDetail = orderService.queryOrderDetailById(comment.getOrderDetailId());
            comment.setColorName(orderDetail.getColorName());
            comment.setSizeName(orderDetail.getSizeName());
            comment.setUserId(userId);
            comment.setUsername(user.getUsername());
            comment.setCreateTime(new Date());
        }
        int count = commentMapper.insertList(comments);
        if(count!=comments.size()){
            throw new MallException(ExceptionEnum.INSERT_COMMENT_ERROR);
        }
        //修改订单状态
        orderService.commentOrder(orderId);
    }

    @Override
    public List<Comment> queryCommentByOrderId(Long orderId) {
        Comment comment = new Comment();
        comment.setOrderId(orderId);
        return commentMapper.select(comment);
    }

    @Override
    public CommentResult queryCommentByProductId(Long productId, Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
        Example example = new Example(Comment.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId);
        example.setOrderByClause("create_time DESC");
        List<Comment> comments = commentMapper.selectByExample(example);
        PageInfo<Comment> pageInfo = new PageInfo<>(comments);
        CommentResult commentResult = new CommentResult(pageInfo.getTotal(),pageInfo.getList(),pageInfo.getPages());
        return commentResult;
    }

    @Transactional
    @Override
    public void deleteComments(List<Long> ids) {
        int count = commentMapper.deleteByIdList(ids);
        if(count!=ids.size()){
            throw new MallException(ExceptionEnum.DELETE_COMMENT_ERROR);
        }
    }

    @Transactional
    @Override
    public void deleteCommentByProductId(Long productId) {
        Example example = new Example(Comment.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId);
        commentMapper.deleteByExample(example);
    }
}
