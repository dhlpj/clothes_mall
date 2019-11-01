package com.pj.mall.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 商品的快照
 * @author Jack
 * @create 2019-04-13 10:03
 */
@Table(name = "tb_cart_item")
@Data
public class CartItem {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;// id
    private Long userId;// 用户id
    private Long productId;// 商品id
    private String name;// 名称
    private Long colorId;// 颜色id
    private Long sizeId;// 尺寸id
    private String colorName;//商品颜色
    private String sizeName;//商品尺寸
    private String image;// 图片
    private Long price;// 加入购物车时的价格
    private Integer num;// 购买数量
    private Boolean valid;//是否有效

    @Transient
    private Long totalPrice;
}
