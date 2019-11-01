package com.pj.mall.controller.admin;

import com.pj.mall.common.PageResult;
import com.pj.mall.pojo.Product;
import com.pj.mall.service.CategoryService;
import com.pj.mall.service.CommentService;
import com.pj.mall.service.ProductService;
import com.pj.mall.vo.CommentResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Jack
 * @create 2019-03-16 18:56
 */
@Controller("adminProductController")
@RequestMapping("/admin/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CommentService commentService;

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
    @RequestMapping("/page")
    @ResponseBody
    public PageResult<List<Product>> queryProductByPage(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "limit",defaultValue = "5") Integer limit,
            @RequestParam(value = "saleable",required = false) Boolean saleable,
            @RequestParam(value = "keyword",required = false) String keyword,
            @RequestParam(value = "cid",required = false) Long cid,
            @RequestParam(value = "sortField",required = false) String sortField,
            @RequestParam(value = "sortType",required = false) String sortType){
        PageResult<List<Product>> pageResult = productService.queryProductByPage(page,limit,saleable,keyword,cid,sortField,sortType);
        //解析商品的类目信息
        categoryService.loadCategoryName(pageResult.getData());
        return pageResult;
    }

    /**
     * 更改商品上下架状态
     * @param productId
     * @param saleable
     * @return
     */
    @PutMapping("/changeSaleable/{productId}")
    @ResponseBody
    public PageResult<Void> changeSaleable(@PathVariable("productId") Long productId,@RequestParam("saleable")Boolean saleable){
        productService.changeSaleable(productId,saleable);

        return new PageResult<>();
    }

    /**
     * 新增商品
     * @param product
     * @return
     */
    @PostMapping
    @ResponseBody
    public PageResult<Void> addProduct(@RequestBody Product product){
        productService.addProduct(product);
        return new PageResult<>();
    }

    /**
     * 根据id查询商品
     * @param productId
     * @return
     */
    @GetMapping("/{productId}")
    @ResponseBody
    public PageResult<Product> queryProductById(@PathVariable("productId") Long productId){
        Product product = productService.queryProductById(productId);
        return new PageResult<>(product);
    }

    /**
     * 更新商品
     * @param productId
     * @param product
     * @return
     */
    @PutMapping("/{productId}")
    @ResponseBody
    public PageResult<Void> updateProduct(@PathVariable("productId") Long productId,@RequestBody Product product){
        productService.updateProduct(productId,product);
        return new PageResult<>();
    }

    /**
     * 批量删除商品
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    @ResponseBody
    public PageResult<Void> deleteProducts(@PathVariable("ids") List<Long> ids){
        productService.deleteProducts(ids);
        return new PageResult<>();
    }

    @GetMapping("/comment/{productId}")
    @ResponseBody
    public CommentResult queryCommentByProductId(@PathVariable("productId") Long productId,
                                                 @RequestParam(value = "page",defaultValue = "1") Integer page,
                                                 @RequestParam(value = "limit",defaultValue = "10") Integer limit){
        return commentService.queryCommentByProductId(productId, page, limit);
    }

    @DeleteMapping("/comment/{ids}")
    @ResponseBody
    public PageResult<Void> deleteComments(@PathVariable List<Long> ids){
        commentService.deleteComments(ids);
        return new PageResult<>();
    }
}
