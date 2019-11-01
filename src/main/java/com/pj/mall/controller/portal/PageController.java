package com.pj.mall.controller.portal;

import com.pj.mall.pojo.Category;
import com.pj.mall.pojo.SearchItem;
import com.pj.mall.service.CategoryService;
import com.pj.mall.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Jack
 * @create 2019-04-13 10:13
 */
@Controller("portalPageController")
public class PageController{
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SearchService searchService;

    @RequestMapping("/portal/index.html")
    public String toIndexPage(HttpSession session, HttpServletRequest request){
        Object object = session.getAttribute("categories");
        if (object==null){
            List<Category> categoryList = categoryService.queryCategoryList();
            session.setAttribute("categories",categoryList);
        }
        List<SearchItem> searchItems = searchService.searchNewestItem();
        request.setAttribute("searchItems",searchItems);
        return "/portal/index";
    }

    @RequestMapping("/portal/login.html")
    public String toLoginPage(){
        return "/portal/login";
    }

    @RequestMapping("/portal/register.html")
    public String toRegisterPage(){
        return "/portal/register.html";
    }
}
