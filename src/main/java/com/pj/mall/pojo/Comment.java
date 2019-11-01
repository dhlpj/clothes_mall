package com.pj.mall.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Jack
 * @create 2019-04-23 15:45
 */
@Data
@Table(name="tb_comment")
public class Comment {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;//id
    private Long orderId;//订单id
    private Long orderDetailId;//商品详情的id
    private Long productId;//商品的id
    private Long userId;//用户的id
    private String username;//用户名
    private String colorName;//商品颜色
    private String sizeName;//商品尺寸
    private String commentText;//评价内容
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")//调整时差
    private Date createTime;//创建时间
}
