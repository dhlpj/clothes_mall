package com.pj.mall.controller.portal;

import com.pj.mall.service.SearchService;
import com.pj.mall.vo.SearchRequest;
import com.pj.mall.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jack
 * @create 2019-04-08 9:07
 */
@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    @RequestMapping("/portal/search.html")
    public String toSearchPage(){
        return "/portal/search";
    }

    @PostMapping("/portal/search")
    @ResponseBody
    public SearchResult search(@RequestBody SearchRequest searchRequest){
        SearchResult searchResult = searchService.search(searchRequest);
        return searchResult;
    }
}
