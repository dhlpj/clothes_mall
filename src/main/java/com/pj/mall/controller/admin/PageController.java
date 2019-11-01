package com.pj.mall.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Jack
 * @create 2019-03-11 18:12
 */
@Controller("adminPageController")
public class PageController {
    @RequestMapping(value="/admin/{page}")
    public String showAdminPage(@PathVariable("page") String page){
        return "/admin/"+page;
    }
}
