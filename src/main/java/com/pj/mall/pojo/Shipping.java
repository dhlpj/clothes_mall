package com.pj.mall.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author Jack
 * @create 2019-04-16 18:16
 */
@Data
@Table(name = "tb_Shipping")
public class Shipping{
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private Long userId;//用户id
    private String province;//收获地址（省）
    private String city;//收获地址（市）
    private String district;//收获地址（区/县）
    private String address;//收货详细地址
    private String zip;//收货人邮编
    private String mobile;//收货人手机号
    private String receiver;//收货人
    private Date createTime;//添加时间
    private Date lastUpdateTime;//最后修改时间

    @Transient
    private String totalAddress;
}
