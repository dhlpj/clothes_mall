package com.pj.mall.vo;

import lombok.Data;

/**
 * @author Jack
 * @create 2019-03-13 12:20
 */
@Data
public class CategoryNode {
    /** 节点ID*/
    private String id;
    /** 上级节点ID*/
    private String parentId;
    /** 节点名称*/
    private String title;
    /** 是否最后一级节点*/
    private Boolean isLast;
}
