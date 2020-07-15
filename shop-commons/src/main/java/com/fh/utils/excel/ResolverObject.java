package com.fh.utils.excel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ResolverObject
 * @author xiezhuang
 * @date 2020/6/27 17:49
 */
public class ResolverObject {

    public static ExcelBean getObjectField(Class clazz){
        ExcelAnnotation excelAnnotation= (ExcelAnnotation) clazz.getAnnotation(ExcelAnnotation.class);
        ExcelBean excelBean=new ExcelBean();
        if(excelAnnotation != null ){
            excelBean.setTitle(excelAnnotation.title());
        }
        //获取类中的全部属性
        Field[] declaredFields = clazz.getDeclaredFields();
        List<String> headers=new ArrayList<String>();
        List<String> fields=new ArrayList<String>();
        for (Field declaredField : declaredFields) {
            ExcelAnnotation fieldAnno=declaredField.getAnnotation(ExcelAnnotation.class);
            if(fieldAnno != null){
                headers.add(fieldAnno.header());
                fields.add(declaredField.getName());

            }
        }
        excelBean.setFields(fields);
        excelBean.setHeaders(headers);
       return excelBean;

    }
}
