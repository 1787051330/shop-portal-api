package com.fh.utils.excel;

import lombok.Data;

import java.util.List;


/**
 * @ClassName ExcelBean
 * @author xiezhuang
 * @date 2020/6/27 17:49
 */
@Data
public class ExcelBean {

    private String title;
    private List<String> headers;
    private List<String> fields;
}
