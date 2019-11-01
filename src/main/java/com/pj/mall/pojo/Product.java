package com.pj.mall.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * @author Jack
 * @create 2019-03-14 11:27
 */
@Data
@Table(name = "tb_product")
public class Product{
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private Long cid1;// 1级类目
    private Long cid2;// 2级类目
    private String name;// 名字
    private Boolean saleable;// 是否上架
    private Date createTime;// 创建时间
    private Date lastUpdateTime;// 最后修改时间

    @Transient
    private String cid1Name;// 1级类目名称
    @Transient
    private String cid2Name;// 2级类目名称
    @Transient
    private List<Color> colors;//商品颜色信息
    @Transient
    private ProductDetail productDetail;//商品详情
}