package com.cnpc.framework.base.pojo;

import java.util.ArrayList;
import java.util.List;

public class PageInfo {

    public PageInfo() {

        pageNum = 1;
        pageSize = 10;
    }

    public PageInfo(Integer pageSize) {

        this.pageNum = 1;
        this.pageSize = pageSize;
    }

    // 当前页数
    private int pageNum;

    // 每页记录数
    private int pageSize;

    // 总记录数
    private int count;

    // 总页数
    private int pageCount;

    // 结果集
    List pojoList = new ArrayList();

    public int getPageNum() {

        return pageNum;
    }

    public void setPageNum(int pageNum) {

        this.pageNum = pageNum;
    }

    public int getPageSize() {

        return pageSize;
    }

    public void setPageSize(int pageSize) {

        this.pageSize = pageSize;
    }

    public int getCount() {

        return count;
    }

    public void setCount(int count) {

        this.count = count;
    }

    public int getPageCount() {

        pageCount = count / pageSize;
        if (count % pageSize > 0) {
            ++pageCount;
        }
        return pageCount;
    }

    public List getPojoList() {

        return pojoList;
    }

    public void setPojoList(List pojoList) {

        this.pojoList = pojoList;
    }

}
