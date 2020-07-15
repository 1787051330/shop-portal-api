package com.fh.service;

import com.fh.bean.PageBean;
import com.fh.bean.SearchBean;

/**
 * @author xiezhuang
 * @ClassName IProductSearchService
 * @date 2020/7/2 12:03
 */
public interface IProductSearchService {
    PageBean<SearchBean> queryProductSearchList(PageBean pageBean,String name);
}
