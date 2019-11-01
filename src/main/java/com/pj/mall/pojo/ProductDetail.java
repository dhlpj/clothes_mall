package com.pj.mall.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name="tb_product_detail")
public class ProductDetail {
    @Id
    private Long productId;// id
    private String description;// 商品描述
    private Date createTime;// 创建时间
    private Date lastUpdateTime;// 最后修改时间
}