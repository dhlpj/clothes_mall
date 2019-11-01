package com.pj.mall.vo;

import com.pj.mall.common.PageResult;
import com.pj.mall.pojo.SearchItem;
import lombok.Data;

import java.util.List;

/**
 * @author Jack
 * @create 2019-04-07 22:26
 */
@Data
public class SearchResult extends PageResult<List<SearchItem>>{
    private Integer totalPage;
    //private List<Category> categories;

    /**
     * 无返回数据时
     */
    public SearchResult(){
        super();
    }

    /**
     * 有返回数据时,且为集合
     * @param total
     * @param data
     */
    public SearchResult(Long total,List<SearchItem> data,Integer totalPage){
        super(total,data);
        this.totalPage = totalPage;
    }
}
