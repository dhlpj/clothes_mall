package com.pj.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pj.mall.common.PageResult;
import com.pj.mall.exception.ExceptionEnum;
import com.pj.mall.exception.MallException;
import com.pj.mall.mapper.ProductDetailMapper;
import com.pj.mall.mapper.ProductMapper;
import com.pj.mall.pojo.*;
import com.pj.mall.repository.SearchItemRepository;
import com.pj.mall.service.*;
import com.pj.mall.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @author Jack
 * @create 2019-03-16 18:54
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductDetailMapper productDetailMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SizeService sizeService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private SearchService searchService;
    @Autowired
    private CommentService commentService;

    @Override
    public PageResult<List<Product>> queryProductByPage(Integer page, Integer limit, Boolean saleable,
                                                        String keyword, Long cid, String sortField, String sortType) {
        PageHelper.startPage(page,limit);
        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        if(saleable!=null){
            criteria.andEqualTo("saleable",saleable);
        }
        if(StringUtils.isNotBlank(keyword)){
            criteria.andLike("name","%"+keyword+"%");
        }
        if (cid!=null){//TODO 添加分类筛选

        }
        if (StringUtils.isNotBlank(sortField)&&StringUtils.isNotBlank(sortType)){
            String orderClause = sortField+" "+sortType;
            example.setOrderByClause(orderClause);
        }
        List<Product> products = productMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(products)){
            throw new MallException(ExceptionEnum.PRODUCT_NOT_FOUND);
        }
        PageInfo<Product> pageInfo = new PageInfo<>(products);//TODO 不使用PageInfo的构造函数，spus的结果也是分页的吗
        return new PageResult<>(pageInfo.getTotal(),products);
    }

    @Transactional
    @Override
    public void changeSaleable(Long id, Boolean saleable) {
        //查询product
        Product product = productMapper.selectByPrimaryKey(id);
        if(product==null){
            throw new MallException(ExceptionEnum.PRODUCT_NOT_FOUND);
        }
        product.setSaleable(saleable);
        int count = productMapper.updateByPrimaryKeySelective(product);
        if (count!=1){
            throw new MallException(ExceptionEnum.UPDATE_PRODUCT_ERROR);
        }
        //同步索引库
        if(saleable==true){//添加
            searchService.addProductToIndex(product);
        }else{//删除
            searchService.deleteProductFromIndex(id);
        }
    }

    @Transactional
    @Override
    public void addProduct(Product product) {
        //新增商品
        product.setCreateTime(new Date());
        product.setLastUpdateTime(product.getCreateTime());
        product.setSaleable(true);
        int productCount = productMapper.insert(product);
        if(productCount!=1){
            throw new MallException(ExceptionEnum.INSERT_PRODUCT_ERROR);
        }
        //新增商品详情
        ProductDetail productDetail = product.getProductDetail();
        productDetail.setProductId(product.getId());
        productDetail.setCreateTime(new Date());
        productDetail.setLastUpdateTime(productDetail.getCreateTime());
        int detailCount = productDetailMapper.insert(productDetail);
        if(detailCount!=1){
            throw new MallException(ExceptionEnum.INSERT_PRODUCT_DETAIL_ERROR);
        }
        //新增商品颜色
        colorService.addColors(product);
        //新增到索引库
        searchService.addProductToIndex(product);
    }

    @Override
    public Product queryProductById(Long id) {
        //查询商品
        Product product = productMapper.selectByPrimaryKey(id);
        if(product==null){
            throw new MallException(ExceptionEnum.PRODUCT_NOT_FOUND);
        }
        //填充商品分类名称
        Category category = categoryService.queryCategoryById(product.getCid2());
        if(category==null){
            throw new MallException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        product.setCid2Name(category.getName());
        //查询商品详情
        ProductDetail productDetail = productDetailMapper.selectByPrimaryKey(id);
        product.setProductDetail(productDetail);
        //查询商品颜色及尺寸
        List<Color> colors = colorService.queryColorByProductId(id);
        product.setColors(colors);
        return product;
    }

    @Transactional
    @Override
    public void updateProduct(Long productId, Product product) {
        //1、更新商品
        product.setId(productId);
        product.setLastUpdateTime(new Date());
        int productCount = productMapper.updateByPrimaryKeySelective(product);
        if (productCount!=1){
            throw new MallException(ExceptionEnum.UPDATE_PRODUCT_ERROR);
        }
        //2、更新商品详情
        ProductDetail productDetail = product.getProductDetail();
        productDetail.setProductId(productId);
        productDetail.setLastUpdateTime(new Date());
        int productDetailCount = productDetailMapper.updateByPrimaryKeySelective(productDetail);
        if(productDetailCount!=1){
            throw new MallException(ExceptionEnum.UPDATE_PRODUCT_DETAIL_ERROR);
        }
        //3、删除之前索引库中的记录（需要查询color）
        searchService.deleteProductFromIndex(productId);
        //4、删除color
        colorService.deleteColorByProductId(productId);
        //5、新增商品颜色
        colorService.addColors(product);
        product = productMapper.selectByPrimaryKey(productId);//确保其数据完整性
        //6、同步elasticsearch
        searchService.addProductToIndex(product);
    }

    @Transactional
    @Override
    public void deleteProducts(List<Long> ids) {
        //删除索引库中的记录
        for (Long productId : ids) {
            searchService.deleteProductFromIndex(productId);
        }
        //删除product
        int count = productMapper.deleteByIdList(ids);
        if (count!=ids.size()){
            throw new MallException(ExceptionEnum.DELETE_PRODUCT_ERROR);
        }
        //删除productDetail
        productDetailMapper.deleteByIdList(ids);
        for (Long productId : ids) {
            //删除color及size
            colorService.deleteColorByProductId(productId);
            //删除评论
            commentService.deleteCommentByProductId(productId);

        }
    }

    @Override
    public List<Product> queryProductByCategory(Long cid1, Long cid2) {
        Product product = new Product();
        if (cid1!=null){
            product.setCid1(cid1);
        }
        if (cid2!=null){
            product.setCid2(cid2);
        }
        List<Product> products = productMapper.select(product);
        return products;
    }

    @Override
    public Map<String,Object> loadModel(Long productId, Long colorId) {
        Product product = queryProductById(productId);
        Map<String,Object> map = new HashMap<>();
        Category cid1 = categoryService.queryCategoryById(product.getCid1());
        Category cid2 = categoryService.queryCategoryById(product.getCid2());
        map.put("productId",productId);
        map.put("saleable",product.getSaleable());
        map.put("cid1",cid1);
        map.put("cid2",cid2);
        map.put("name",product.getName());
        map.put("detail",product.getProductDetail());
        map.put("colors",product.getColors());
        Color color = colorService.queryColorById(colorId);
        map.put("currentColor",color);
        //当前颜色的尺寸
        List<Size> sizes = sizeService.querySizeByColorId(colorId);
        map.put("sizes",sizes);
        // 前台ajax查询评论
        return map;
    }

    @Override
    public Product queryOnlyProductById(Long productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        return product;
    }

}
