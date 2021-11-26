package com.zhangyongsic.framework.mybatisplus.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhang yong
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PageOut<T> implements Serializable {

    private static final long serialVersionUID = -2515866491867868122L;

    /**
     * 页号
     */
    @ApiModelProperty(value = "页号")
    private long pageNo;

    /**
     * 页大小
     */
    @ApiModelProperty(value = "页大小")
    private long pageSize;

    /**
     * 页数
     */
    @ApiModelProperty(value = "总页数")
    private long pageCount;


    /**
     * 总记录数
     */
    @ApiModelProperty(value = "总记录数")
    private long totalCount;


    private String scrollId;

    /**
     * 数据
     */
    @ApiModelProperty(value = "列表数据")
    private List<T> rows;

    @Override
    public String toString() {
        return "PageOut [pageNo=" + pageNo
                + ", pageSize=" + pageSize
                + ", pageCount=" + pageCount
                + ", totalCount=" + totalCount
                + ", rows=" + rows + "]";
    }

    public PageOut() {
    }

    public PageOut(long pageNo, long pageSize, long pageCount, long totalCount) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.pageCount = pageCount;
        this.totalCount = totalCount;
    }
}
