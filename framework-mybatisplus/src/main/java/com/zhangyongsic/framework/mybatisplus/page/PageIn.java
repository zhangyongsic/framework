package com.zhangyongsic.framework.mybatisplus.page;

import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * @author fanchao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PageIn implements Serializable {

    private static final long serialVersionUID = -6387419775146484644L;

    /**
     * 页号
     */
    @ApiModelProperty(value = "页号")
    private int pageNo = 1;

    /**
     * 页大小
     */
    @ApiModelProperty(value = "页大小")
    private int pageSize = 20;

    /**
     * 排序规则 asc(升序) desc(降序)
     */
    @ApiModelProperty(value = "排序规则 asc(升序) desc(降序)")
    private String sortOrder;

    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    private String orderBy;

    @ApiModelProperty
    private String wd;

    @Override
    public String toString() {
        return "PageIn [pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", sortOrder=" + sortOrder +
                ", orderBy=" + orderBy
                + "]";
    }

}
