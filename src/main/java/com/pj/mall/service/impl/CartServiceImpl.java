package com.pj.mall.service.impl;

import com.pj.mall.exception.ExceptionEnum;
import com.pj.mall.exception.MallException;
import com.pj.mall.mapper.CartItemMapper;
import com.pj.mall.pojo.CartItem;
import com.pj.mall.pojo.Color;
import com.pj.mall.pojo.Product;
import com.pj.mall.pojo.Size;
import com.pj.mall.service.CartService;
import com.pj.mall.service.ColorService;
import com.pj.mall.service.ProductService;
import com.pj.mall.service.SizeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author Jack
 * @create 2019-04-14 15:21
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartItemMapper cartItemMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private SizeService sizeService;

    @Transactional
    @Override
    public void addCartItem(CartItem cartItem) {
        //查询该用户购物车中是否有该商品
        CartItem queryItem = new CartItem();
        queryItem.setUserId(cartItem.getUserId());
        queryItem.setProductId(cartItem.getProductId());
        queryItem.setColorId(cartItem.getColorId());
        queryItem.setSizeId(cartItem.getSizeId());
        List<CartItem> select = cartItemMapper.select(queryItem);
        if(select.size()==1){
            //修改数量
            CartItem record = select.get(0);
            int totalCount = record.getNum()+cartItem.getNum();
            //判断购物车中商品数量和待加入购物车的商品数量是否超过库存
            Size size = sizeService.querySizeById(cartItem.getSizeId());
            if(totalCount>size.getStockCount()){
                throw new MallException(ExceptionEnum.STOCK_IN_CART_IS_MAX);
            }else{
                record.setNum(totalCount);
                int count = cartItemMapper.updateByPrimaryKey(record);
                if(count!=1){
                    throw new MallException(ExceptionEnum.INSERT_CART_ITEM_ERROR);
                }
            }
        }else{
            //该用户购物车中没有该商品
            Product product = productService.queryProductById(cartItem.getProductId());
            cartItem.setName(product.getName());
            Color color = colorService.queryColorById(cartItem.getColorId());
            cartItem.setColorName(color.getColorName());
            cartItem.setImage(StringUtils.substringBefore(color.getImages(),","));
            cartItem.setPrice(color.getPrice());
            Size size = sizeService.querySizeById(cartItem.getSizeId());
            cartItem.setSizeName(size.getSizeName());
            cartItem.setValid(true);
            int count = cartItemMapper.insert(cartItem);
            if(count!=1){
                throw new MallException(ExceptionEnum.INSERT_CART_ITEM_ERROR);
            }
        }
    }

    @Override
    public List<CartItem> getCartByUserId(Long userId) {
        CartItem cartItem = new CartItem();
        cartItem.setUserId(userId);
        List<CartItem> cartItems = cartItemMapper.select(cartItem);
        cartItems.forEach(item -> {
            //判断商品是否有效
            boolean valid = true;
            Product product = productService.queryOnlyProductById(item.getProductId());
            if (product==null){
                valid = false;
            }else{
                //判断是否下架
                if(product.getSaleable()==false){
                    valid = false;
                }else{
                    //判断color是否存在
                    Color color = colorService.queryOnlyColorById(item.getColorId());
                    if(color==null){
                        valid = false;
                    }else{
                        //判断size是否存在
                        Size size = sizeService.queryOnlySizeById(item.getSizeId());
                        if(size==null){
                            valid=false;
                        }
                    }
                }
            }
            if(valid){
                item.setTotalPrice(item.getPrice()*item.getNum());
                item.setValid(true);
                cartItemMapper.updateByPrimaryKey(item);
            }else{
                //无效，更新cartItem
                item.setValid(false);
                cartItemMapper.updateByPrimaryKey(item);
            }
        });
        return cartItems;
    }

    @Transactional
    @Override
    public void updateCartItemValid(Long productId){
        CartItem cartItem = new CartItem();
        cartItem.setValid(false);
        Example example = new Example(CartItem.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId);
        cartItemMapper.updateByExampleSelective(cartItem,example);
    }

    @Transactional
    @Override
    public void updateCartItemNum(Long itemId, Integer num) {
        CartItem cartItem = cartItemMapper.selectByPrimaryKey(itemId);
        if(cartItem==null){
            throw new MallException(ExceptionEnum.CART_ITEM_NOT_FOUND);
        }
        Long sizeId = cartItem.getSizeId();
        Size size = sizeService.querySizeById(sizeId);
        if(size.getStockCount()<num){
            throw new MallException(ExceptionEnum.STOCK_NOT_ENOUGH);
        }
        cartItem.setNum(num);
        cartItemMapper.updateByPrimaryKey(cartItem);
    }

    @Transactional
    @Override
    public void deleteCartItem(List<Long> itemIds) {
        if(!CollectionUtils.isEmpty(itemIds)){
            int count = cartItemMapper.deleteByIdList(itemIds);
            if(count!=itemIds.size()){
                throw new MallException(ExceptionEnum.DELETE_CART_ERROR);
            }
        }
    }

    @Override
    public List<CartItem> queryCartByIds(List<Long> itemIds) {
        if(CollectionUtils.isEmpty(itemIds)) {
            throw new MallException(ExceptionEnum.CART_ITEM_NOT_FOUND);
        }else {
            List<CartItem> cartItems = cartItemMapper.selectByIdList(itemIds);
            if (CollectionUtils.isEmpty(cartItems)) {
                throw new MallException(ExceptionEnum.CART_ITEM_NOT_FOUND);
            }
            return cartItems;
        }
    }
}
