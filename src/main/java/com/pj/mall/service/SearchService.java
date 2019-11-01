package com.pj.mall.service;

import com.pj.mall.pojo.Product;
import com.pj.mall.pojo.SearchItem;
import com.pj.mall.vo.SearchRequest;
import com.pj.mall.vo.SearchResult;

import java.util.List;

/**
 * @author Jack
 * @create 2019-04-08 8:45
 */
public interface SearchService {
    /**
     * 构建SearchItem
     * @param product 包含分类信息
     * @return
     */
    List<SearchItem> buildSearchItem(Product product);

    /**
     * 向索引库中添加数据
     * @param product 必须是完整的
     *
     */
    void addProductToIndex(Product product);

    /**
     * 从索引库中删除数据
     * @param productId
     */
    void deleteProductFromIndex(Long productId);

    /**
     * 执行搜索
     * @param searchRequest
     * @return
     */
    SearchResult search(SearchRequest searchRequest);

    /**
     * 搜索最新的商品数据
     * @return
     */
    List<SearchItem> searchNewestItem();
}
