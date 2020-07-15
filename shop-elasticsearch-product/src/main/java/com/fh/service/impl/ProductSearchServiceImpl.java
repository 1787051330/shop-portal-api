package com.fh.service.impl;

import com.fh.bean.PageBean;
import com.fh.bean.SearchBean;
import com.fh.service.IProductSearchService;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

/**
 * @author xiezhuang
 * @ClassName ProductSearchServiceImpl
 * @date 2020/7/2 12:03
 */
@Service("productSearchService")
public class ProductSearchServiceImpl implements IProductSearchService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public PageBean<SearchBean> queryProductSearchList(PageBean pageBean,String name) {
        //条件构造器
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        if (name != null && name != "") {
            nativeSearchQueryBuilder.withQuery(QueryBuilders.queryStringQuery(name));
        }
        nativeSearchQueryBuilder.withPageable(PageRequest.of(pageBean.getCurrent() - 1, pageBean.getSize()));
        AggregatedPage<SearchBean> searchBeans = elasticsearchTemplate.queryForPage(nativeSearchQueryBuilder.build(), SearchBean.class);
        pageBean.setData(searchBeans.getContent());
        pageBean.setTotal(searchBeans.getTotalElements());
        return pageBean;
    }
}
