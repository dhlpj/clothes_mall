package com.pj.mall.controller.portal;

import com.pj.mall.exception.MallException;
import com.pj.mall.service.CommentService;
import com.pj.mall.service.ProductService;
import com.pj.mall.vo.CommentResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Jack
 * @create 2019-04-06 19:15
 */
@Controller
@RequestMapping("/portal/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/productDetail")
    public String toItemPage(@RequestParam("productId")Long productId,
                             @RequestParam("colorId") Long colorId,
                             Model model){
        try{
            Map<String,Object> attributes = productService.loadModel(productId,colorId);
            model.addAllAttributes(attributes);
            return "/portal/productDetail";
        }catch (MallException exception){
            exception.printStackTrace();
            return "/portal/productNotFound";//捕获异常。
        }
    }

    @GetMapping("/comment/{productId}")
    @ResponseBody
    public CommentResult queryCommentByProductId(@PathVariable("productId") Long productId,
                                     @RequestParam(value = "page",defaultValue = "1") Integer page,
                                     @RequestParam(value = "limit",defaultValue = "10") Integer limit){
        return commentService.queryCommentByProductId(productId,page,limit);
    }
}
