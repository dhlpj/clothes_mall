package com.pj.mall.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Jack
 * @create 2019-04-18 10:37
 */
@Data
public class OrderVO {
    @NotNull
    private Long shippingId; // 收获人地址id
    @NotNull
    private Integer paymentType;// 付款类型，1、在线支付，2、货到付款',
    @NotNull
    private List<Long> itemIds;// 购物车项id
}
