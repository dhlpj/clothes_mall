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
 * @create 2019-03-11 23:47
 */
@Table(name="tb_category")
@Data
public class Category {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String name;
    private Long parentId;
    private Integer sortOrder;
    private Date createTime;
    private Date lastUpdateTime;

    @Transient
    private List<Category> categories;//该类目下的子目录
}
