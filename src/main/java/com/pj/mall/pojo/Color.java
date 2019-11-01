package com.pj.mall.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "tb_color")
public class Color {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private Long productId;
    private String colorName;
    private String images;
    private Long price;
    private Date createTime;// 创建时间
    private Date lastUpdateTime;// 最后修改时间

    @Transient
    private List<Size> sizes;
}