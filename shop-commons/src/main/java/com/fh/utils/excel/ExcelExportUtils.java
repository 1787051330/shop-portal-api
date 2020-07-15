package com.fh.utils.excel;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName ExcelExportUtils
 * @author xiezhuang
 * @date 2020/6/27 17:49
 */
public class ExcelExportUtils {

    public static  void excelExport(List<?> data, Class clazz, HttpServletResponse response) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //获取要到处对象
        ExcelBean excelBean=ResolverObject.getObjectField(clazz);
        //声明工作薄
        XSSFWorkbook workbook=new XSSFWorkbook();
        //创建sheet页
        XSSFSheet sheet=workbook.createSheet();
        //创建title标题行
        XSSFRow titleRow=sheet.createRow(0);
        XSSFCell titleCell=titleRow.createCell(0);
        titleCell.setCellValue(excelBean.getTitle());
        titleCell.setCellStyle(PoiCellStyle.titleStyle(workbook));
        CellRangeAddress cellRangeAddress=new CellRangeAddress(0,0,0,excelBean.getFields().size()-1);
        sheet.addMergedRegion(cellRangeAddress);

        //创建表头
        XSSFRow headerRow=sheet.createRow(1);
        List<String> headers=excelBean.getHeaders();
        for(int i=0;i<headers.size();i++){
            XSSFCell headerCell=headerRow.createCell(i);
            headerCell.setCellValue(headers.get(i));
            headerCell.setCellStyle(PoiCellStyle.colunmStyle(workbook));
        }

        //创建数据行
        List<String> fields=excelBean.getFields();
        for(int i=0;i<data.size();i++){
            XSSFRow dataRow=sheet.createRow(i+2);
            //获取对象
            Object obj=data.get(i);
            for(int j=0;j<fields.size();j++){
                XSSFCell dataCell=dataRow.createCell(j);
                //给单元个赋值
                String fieldName=fields.get(j);
                //获取对应的get方法名
                String methodName=getFieldMethod(fieldName);

                Method method=clazz.getMethod(methodName);
                Object invoke = method.invoke(obj);
                if(invoke instanceof Date){
                    //转成String
                    //就用excel自带的日期格式
                   String dateFormat= DateFormatUtils.format((Date) invoke,"yyyy-MM-dd");
                    dataCell.setCellValue(dateFormat);
                }else{
                    if(invoke != null){
                        dataCell.setCellValue( String.valueOf(invoke));
                    }else{
                        dataCell.setCellValue( "");
                    }

                }
                dataCell.setCellStyle(PoiCellStyle.dataStyle(workbook));
            }
        }

        for(int i=0;i<fields.size();i++){
            sheet.autoSizeColumn((short) i);
            // 解决自动设置列宽中文失效的问题
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 17 / 10);
        }
        //使用response进行导出
        response.setContentType("application/octet-stream");
        //默认Excel名称
        response.setHeader("Content-Disposition", "attachment;fileName="+UUID.randomUUID().toString() +".xlsx");
        try {
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static  String getFieldMethod(String fieldName){
        return "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
    }
}
