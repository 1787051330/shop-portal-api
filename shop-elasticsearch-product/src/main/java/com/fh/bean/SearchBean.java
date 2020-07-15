package com.fh.bean;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @author xiezhuang
 * @ClassName SearchBean
 * @date 2020/7/2 11:30
 */
@Data
@Accessors(chain = true)
@Document(indexName = "shop_product",type = "blog")
public class SearchBean implements Serializable {
    @Id
    private Integer id;
    @Field(type = FieldType.Keyword,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String name;
    @Field(type = FieldType.Keyword,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String subtitle;
    @Field(type = FieldType.Keyword,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String detail;
    @Field(type = FieldType.Double)
    private String price;
    @Field(type = FieldType.Integer)
    private Integer stock;
    @Field(type = FieldType.Integer)
    private Integer status;
    @Field(type = FieldType.Date,format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss")
    private String createtime;
}
