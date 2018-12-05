package com.xin.lemontree.tools.page;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.util.ObjectUtils;

/**
 * @author creator mafh 2018/4/26 10:15
 * @author updater mafh
 * @version 1.0.0
 * @description 分页对象
 */
public class Pageable {

    /**
     * 当前页
     */
    private Integer pageNo = 0;

    /**
     * 每页条数
     */
    private Integer pageSize = 10;

    /**
     * 总记录数
     */
    @ApiModelProperty(hidden = true)
    private Integer totalCount;

    /**
     * 总页数
     */
    @ApiModelProperty(hidden = true)
    private Integer totalPageCount;

    private static Pageable pageable = new Pageable();

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo - 1;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(Integer totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    /**
     * 获取分页对象（单例）
     *
     * @param pageNo   当前页
     * @param pageSize 页面记录数
     * @return 对象
     */
    public static Pageable getPageable(Integer pageNo, Integer pageSize) {
        if (!ObjectUtils.isEmpty(pageNo)) {
            pageable.setPageNo(pageNo);
        }
        if (!ObjectUtils.isEmpty(pageSize)) {
            pageable.setPageSize(pageSize);
        }
        return pageable;
    }

}
