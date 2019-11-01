package com.pj.mall.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_size")
@Data
public class Size {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private Long colorId;
    private String sizeName;
    private Integer stockCount;
    private Date createTime;// 创建时间
    private Date lastUpdateTime;// 最后修改时间
}