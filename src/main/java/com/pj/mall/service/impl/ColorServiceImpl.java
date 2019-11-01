package com.pj.mall.service.impl;

import com.pj.mall.exception.ExceptionEnum;
import com.pj.mall.exception.MallException;
import com.pj.mall.mapper.ColorMapper;
import com.pj.mall.pojo.Color;
import com.pj.mall.pojo.Product;
import com.pj.mall.pojo.Size;
import com.pj.mall.service.ColorService;
import com.pj.mall.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jack
 * @create 2019-03-26 10:40
 */
@Service
public class ColorServiceImpl implements ColorService {
    @Autowired
    private ColorMapper colorMapper;
    @Autowired
    private SizeService sizeService;

    @Transactional
    @Override
    public void addColors(Product product) {
        List<Color> colors = product.getColors();
        for (Color color : colors) {
            color.setCreateTime(new Date());
            color.setLastUpdateTime(color.getCreateTime());
            color.setProductId(product.getId());
            int colorCount = colorMapper.insert(color);
            if(colorCount!=1){
                throw new MallException(ExceptionEnum.INSERT_COLOR_ERROR);
            }
            //新增商品尺寸
            sizeService.addSizes(color);
        }
    }

    @Override
    public List<Color> queryColorByProductId(Long id) {
        Color color = new Color();
        color.setProductId(id);
        List<Color> colors = colorMapper.select(color);
        for (Color c : colors) {
            //查询尺寸信息
            List<Size> sizes = sizeService.querySizeByColorId(c.getId());
            c.setSizes(sizes);
        }
        return colors;
    }

    @Transactional
    @Override
    public void deleteColorByProductId(Long productId) {
        //删除color和size
        Color color = new Color();
        color.setProductId(productId);
        //(1)查询color
        List<Color> colors = colorMapper.select(color);
        //(2)删除color
        if(!CollectionUtils.isEmpty(colors)){
            List<Long> colorIdList = colors.stream().map(Color::getId).collect(Collectors.toList());
            int count = colorMapper.deleteByIdList(colorIdList);
            if(count!=colorIdList.size()){
                throw new MallException(ExceptionEnum.DELETE_COLOR_ERROR);
            }
            //(3)删除size
            for (Long colorId:colorIdList){
                sizeService.deleteSizeByColorId(colorId);
            }
        }
    }

    @Override
    public Color queryColorById(Long colorId) {
        Color color = colorMapper.selectByPrimaryKey(colorId);
        if(color==null){
            throw new MallException(ExceptionEnum.COLOR_NOT_FOUND);
        }
        return color;
    }

    @Override
    public Color queryOnlyColorById(Long colorId) {
        Color color = colorMapper.selectByPrimaryKey(colorId);
        return color;
    }
}
