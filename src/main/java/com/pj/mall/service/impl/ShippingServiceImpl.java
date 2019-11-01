package com.pj.mall.service.impl;

import com.pj.mall.exception.ExceptionEnum;
import com.pj.mall.exception.MallException;
import com.pj.mall.mapper.ShippingMapper;
import com.pj.mall.pojo.Order;
import com.pj.mall.pojo.Shipping;
import com.pj.mall.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Jack
 * @create 2019-04-17 8:30
 */
@Service
public class ShippingServiceImpl implements ShippingService {
    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public List<Shipping> queryShippingByUserId(Long userId) {
        Shipping shipping = new Shipping();
        shipping.setUserId(userId);
        List<Shipping> shippings = shippingMapper.select(shipping);
        return shippings;
    }

    @Transactional
    @Override
    public void addShipping(Shipping shipping) {
        shipping.setCreateTime(new Date());
        shipping.setLastUpdateTime(new Date());
        int count = shippingMapper.insert(shipping);
        if(count!=1){
            throw new MallException(ExceptionEnum.INSERT_SHIPPING_ERROR);
        }
    }

    @Transactional
    @Override
    public void updateShipping(Long id, Shipping shipping) {
        //先查询
        Shipping record = shippingMapper.selectByPrimaryKey(id);
        if(record==null){
            throw new MallException(ExceptionEnum.SHIPPING_NOT_FOUND);
        }
        record.setProvince(shipping.getProvince());
        record.setCity(shipping.getCity());
        record.setDistrict(shipping.getDistrict());
        record.setAddress(shipping.getAddress());
        record.setMobile(shipping.getMobile());
        record.setReceiver(shipping.getReceiver());
        record.setZip(shipping.getZip());
        record.setLastUpdateTime(new Date());
        int count = shippingMapper.updateByPrimaryKey(record);
        if(count!=1){
            throw new MallException(ExceptionEnum.UPDATE_SHIPPING_ERROR);
        }
    }

    @Transactional
    @Override
    public void deleteShipping(Long id) {
        int count = shippingMapper.deleteByPrimaryKey(id);
        if(count!=1){
            throw new MallException(ExceptionEnum.DELETE_SHIPPING_ERROR);
        }
    }

    @Override
    public Shipping queryShippingById(Long shippingId) {
        Shipping shipping = shippingMapper.selectByPrimaryKey(shippingId);
        if(shipping==null){
            throw new MallException(ExceptionEnum.SHIPPING_NOT_FOUND);
        }
        return shipping;
    }
}
