package com.fh.service;

import com.fh.po.ProductBeanPo;
import com.fh.vo.SearchBean;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;
import java.util.List;

/**
 * @author xiezhuang
 * @ClassName ILuceneService
 * @date 2020/6/30 12:38
 */
public interface ILuceneService {

    public void syncProductCreateindex() throws IOException;

    List<ProductBeanPo> searchProduct(SearchBean searchBean) throws IOException, ParseException;
}
