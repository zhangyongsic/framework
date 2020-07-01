package com.zhangyongsic.framework.mybatisplus.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.zhangyongsic.framework.mybatisplus.page.PageIn;
import com.zhangyongsic.framework.mybatisplus.page.PageOut;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * @author fanchao
 */
public class PageUtil {

    private static String DESC = "desc";

    /**
     * @param pageIn
     * @return
     */
    public static Page in(PageIn pageIn) {
        Page page = new Page();
        // pageNo 页码
        page.setCurrent(pageIn.getPageNo());
        // pageSize 一页数据条数
        page.setSize(pageIn.getPageSize());
        // orderBy 排序字段
        if (StringUtils.isNotBlank(pageIn.getOrderBy())) {
            // 排序规则
            if (StringUtils.isNotBlank(pageIn.getSortOrder()) && pageIn.getSortOrder().equals(DESC)) {
                page.addOrder(OrderItem.desc(pageIn.getOrderBy()));
            } else {
                page.addOrder(OrderItem.asc(pageIn.getOrderBy()));
            }
        }
        return page;
    }

    /**
     * @param page
     * @return
     */
    public static PageOut out(IPage page) {
        PageOut pageOut = new PageOut();
        pageOut.setPageNo(page.getCurrent());
        pageOut.setPageSize(page.getSize());
        pageOut.setPageCount(page.getPages());
        pageOut.setTotalCount(page.getTotal());
        pageOut.setRows(page.getRecords());
        return pageOut;
    }

}
