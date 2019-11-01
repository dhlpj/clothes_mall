package com.pj.mall.service.impl;

import com.pj.mall.pojo.Color;
import com.pj.mall.pojo.Product;
import com.pj.mall.pojo.SearchItem;
import com.pj.mall.repository.SearchItemRepository;
import com.pj.mall.service.CategoryService;
import com.pj.mall.service.ColorService;
import com.pj.mall.service.SearchService;
import com.pj.mall.util.JsonUtil;
import com.pj.mall.vo.SearchRequest;
import com.pj.mall.vo.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Jack
 * @create 2019-04-08 8:45
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SearchItemRepository repository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ColorService colorService;

    public List<SearchItem> buildSearchItem(Product product){
        //加载类目信息
        categoryService.loadCategoryName(Arrays.asList(product));
        List<SearchItem> searchItems = new ArrayList<>();
        //查询该商品下的颜色
        List<Color> colors = colorService.queryColorByProductId(product.getId());
        for (Color color : colors) {
            //构造searchItem
            SearchItem searchItem = new SearchItem();
            Map<String,Long> map = new HashMap<>();
            map.put("productId",product.getId());
            map.put("colorId",color.getId());
            String id = JsonUtil.serialize(map);
            searchItem.setId(id);
            searchItem.setCreateTime(color.getCreateTime());
            searchItem.setName(product.getName()+" "+color.getColorName());
            searchItem.setCid1(product.getCid1());
            searchItem.setCid2(product.getCid2());
            searchItem.setImage(StringUtils.substringBefore(color.getImages(),","));
            searchItem.setPrice(color.getPrice());
            searchItem.setAll(product.getName()+color.getColorName()+product.getCid1Name()+product.getCid2Name());
            searchItems.add(searchItem);
        }
        return searchItems;
    }

    @Override
    public void addProductToIndex(Product product) {
        //构建SearchItem
        List<SearchItem> searchItems = buildSearchItem(product);
        //保存到索引库
        repository.saveAll(searchItems);
    }

    @Override
    public void deleteProductFromIndex(Long productId) {
        //查询该商品下的颜色
        List<Color> colors = colorService.queryColorByProductId(productId);
        colors.forEach(color -> {
            Map<String,Long> map = new HashMap<>();
            map.put("productId",productId);
            map.put("colorId",color.getId());
            //生成索引库中的id
            String searchItemId = JsonUtil.serialize(map);
            repository.deleteById(searchItemId);
        });
    }

    @Override
    public SearchResult search(SearchRequest searchRequest) {
        int page = searchRequest.getPage()-1;//elasticSearch分页是从0页开始
        int size = searchRequest.getSize();
        Long cid1 = searchRequest.getCid1();
        Long cid2 = searchRequest.getCid2();
        String sortBy = searchRequest.getSortBy();
        Boolean descending = searchRequest.getDescending();
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //分页
        queryBuilder.withPageable(PageRequest.of(page,size));
        //搜索条件
        if(StringUtils.isNotBlank(searchRequest.getKey())){
            queryBuilder.withQuery(QueryBuilders.matchQuery("all",searchRequest.getKey()));
        }
        if(cid1!=null&&cid1!=0){
            queryBuilder.withQuery(QueryBuilders.termQuery("cid1",cid1));
        }
        if(cid2!=null&&cid2!=0){
            queryBuilder.withQuery(QueryBuilders.termQuery("cid2",cid2));
        }
        //排序
        if(StringUtils.isNotBlank(sortBy)){
            if(descending==true){
                queryBuilder.withSort(SortBuilders.fieldSort(sortBy).order(SortOrder.DESC));
            }
            if(descending==false){
                queryBuilder.withSort(SortBuilders.fieldSort(sortBy).order(SortOrder.ASC));
            }
        }
        //过滤字段(查询结果包含的字段)
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","name","price","image"},null));
        //执行搜索
        Page<SearchItem> pageInfo = repository.search(queryBuilder.build());
        //获取商品信息
        List<SearchItem> content = pageInfo.getContent();
        int totalPages = pageInfo.getTotalPages();
        long total = pageInfo.getTotalElements();
        return new SearchResult(total,content,totalPages);
    }

    @Override
    public List<SearchItem> searchNewestItem() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withPageable(PageRequest.of(0,12, Sort.by(Sort.Direction.DESC,"createTime")));
        //过滤字段(查询结果包含的字段)
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","name","price","image"},null));
        //执行搜索
        Page<SearchItem> pageInfo = repository.search(queryBuilder.build());
        return pageInfo.getContent();
    }
}
