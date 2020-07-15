package com.fh.mapper;

import com.fh.po.ProductBeanPo;
import com.fh.vo.SearchBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiezhuang
 * @ClassName LuceneMapper
 * @date 2020/6/30 13:26
 */
@Repository
public class LuceneMapper {

    @Autowired
    private IndexWriter indexWriter;

    @Autowired
    private Analyzer analyzer;

    @Autowired
    private SearcherManager searcherManager;

    /**
     * 给索引文件赋值
     * @param productBeanList
     * @throws IOException
     */
    public void createProductIndex(List<ProductBeanPo> productBeanList) throws IOException {
        List<Document> docs = new ArrayList<Document>();
        productBeanList.forEach(product->{
            Document doc = new Document();
            doc.add(new StringField("id",product.getId()+"", Field.Store.YES));
            doc.add(new TextField("name",product.getName(),Field.Store.YES));
            doc.add(new TextField("subtitle",product.getSubtitle(),Field.Store.YES));
            doc.add(new TextField("detail",product.getDetail(),Field.Store.YES));
            doc.add(new DoubleDocValuesField("price",product.getPrice().doubleValue()));
            doc.add(new StoredField("price",product.getPrice().doubleValue()));
            doc.add(new StringField("stock",product.getStock()+"",Field.Store.YES));
            doc.add(new StringField("status",product.getStatus()+"",Field.Store.YES));
            docs.add(doc);
        });
        indexWriter.addDocuments(docs);
        indexWriter.commit();
    }

    /**
     * lucene查询
     * @param searchBean
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public List<ProductBeanPo> searchProduct(SearchBean searchBean) throws IOException, ParseException {
        searcherManager.maybeRefresh();
        IndexSearcher acquire = searcherManager.acquire();
        //组装条件构造器
        BooleanQuery.Builder booleanClauses = new BooleanQuery.Builder();
        if(StringUtils.isNotBlank(searchBean.getName())){
            booleanClauses.add(new QueryParser("name", analyzer).parse(searchBean.getName()), BooleanClause.Occur.MUST);
        }

        //没条件展示全部数据 使用通配符
        if(CollectionUtils.isEmpty(booleanClauses.build().clauses())){
            booleanClauses.add(new WildcardQuery(new Term("name","*")),BooleanClause.Occur.SHOULD);
        }

        //排序
        String ASC = "ASC";
        String DESC = "DESC";
        Sort sort = new Sort();
        if(StringUtils.isNotBlank(searchBean.getField()) && StringUtils.isNotBlank(searchBean.getOrder())){
            if(ASC.equals(searchBean.getOrder().toUpperCase())){
                sort.setSort(new SortField(searchBean.getField(),SortField.Type.DOUBLE,false));
            }else if(DESC.equals(searchBean.getOrder().toUpperCase())){
                sort.setSort(new SortField(searchBean.getField(),SortField.Type.DOUBLE,true));
            }
        }

        //开始查询
        TopDocs search = acquire.search(booleanClauses.build(), 10,sort);
        ScoreDoc[] docs = search.scoreDocs;
        List<ProductBeanPo> list = new ArrayList<>();
        for (int i = 0; i < docs.length; i++) {
            Document doc = acquire.doc(docs[i].doc);
            ProductBeanPo productBeanPo = new ProductBeanPo();
            productBeanPo.setId(Integer.valueOf(doc.get("id")));
            productBeanPo.setName(doc.get("name"));
            productBeanPo.setSubtitle(doc.get("subtitle"));
            productBeanPo.setDetail(doc.get("detail"));
            productBeanPo.setPrice(new BigDecimal(doc.get("price")));
            productBeanPo.setStock(Integer.valueOf(doc.get("stock")));
            productBeanPo.setStatus(Integer.valueOf(doc.get("status")));
            list.add(productBeanPo);
        }
        return list;
    }
}
