package com.pj.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pj.mall.common.PageResult;
import com.pj.mall.enums.OrderStatusEnum;
import com.pj.mall.exception.ExceptionEnum;
import com.pj.mall.exception.MallException;
import com.pj.mall.mapper.OrderDetailMapper;
import com.pj.mall.mapper.OrderMapper;
import com.pj.mall.pojo.*;
import com.pj.mall.service.CartService;
import com.pj.mall.service.OrderService;
import com.pj.mall.service.ShippingService;
import com.pj.mall.service.SizeService;
import com.pj.mall.util.IdWorker;
import com.pj.mall.vo.OrderVO;
import com.pj.mall.vo.StockVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Jack
 * @create 2019-04-17 21:00
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private ShippingService shippingService;
    @Autowired
    private CartService cartService;
    @Autowired
    private SizeService sizeService;
    @Autowired
    private IdWorker idWorker;

    private final Long FreeShippingLimit = 59900L;//购物满599免邮,单位为分

    private final Long defaultPostFee = 1000L;//单位为分

    @Override
    public Map<String, Object> getOrderInfo(List<Long> itemIds, Long userId) {
        List<CartItem> cartItems = cartService.queryCartByIds(itemIds);
        //计算商品总价格
        Long productTotalPrice = 0l;
        //运费
        Long postFee = 0l;
        //订单价格(商品总价格+运费)
        Long orderTotalPrice = 0l;
        for (CartItem cartItem : cartItems) {
            //填充cartitem的totalPrice字段
            cartItem.setTotalPrice(cartItem.getPrice()*cartItem.getNum());
            //计算商品总价格
            productTotalPrice = cartItem.getTotalPrice()+productTotalPrice;
        }
        //判断是否需要运费
        if(productTotalPrice<FreeShippingLimit){
            postFee = defaultPostFee;
        }
        orderTotalPrice = postFee + productTotalPrice;
        //查找收货信息
        List<Shipping> shippings = shippingService.queryShippingByUserId(userId);
        shippings.forEach(shipping -> shipping.setTotalAddress(
                shipping.getProvince()+" "+shipping.getCity()+" "+shipping.getDistrict()+" "+shipping.getAddress()));
        Map<String,Object> map = new HashMap<>();
        map.put("cartItems",cartItems);
        map.put("shippings",shippings);
        map.put("productTotalPrice",productTotalPrice);
        map.put("postFee",postFee);
        map.put("orderTotalPrice",orderTotalPrice);
        return map;
    }

    @Transactional
    @Override
    public Long createOrder(OrderVO orderVO, Long userId) {
        //查找收货地址
        Shipping shipping = shippingService.queryShippingById(orderVO.getShippingId());
        //查找购物车商品
        List<CartItem> cartItems = cartService.queryCartByIds(orderVO.getItemIds());
        //构造order
        Order order = new Order();
        long orderId = idWorker.nextId();
        order.setOrderId(orderId);
        order.setUserId(userId);
        order.setReceiver(shipping.getReceiver());
        order.setReceiverProvince(shipping.getProvince());
        order.setReceiverDistrict(shipping.getDistrict());
        order.setReceiverCity(shipping.getCity());
        order.setReceiverAddress(shipping.getAddress());
        order.setReceiverZip(shipping.getZip());
        order.setReceiverMobile(shipping.getMobile());
        order.setPaymentType(orderVO.getPaymentType());
        order.setStatus(OrderStatusEnum.UN_PAY.value());
        //商品总金额
        Long productTotalPrice = 0l;
        //运费
        Long postFee = 0l;
        //订单价格(商品总价格+运费)
        Long orderTotalPrice = 0l;
        //订单详情
        List<OrderDetail> orderDetails = new ArrayList<>();
        //尺寸id集合
        List<StockVO> stockVOs = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            StockVO stockVO = new StockVO();
            stockVO.setSizeId(cartItem.getSizeId());
            stockVO.setNum(cartItem.getNum());
            stockVOs.add(stockVO);
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setProductId(cartItem.getProductId());
            orderDetail.setColorId(cartItem.getColorId());
            orderDetail.setSizeId(cartItem.getSizeId());
            orderDetail.setName(cartItem.getName());
            orderDetail.setColorName(cartItem.getColorName());
            orderDetail.setSizeName(cartItem.getSizeName());
            orderDetail.setPrice(cartItem.getPrice());
            orderDetail.setNum(cartItem.getNum());
            orderDetail.setImage(cartItem.getImage());
            orderDetails.add(orderDetail);
            //计算商品总价格
            productTotalPrice = cartItem.getPrice()*cartItem.getNum()+productTotalPrice;
        }
        //判断是否需要运费
        if(productTotalPrice<FreeShippingLimit){
            postFee = defaultPostFee;
        }
        orderTotalPrice = postFee + productTotalPrice;
        order.setPostFee(postFee);
        order.setTotalPay(orderTotalPrice);
        order.setCreateTime(new Date());
        //新增订单
        int count = orderMapper.insert(order);
        if(count!=1){
            log.error("【订单】生成订单失败,orderId:{}"+orderId);
            throw new MallException(ExceptionEnum.CREATE_ORDER_ERROR);
        }
        //新增订单详情
        count = orderDetailMapper.insertList(orderDetails);
        if(count!=orderDetails.size()){
            log.error("【订单】新增订单详情失败，orderId:{}",orderId);
            throw new MallException(ExceptionEnum.CREATE_ORDER_ERROR);
        }
        //减库存
        sizeService.decreaseStock(stockVOs);
        //删除购物车商品
        cartService.deleteCartItem(orderVO.getItemIds());//TODO 加入userId 避免横向越权
        return orderId;
    }

    @Override
    public Order queryOrderById(Long orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if(order==null){
            throw new MallException(ExceptionEnum.ORDER_NOT_FOUND);
        }
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(orderId);
        List<OrderDetail> orderDetails = orderDetailMapper.select(orderDetail);
        if(CollectionUtils.isEmpty(orderDetails)){
            throw new MallException(ExceptionEnum.ORDER_DETAIL_NOT_FOUND);
        }
        order.setOrderDetails(orderDetails);
        return order;
    }

    @Transactional
    @Override
    public void updateOrder(Order order) {
        int count = orderMapper.updateByPrimaryKey(order);
        if(count!=1){
            throw new MallException(ExceptionEnum.UPDATE_ORDER_ERROR);
        }
    }

    @Override
    public PageResult<List<Order>> queryOrderByPage(Integer page, Integer limit,Integer status,Long userId,Long orderId) {
        PageHelper.startPage(page,limit);
        Order order = new Order();
        if(status!=null){
            order.setStatus(status);
        }
        if(userId!=null){
            order.setUserId(userId);
        }
        if(orderId!=null){
            order.setOrderId(orderId);
        }
        List<Order> orders = orderMapper.select(order);
        PageInfo<Order> pageInfo = new PageInfo<>(orders);
        PageResult<List<Order>> pageResult = new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
        return pageResult;
    }

    @Transactional
    @Override
    public void consign(Long orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if(order==null){
            throw new MallException(ExceptionEnum.ORDER_NOT_FOUND);
        }
        order.setStatus(OrderStatusEnum.DELIVERED.value());
        order.setConsignTime(new Date());
        int count = orderMapper.updateByPrimaryKey(order);
        if(count!=1){
            throw new MallException(ExceptionEnum.UPDATE_ORDER_ERROR);
        }
    }

    @Transactional
    @Override
    public void deleteOrder(List<Long> orderIds) {
        //删除orderDetail
        for (Long orderId : orderIds) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            List<OrderDetail> orderDetails = orderDetailMapper.select(orderDetail);
            if (!CollectionUtils.isEmpty(orderDetails)){
                List<Long> orderDetailIds = orderDetails.stream().map(OrderDetail::getId).collect(Collectors.toList());
                int count = orderDetailMapper.deleteByIdList(orderDetailIds);
                if(count!=orderDetailIds.size()){
                    throw new MallException(ExceptionEnum.DELETE_ORDER_ERROR);
                }
            }
        }
        //删除order
        int count = orderMapper.deleteByIdList(orderIds);
        if(count!=orderIds.size()){
            throw new MallException(ExceptionEnum.DELETE_ORDER_ERROR);
        }
    }

    @Override
    public Map<String,Integer> queryOrderCountByUserId(Long userId) {
        Map<String,Integer> map = new HashMap<>();
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatusEnum.UN_PAY.value());
        int count = orderMapper.selectCount(order);
        map.put("un_pay",count);
        order.setStatus(OrderStatusEnum.PAYED.value());
        count = orderMapper.selectCount(order);
        map.put("payed",count);
        order.setStatus(OrderStatusEnum.DELIVERED.value());
        count = orderMapper.selectCount(order);
        map.put("delivered",count);
        order.setStatus(OrderStatusEnum.CONFIRMED.value());
        count = orderMapper.selectCount(order);
        map.put("confirmed",count);
        return map;
    }

    @Override
    public void loadOrderDetail(List<Order> data) {
        for (Order order : data) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(order.getOrderId());
            List<OrderDetail> orderDetails = orderDetailMapper.select(orderDetail);
            order.setOrderDetails(orderDetails);
        }
    }

    @Transactional
    @Override
    public void confirmOrder(Long orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if(order==null){
            throw new MallException(ExceptionEnum.ORDER_NOT_FOUND);
        }
        order.setStatus(OrderStatusEnum.CONFIRMED.value());
        order.setEndTime(new Date());
        int count = orderMapper.updateByPrimaryKey(order);
        if(count!=1){
            throw new MallException(ExceptionEnum.UPDATE_ORDER_ERROR);
        }
    }

    @Transactional
    @Override
    public void commentOrder(Long orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if(order==null){
            throw new MallException(ExceptionEnum.ORDER_NOT_FOUND);
        }
        order.setStatus(OrderStatusEnum.RATED.value());
        order.setCommentTime(new Date());
        int count = orderMapper.updateByPrimaryKey(order);
        if(count!=1){
            throw new MallException(ExceptionEnum.UPDATE_ORDER_ERROR);
        }
    }

    @Override
    public OrderDetail queryOrderDetailById(Long orderDetailId) {
        OrderDetail orderDetail = orderDetailMapper.selectByPrimaryKey(orderDetailId);
        if(orderDetail==null){
            throw new MallException(ExceptionEnum.ORDER_DETAIL_NOT_FOUND);
        }
        return orderDetail;
    }
}
