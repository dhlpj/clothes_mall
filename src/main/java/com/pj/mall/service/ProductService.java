package com.pj.mall.service;

import com.pj.mall.common.PageResult;
import com.pj.mall.pojo.Product;

import java.util.List;
import java.util.Map;

/**
 * @author Jack
 * @create 2019-03-16 18:54
 */
public interface ProductService {
    /**
     * 带分页的查询
     * @param page
     * @param limit
     * @param saleable
     * @param keyword
     * @param cid
     * @param sortField
     * @param sortType
     * @return
     */
    PageResult<List<Product>> queryProductByPage(Integer page, Integer limit, Boolean saleable,String keyword, Long cid, String sortField, String sortType);

    /**
     * 更改商品上下架状态
     * @param id
     * @param saleable
     */
    void changeSaleable(Long id, Boolean saleable);

    /**
     * 新增商品
     * @param product
     */
    void addProduct(Product product);

    /**
     * 根据商品id查询商品
     * @param id
     * @return
     */
    Product queryProductById(Long id);

    /**
     * 更新商品
     * @param productId
     * @param product
     */
    void updateProduct(Long productId, Product product);

    /**
     * 批量删除商品
     * @param ids
     */
    void deleteProducts(List<Long> ids);

    /**
     * 根据商品分类id查询商品
     * @param cid1
     * @param cid2
     * @return
     */
    List<Product> queryProductByCategory(Long cid1, Long cid2);

    /**
     * 根据productId和colorId加载商品
     * @param productId
     * @param colorId
     * @return
     */
    Map<String,Object> loadModel(Long productId, Long colorId);

    /**
     * 只查询product,并且为null不会抛出异常
     * @param productId
     * @return
     */
    Product queryOnlyProductById(Long productId);
}
