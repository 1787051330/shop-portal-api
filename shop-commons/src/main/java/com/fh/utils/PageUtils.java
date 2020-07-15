package com.fh.utils;

import lombok.Data;

/**
 * @ClassName PageUtils
 * @author xiezhuang
 * @date 2020/6/27 17:49
 */
@Data
public class PageUtils {

    private Long count;

    private Long page;

    private Long limit;

    private Integer code;

    private String msg;

    private Object data;

    public PageUtils(Object data, Long count){
        this.code = 0;
        this.msg="查询成功";
        this.count=count;
        this.data=data;
    }
    public PageUtils(){

    }

}
