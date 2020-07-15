package com.fh.bean;

import lombok.Data;

import java.util.List;

/**
 * @author xiezhuang
 * @ClassName PageBean
 * @date 2020/7/2 11:27
 */
@Data
public class PageBean<T> {

    /**
     * 当前页数
     */
    private Integer current;

    /**
     * 每页条数
     */
    private Integer size;
    /**
     * 总条数
     */
    private long total;
    /**
     * 数据存放
     */
    private List<?> data;


}
