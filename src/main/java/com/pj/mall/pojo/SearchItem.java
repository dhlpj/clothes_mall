package com.pj.mall.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * 保存的都是上架的商品
 * @author Jack
 * @create 2019-04-07 10:52
 */
@Data
@Document(indexName = "mall",type = "docs",shards = 1,replicas = 0)//索引库的名称,索引库中的类型，分片数，副本数
public class SearchItem {
    @Id
    @Field(type = FieldType.Keyword)
    private String id;//productId和colorId的json格式

    @Field(type=FieldType.Text, analyzer = "ik_max_word")
    private String all;//所有需要被分词搜索的信息（key），包含商品名称，分类，颜色，其余的都是过滤信息（面包屑）

    @Field(type = FieldType.Keyword,index = false)
    private String name;//商品名称，加上颜色
    //无论是什么类型，index默认都是true
    //自动创建映射
    //private Long productId;//商品id
    //private Long colorId;//颜色id
    private Long cid1;//一级分类id
    private Long cid2;//二级分类id
    private Long price;//价格

    //自动创建映射,默认将时间转换为long
    private Date createTime;//color创建时间

    @Field(type = FieldType.Keyword,index = false)
    private String image;//该分类下该颜色对应的一张图片
}
