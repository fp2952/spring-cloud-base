package com.peng.common.pojo;


/**
 * 基础查询参数实体
 */
public class BaseRequestPojo {

    protected Integer pageNum;

    protected Integer pageSize;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
