package com.pj.mall.service;

import com.pj.mall.pojo.Category;
import com.pj.mall.pojo.Product;

import java.util.List;

/**
 * @author Jack
 * @create 2019-03-12 11:19
 */
public interface CategoryService {
    /**
     * 根据parentId查询分类
     * @param parentId
     * @return
     */
    List<Category> queryCategoryByPid(Long parentId);

    /**
     * 添加分类
     * @param category
     * @return
     */
    Long addCategory(Category category);

    /**
     * 删除分类
     * @param id
     */
    void deleteCategory(Long id);

    /**
     * 更新分类
     * @param id
     * @param category
     */
    void updateCategory(Long id, Category category);

    /**
     * 解析分类的名称
     * @param products
     * @return
     */
    void loadCategoryName(List<Product> products);

    /**
     * 查询所有的分类，并一级一级封装
     * @return
     */
    List<Category> queryCategoryList();

    /**
     * 根据id查询分类
     * @param cid
     * @return
     */
    Category queryCategoryById(Long cid);
}
