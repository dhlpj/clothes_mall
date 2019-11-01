package com.pj.mall.service;

import com.pj.mall.pojo.Color;
import com.pj.mall.pojo.Size;
import com.pj.mall.vo.StockVO;

import java.util.List;

/**
 * @author Jack
 * @create 2019-03-31 17:30
 */
public interface SizeService {
    /**
     * 根据color添加尺寸集合
     * @param color
     */
    void addSizes(Color color);

    /**
     * 根据颜色id查找尺寸信息
     * @param colorId
     * @return
     */
    List<Size> querySizeByColorId(Long colorId);

    /**
     * 根据颜色id删除尺寸信息
     * @param colorId
     */
    void deleteSizeByColorId(Long colorId);

    /**
     * 根据id查询尺寸信息
     * @param sizeId
     * @return
     */
    Size querySizeById(Long sizeId);

    /**
     * 根据id查询尺寸信息，但不会抛出异常
     * @param sizeId
     * @return
     */
    Size queryOnlySizeById(Long sizeId);

    /**
     * 减库存
     * @param stockVOs
     */
    void decreaseStock(List<StockVO> stockVOs);
}
