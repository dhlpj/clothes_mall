package com.pj.mall.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * @author Jack
 * @create 2019-04-17 21:06
 */
@Data
@Table(name = "tb_order")
public class Order {
    @JsonSerialize(using = ToStringSerializer.class)//转化为json时，前端接收Long会精度丢失，所以在转化为json时，将其转化为String
    @Id
    private Long orderId;// id,不能自动生成
    private Long userId;//用户id
    private Integer paymentType; // 支付类型，1、在线支付，2、货到付款
    private Long postFee;// 邮费
    private Long totalPay;// 总金额
    private Integer status;//状态：1、未付款 2、已付款,未发货 3、已发货,未确认 4、交易成功 5、交易关闭 6、已评价
    private String receiver; // 收货人全名
    private String receiverMobile; // 移动电话
    private String receiverProvince; // 省份
    private String receiverCity; // 城市
    private String receiverDistrict; // 区/县
    private String receiverAddress; // 收货地址，如：xx路xx号
    private String receiverZip; // 邮政编码,如：310001
    private Date createTime;// 创建时间
    private Date paymentTime;// 付款时间
    private Date consignTime;// 发货时间
    private Date endTime;// 交易结束时间
    private Date closeTime;// 交易关闭时间
    private Date commentTime;// 评价时间

    @Transient
    private User user;
    @Transient
    private List<OrderDetail> orderDetails;
}
