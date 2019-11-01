package com.pj.mall.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jack
 * @create 2019-03-11 23:13
 */
@AllArgsConstructor
@Getter
public enum ExceptionEnum {
    CATEGORY_NOT_FOUND(404,"无商品分类信息"),
    SERVER_INTERNAL_EXCEPTION(500,"服务器内部异常"),
    EXIST_SAME_CATEGORY_NAME(400,"新增失败！该节点下该名称已被占用" ),
    INSERT_CATEGORY_ERROR(500, "添加商品分类失败"),
    EXIST_SUB_CATEGORY(400, "删除失败！该节点存在子分类"),
    EXIST_PRODUCTS(400,"删除失败！该节点下存在商品"),
    DELETE_CATEGORY_ERROR(500,"删除分类失败"),
    UPDATE_CATEGORY_ERROR(500,"更新分类失败" ),
    PRODUCT_NOT_FOUND(404,"无商品信息"),
    INVALID_FILE_TYPE(400,"文件类型无效" ),
    UPLOAD_FILE_ERROR(500,"上传文件失败" ),
    INSERT_PRODUCT_ERROR(500,"添加商品失败" ),
    INSERT_PRODUCT_DETAIL_ERROR(500,"添加商品详情失败"),
    INSERT_COLOR_ERROR(500, "添加颜色失败"),
    DELETE_COLOR_ERROR(500, "删除颜色失败"),
    INSERT_SIZE_ERROR(500,"添加尺寸失败" ),
    UPDATE_PRODUCT_ERROR(500,"更新商品失败" ),
    DELETE_PRODUCT_ERROR(500, "删除商品失败"),
    UPDATE_PRODUCT_DETAIL_ERROR(500,"更新商品详情失败"),
    CAPTCHA_ERROR(400,"验证码输入错误" ),
    CAPTCHA_NOT_FOUND(404,"未查找到验证码"),
    CAPTCHA_TIMEOUT(400, "该验证码已失效"),
    CAPTCHA_RENDER_ERROR(500,"验证码渲染失败"),
    INVALID_USERNAME_OR_PASSWORD(400 ,"用户名或密码错误" ),
    PASSWORD_ERROR(400,"密码错误"),
    ADMIN_NOT_FOUND(404,"该管理员不存在"),
    INVALID_USER_DATA_TYPE(400,"无效的校验类型"),
    USERNAME_ALREADY_EXIST(400,"该用户名已存在"),
    PHONE_ALREADY_EXIST(400,"该手机号已存在"),
    UNAUTHORIZED(403, "用户未登录"),
    SIZE_NOT_FOUND(404,"无尺寸信息" ),
    INSERT_CART_ITEM_ERROR(500,"添加到购物车失败"),
    CART_ITEM_NOT_FOUND(404,"未查找到购物车中的商品"),
    DELETE_CART_ERROR(500, "删除购物车商品失败"),
    STOCK_NOT_ENOUGH(500,"该商品库存量不足"),
    COLOR_NOT_FOUND(404, "无颜色信息"),
    INSERT_SHIPPING_ERROR(500, "添加收货地址失败"),
    SHIPPING_NOT_FOUND(404, "该收货地址不存在"),
    UPDATE_SHIPPING_ERROR(500, "更新收货地址失败"),
    DELETE_SHIPPING_ERROR(500,"删除收货地址失败"),
    CREATE_ORDER_ERROR(500, "生成订单失败"),
    ORDER_NOT_FOUND(404, "未查找到订单"),
    ORDER_DETAIL_NOT_FOUND(404, "订单详情不存在"),
    ORDER_STATUS_ERROR(400, "订单状态异常"),
    JUMP_TO_ALIPAY_ERROR(500, "跳转到支付宝失败"),
    INVALID_ORDER_PARAM(400, "无效的订单参数"),
    ALIPAY_RETURN_ERROR(500, "支付宝同步返回调用错误"),
    UPDATE_ORDER_ERROR(500, "修改订单失败"),
    DELETE_ORDER_ERROR(500, "删除订单失败"),
    STOCK_IN_CART_IS_MAX(400, "购物车中的商品数量已达到库存上限"),
    USER_NOT_FOUND(400,"用户不存在"),
    INSERT_COMMENT_ERROR(500, "添加评论失败"),
    DELETE_COMMENT_ERROR(500, "删除评论失败");
    private int code;
    private String msg;
}
