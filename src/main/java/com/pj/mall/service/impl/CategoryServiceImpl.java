package com.pj.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pj.mall.common.PageResult;
import com.pj.mall.exception.ExceptionEnum;
import com.pj.mall.exception.MallException;
import com.pj.mall.mapper.CategoryMapper;
import com.pj.mall.mapper.ProductMapper;
import com.pj.mall.pojo.Category;
import com.pj.mall.pojo.Product;
import com.pj.mall.service.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author Jack
 * @create 2019-03-12 11:20
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Category> queryCategoryByPid(Long parentId) {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("parentId",parentId);
        String orderByClause = "sort_order ASC";//此处不能使用驼峰，只能使用数据库中列名
        example.setOrderByClause(orderByClause);
        List<Category> categories = categoryMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(categories)){
            throw new MallException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        return categories;
    }

    @Transactional
    @Override
    public Long addCategory(Category category) {
        category.setCreateTime(new Date());
        category.setLastUpdateTime(category.getCreateTime());
        //查找新增节点的兄弟节点
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("parentId",category.getParentId());
        List<Category> categories = categoryMapper.selectByExample(example);
        int maxSortOrder = 0;
        if(!CollectionUtils.isEmpty(categories)){
            for (Category cat : categories) {
                //判断该分类下是否存在同名的节点
                if(cat.getName().equals(category.getName())){
                    throw new MallException(ExceptionEnum.EXIST_SAME_CATEGORY_NAME);
                }
                //查找sortOrder的最大值
                if (cat.getSortOrder()>maxSortOrder){
                    maxSortOrder=cat.getSortOrder();
                }

            }
        }
        //设置sortOrder值
        category.setSortOrder(maxSortOrder+1);
        int insertCount = categoryMapper.insert(category);
        if(insertCount==0){
            throw new MallException(ExceptionEnum.INSERT_CATEGORY_ERROR);
        }
        return category.getId();
    }

    @Transactional
    @Override
    public void deleteCategory(Long id) {
        //查询该类目下是否存在子分类
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("parentId",id);
        int count = categoryMapper.selectCountByExample(example);
        if(count>0){
            throw new MallException(ExceptionEnum.EXIST_SUB_CATEGORY);
        }
        //如果没有,该类目可能是一级分类，一级分类肯定不存在商品,如果为二级分类，判断该分类下是否存在商品
        Example example2 = new Example(Product.class);
        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andEqualTo("cid2",id);
        int count2 = productMapper.selectCountByExample(example2);
        if(count2>0){
            throw new MallException(ExceptionEnum.EXIST_PRODUCTS);
        }
        int deleteCount = categoryMapper.deleteByPrimaryKey(id);
        if(deleteCount!=1){
            throw new MallException(ExceptionEnum.DELETE_CATEGORY_ERROR);
        }
    }

    @Transactional
    @Override
    public void updateCategory(Long id, Category category) {
        Category record = categoryMapper.selectByPrimaryKey(id);
        record.setLastUpdateTime(new Date());
        record.setName(category.getName());
        int count = categoryMapper.updateByPrimaryKeySelective(record);
        if(count!=1){
            throw new MallException(ExceptionEnum.UPDATE_CATEGORY_ERROR);
        }
    }

    @Override
    public void loadCategoryName(List<Product> products) {
        for (Product product : products) {
            Category cid1 = categoryMapper.selectByPrimaryKey(product.getCid1());
            Category cid2 = categoryMapper.selectByPrimaryKey(product.getCid2());
            product.setCid1Name(cid1.getName());
            product.setCid2Name(cid2.getName());
        }
    }

    @Override
    public List<Category> queryCategoryList() {
        List<Category> categories = queryCategoryByPid(0L);
        categories.forEach(category -> {
            category.setCategories(queryCategoryByPid(category.getId()));
        });
        return categories;
    }

    @Override
    public Category queryCategoryById(Long cid) {
        Category category = categoryMapper.selectByPrimaryKey(cid);
        return category;
    }
}
