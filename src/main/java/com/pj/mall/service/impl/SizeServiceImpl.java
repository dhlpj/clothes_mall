package com.pj.mall.service.impl;

import com.pj.mall.exception.ExceptionEnum;
import com.pj.mall.exception.MallException;
import com.pj.mall.mapper.SizeMapper;
import com.pj.mall.pojo.Color;
import com.pj.mall.pojo.Size;
import com.pj.mall.service.SizeService;
import com.pj.mall.vo.StockVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author Jack
 * @create 2019-03-31 17:30
 */
@Service
public class SizeServiceImpl implements SizeService{
    @Autowired
    private SizeMapper sizeMapper;

    @Transactional
    @Override
    public void addSizes(Color color) {
        List<Size> sizes = color.getSizes();
        for (Size size : sizes) {
            size.setCreateTime(new Date());
            size.setLastUpdateTime(size.getCreateTime());
            size.setColorId(color.getId());
        }
        int sizeCount = sizeMapper.insertList(sizes);
        if(sizeCount!=sizes.size()){
            throw new MallException(ExceptionEnum.INSERT_SIZE_ERROR);
        }
    }

    @Override
    public List<Size> querySizeByColorId(Long colorId) {
        Size size = new Size();
        size.setColorId(colorId);
        List<Size> sizes = sizeMapper.select(size);
        return sizes;
    }

    @Transactional
    @Override
    public void deleteSizeByColorId(Long colorId) {
        Size size = new Size();
        Example example = new Example(Size.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("colorId",colorId);
        sizeMapper.deleteByExample(example);
    }

    @Override
    public Size querySizeById(Long sizeId) {
        Size size = sizeMapper.selectByPrimaryKey(sizeId);
        if(size==null){
            throw new MallException(ExceptionEnum.SIZE_NOT_FOUND);
        }
        return size;
    }

    @Override
    public Size queryOnlySizeById(Long sizeId) {
        Size size = sizeMapper.selectByPrimaryKey(sizeId);
        return size;
    }

    @Transactional
    @Override
    public void decreaseStock(List<StockVO> stockVOs) {
        for (StockVO stockVO : stockVOs) {
            int count = sizeMapper.decreaseStock(stockVO.getSizeId(), stockVO.getNum(),new Date());
            if(count!=1){
                throw new MallException(ExceptionEnum.STOCK_NOT_ENOUGH);
            }
        }
    }
}
