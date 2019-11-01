package com.pj.mall;

import com.pj.mall.common.PageResult;
import com.pj.mall.pojo.Product;
import com.pj.mall.pojo.SearchItem;
import com.pj.mall.repository.SearchItemRepository;
import com.pj.mall.service.CategoryService;
import com.pj.mall.service.ProductService;
import com.pj.mall.service.SearchService;
import com.pj.mall.util.JsonUtil;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jack
 * @create 2019-04-08 9:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataImportTest {
    @Autowired
    private ElasticsearchTemplate template;
    @Autowired
    private SearchItemRepository repository;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SearchService searchService;

    @Test
    public void testCreateIndex(){
        template.createIndex(SearchItem.class);
        template.putMapping(SearchItem.class);
    }

    @Test
    public void testDeleteIndex(){
        template.deleteIndex(SearchItem.class);
    }

    @Test
    public void testDeleteAll(){
        repository.deleteAll();
    }

    @Test
    public void testDataImport(){
        int page = 1;
        int limit = 100;
        long count = 0;
        do {
            PageResult<List<Product>> pageResult = productService.queryProductByPage(page, limit, true, null, null, null, null);
            if(pageResult.getCount()!=0){
                List<Product> products = pageResult.getData();
                categoryService.loadCategoryName(products);
                //searchItem
                for (Product product : products) {
                    List<SearchItem> searchItems = searchService.buildSearchItem(product);
                    //存入索引库
                    repository.saveAll(searchItems);
                }
                count = products.size();
            }
            page++;
        }while (count==limit);
    }

    @Test
    public void testDeleteData(){
        Map<String,Long> map = new HashMap<>();
        map.put("productId",13L);
        map.put("colorId",25L);
        repository.deleteById(JsonUtil.serialize(map));
    }

    @Test
    public void testSearchAll(){
        Iterable<SearchItem> search = repository.search(QueryBuilders.matchAllQuery());
        search.forEach(searchItem -> System.out.println(searchItem.getId()+":"+searchItem.getAll()));
    }
}