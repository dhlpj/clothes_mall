package com.pj.mall.vo;

/**
 * @author Jack
 * @create 2019-04-07 22:41
 */
public class SearchRequest {
    private String key;// 搜索条件

    private Integer page;// 当前页

    private String sortBy;// 排序字段

    private Boolean descending;// 是否降序

    private Long cid1;//一级分类过滤信息

    private Long cid2;//二级分类过滤信息

    private static final Integer DEFAULT_SIZE = 8;// 每页大小，不从页面接收，而是固定大小 TODO 修改大小
    private static final Integer DEFAULT_PAGE = 1;// 默认页

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getPage() {
        if(page == null){
            return DEFAULT_PAGE;
        }
        // 获取页码时做一些校验，不能小于1
        return Math.max(DEFAULT_PAGE, page);
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return DEFAULT_SIZE;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Boolean getDescending() {
        return descending;
    }

    public void setDescending(Boolean descending) {
        this.descending = descending;
    }

    public Long getCid1() {
        return cid1;
    }

    public void setCid1(Long cid1) {
        this.cid1 = cid1;
    }

    public Long getCid2() {
        return cid2;
    }

    public void setCid2(Long cid2) {
        this.cid2 = cid2;
    }
}
