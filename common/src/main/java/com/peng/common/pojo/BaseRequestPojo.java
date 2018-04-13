package com.peng.common.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 基础查询参数实体
 */
@ApiModel(value = "BaseRequestPojo", description = "表格请求体")
public class BaseRequestPojo {

    @ApiModelProperty("页码")
    protected Integer pageNum;

    @ApiModelProperty("每页条数")
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
