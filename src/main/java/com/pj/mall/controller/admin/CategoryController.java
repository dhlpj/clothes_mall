package com.pj.mall.controller.admin;

import com.pj.mall.common.PageResult;
import com.pj.mall.pojo.Category;
import com.pj.mall.service.CategoryService;
import com.pj.mall.vo.CategoryNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jack
 * @create 2019-03-11 23:52
 */
@RequestMapping("/admin/category")
@Controller("adminCategoryController")//避免与前台的controller命名冲突
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 根据parentId查询分类信息
     * @param parentId
     * @return List<CategoryNode>
     */
    @GetMapping("/list")
    @ResponseBody
    public PageResult<List<CategoryNode>> queryCategoryByPid(@RequestParam(value = "nodeId",defaultValue = "0") Long parentId){
        List<Category> categories = categoryService.queryCategoryByPid(parentId);
        List<CategoryNode> categoryNodes = new ArrayList<>();
        categories.forEach(category -> {
            CategoryNode categoryNode = new CategoryNode();
            categoryNode.setId(category.getId().toString());
            categoryNode.setParentId(category.getParentId().toString());
            categoryNode.setIsLast(category.getParentId()!=0);
            categoryNode.setTitle(category.getName());
            categoryNodes.add(categoryNode);
        });
        return new PageResult<List<CategoryNode>>(Long.parseLong(String.valueOf(categoryNodes.size())),categoryNodes);
    }

    /**
     * 新增分类
     * @param category
     * @return id
     */
    @PostMapping
    @ResponseBody
    public PageResult<Long> addCategory(Category category){
        Long id = categoryService.addCategory(category);
        return new PageResult<Long>(id);
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public PageResult<Void> deleteCategory(@PathVariable("id") Long id){
        categoryService.deleteCategory(id);
        return new PageResult<>();
    }

    /**
     * 更新分类
     * @param id
     * @param category
     * @return
     */
    @PutMapping("/{id}")
    @ResponseBody
    public PageResult<Void> updateCategory(@PathVariable("id") Long id,Category category){
        categoryService.updateCategory(id,category);
        return new PageResult<>();
    }


}
