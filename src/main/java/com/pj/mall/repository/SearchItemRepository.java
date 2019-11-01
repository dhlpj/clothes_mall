package com.pj.mall.repository;

import com.pj.mall.pojo.SearchItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Jack
 * @create 2019-04-08 8:43
 */
public interface SearchItemRepository extends ElasticsearchRepository<SearchItem,String> {
}
