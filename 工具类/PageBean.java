package com.gmv.util;

import lombok.Data;

import java.util.List;

@Data
public class PageBean<T> {

    private List<T> dataList;
    private int total;
}
