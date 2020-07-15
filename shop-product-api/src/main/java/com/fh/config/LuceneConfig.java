package com.fh.config;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.ControlledRealTimeReopenThread;
import org.apache.lucene.search.SearcherFactory;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author xiezhuang
 * @ClassName LuceneConfig
 * @date 2020/6/30 12:16
 */
@Configuration
public class LuceneConfig {

    /**
     * 索引存放的位置
     */
    private static final String LUCENEINDEXPATH = "lucene/indexDir/";

    /**
     * 创建中文分词器的bean
     * @return
     */
    @Bean
    public Analyzer analyzer(){
        return new SmartChineseAnalyzer();
    }

    /**
     * 创建索引存放的文件目录
     * @return
     */
    @Bean
    public Directory directory() throws IOException {
        Path path = Paths.get(LUCENEINDEXPATH);
        File file = path.toFile();
        if(!file.exists()){
            file.mkdirs();
        }
        return FSDirectory.open(path);
    }

    /**
     * 创建索引写的方法indexWriter
     * @param directory
     * @param analyzer
     * @return
     */

    @Bean
    public IndexWriter indexWriter(Directory directory,Analyzer analyzer) throws IOException {
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory,indexWriterConfig);
        //清空索引
        indexWriter.deleteAll();
        indexWriter.commit();
        return indexWriter;
    }

    /**
     * SearchManager管理的search能获得最新的索引库，下面是每25S执行一次。
     * @param directory
     * @param indexWriter
     * @return
     */
    @Bean
    public SearcherManager searcherManager(Directory directory,IndexWriter indexWriter) throws IOException {
          /*
        SearcherManager参数解释
           IndexWriter writer
           boolean applyAllDeletes 如果为 true，那么在可见模式（made visible）
           下索引搜索器 IndexSearcher 和 DirectoryReader 词典阅读器中所有的缓冲都奖被删除。
           如果为 false，那么所有的缓冲可能会被删除，也可能不会被删除，取决于是否 commit，
           缓存会被保持缓冲在 IndexWriter 中。以便将来应用它们，删除的代价是非常高的，如果你非要这样做
           boolean writeAllDeletes 如果为True当进行写入操作时，那么所有的缓冲可能会被删除，重新加载，false话提交时触发
           SearcherFactory searcherFactory
        */
        SearcherManager searcherManager = new SearcherManager(indexWriter,false,false,new SearcherFactory());
        /**
         * targetMaxStaleSec-如果没有用户着急获取新Searcher，则等待最大时间间隔后再打开
         * targetMinStaleSec-当有外部用户在等待重新打开Searcher时就按最小时间间隔等待，
         */
        ControlledRealTimeReopenThread controlledRealTimeReopenThread = new ControlledRealTimeReopenThread(indexWriter,searcherManager,5.0,0.025);
        controlledRealTimeReopenThread.setDaemon(true);
        //设置线程名称
        controlledRealTimeReopenThread.setName("更新IndexReader线程");
        //开启线程
        controlledRealTimeReopenThread.start();
        return searcherManager;
    }

}
