package com.example.courses.music.model.response;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import com.example.courses.music.model.Base;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * 分页响应
 */
public class PageResponse extends Base {
    /**
     * 总数
     */
    protected long total = 0;

    /**
     * 分页总数
     */
    protected long pages = 0;

    /**
     * 每页显示条数，默认 10
     */
    protected long size;

    /**
     * 当前页
     */
    protected long page = 1;

    /**
     * 上一页
     * <p>
     * 当然这些数据，客户端也可以计算
     */
    protected Long previous;

    /**
     * 下一页
     */
    protected Long next;

    /**
     * 真实列表数据
     */
    private Object data;

    public PageResponse() {
    }

    public PageResponse(List data) {
        this.data = data;
    }

    public PageResponse(long total, long pages, long page, long size, List data) {
        this.total = total;
        this.pages = pages;
        this.page = page;
        this.size = size;

        if (CollectionUtils.isNotEmpty(data)) {
            this.data = data;
        }

        //计算上一页
        if (page > 1) {
            previous = page - 1;
        } else {
            previous = null;
        }

        //计算下一页
        if (page >= pages) {
            next = null;
        } else {
            next = page + 1;
        }
    }

    public static PageResponse create(IPage data) {
        return new PageResponse(data.getTotal(), data.getPages(), data.getCurrent(), data.getSize(), data.getRecords());
    }

    public static Object create(List data) {
        return new PageResponse(data);
    }

    public static Object create(PageInfo data) {
        return new PageResponse(data.getTotal(), data.getPages(), data.getPageNum(), data.getPageSize(), data.getList());
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public Long getNext() {
        return next;
    }

    public void setNext(Long next) {
        this.next = next;
    }

    public Long getPrevious() {
        return previous;
    }

    public void setPrevious(Long previous) {
        this.previous = previous;
    }
}
