package com.gmv.util;


/**
 * 分页对象
 */
public class Page {

    private int offset = 0;

    private int pageSize = 10000;

    public void nextOffset(){
        offset = offset + pageSize;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
