package com.fh.service.impl;

import com.fh.mapper.LuceneMapper;
import com.fh.mapper.ProductMapper;
import com.fh.po.ProductBeanPo;
import com.fh.service.ILuceneService;
import com.fh.vo.SearchBean;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author xiezhuang
 * @ClassName LuceneServiceImpl
 * @date 2020/6/30 12:38
 */
@Service("luceneService")
public class LuceneServiceImpl implements ILuceneService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private LuceneMapper luceneMapper;

    @Override
    public void syncProductCreateindex() throws IOException {
        List<ProductBeanPo> productBeanList = productMapper.selectList(null);
        if(productBeanList.size() > 0){
            luceneMapper.createProductIndex(productBeanList);
        }
    }

    @Override
    public List<ProductBeanPo> searchProduct(SearchBean searchBean) throws IOException, ParseException {
        return luceneMapper.searchProduct(searchBean);
    }
}
