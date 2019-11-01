package com.pj.mall.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Jack
 * @create 2019-04-02 11:00
 */
@Data
@Table(name = "tb_admin")
public class Admin {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private String username;// 用户名

    @JsonIgnore
    private String password;// 密码

    @JsonIgnore
    private String salt;// 密码的盐值
}
