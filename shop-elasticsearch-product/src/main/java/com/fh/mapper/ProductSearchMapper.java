package com.fh.mapper;

import com.fh.bean.SearchBean;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

/**
 * @author xiezhuang
 * @ClassName ProductSearchMapper
 * @date 2020/7/2 12:07
 */
public interface ProductSearchMapper extends ElasticsearchCrudRepository<SearchBean,Integer> {
}
