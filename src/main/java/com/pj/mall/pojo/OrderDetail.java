package com.pj.mall.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Jack
 * @create 2019-04-17 21:08
 */
@Data
@Table(name = "tb_order_detail")
public class OrderDetail {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private Long orderId;// 订单id
    private Long productId;// 商品id
    private Long colorId;//颜色id
    private Long sizeId;//尺寸id
    private String name;// 商品名称
    private String colorName;//颜色名称
    private String sizeName;//尺寸名称
    private Long price;// 商品单价
    private Integer num;// 商品购买数量
    private String image;// 图片
}
