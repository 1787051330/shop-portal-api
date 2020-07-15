package com.fh.utils;

import com.aliyun.oss.OSSClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @ClassName CopyFile
 * @author xiezhuang
 * @date 2020/6/27 17:49
 */
public class CopyFile {

    public static  final String endpoint = "oss-cn-beijing.aliyuncs.com";

    public static  final String accessKeyId = "LTAI4FkJc5YBgDZFkgQsaRh6";

    public  static  final String accessKeySecret = "uvdV6YsKxRcBSMcqO0wOnZlobVAhaV";

    public static final String bucketName = "workspace1905";

    public static String copyFile (CommonsMultipartFile photo, String mkdirName){
        //临时文件重命名
        String oldName = photo.getOriginalFilename();//原来的旧名字.后缀
        //获取时间戳
        long time = System.currentTimeMillis();
        //截取后缀名
        String suffix = oldName.substring(oldName.lastIndexOf("."));
        //新名字
        String newFileName = time+suffix;
        //拼接保存的路径
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        //获取项目在电脑中的绝对路径
        String realPath = request.getServletContext().getRealPath("/");
        realPath = realPath+"commons/"+mkdirName;

        //判断存放图片的文件夹是否存在

        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        try {
            photo.transferTo(new File(realPath+"/"+newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "commons/"+mkdirName+"/"+newFileName;
    }

    //oss调用接口

      public static String FileUploadOSS(MultipartFile file, String oldName, byte[] bytes, String mkdirName){
          OSSClient ossClient=new OSSClient(CopyFile.endpoint, CopyFile.accessKeyId, CopyFile.accessKeySecret);
          //给临时文件重命名
          //获取老文件名+后缀
          //截取后缀名   suffixName：文件后缀名
          String suffixName = oldName.substring(oldName.lastIndexOf("."));
          //给前缀名重命名   uuID/时间戳
          long time=System.currentTimeMillis();
          //新文件名
          String newName=time+suffixName;
          //获取request对象
          HttpServletRequest request=((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest();
          //获取绝对路径
          String realPath = request.getServletContext().getRealPath("/");
          String path = mkdirName+"/"+newName;
          ossClient.putObject(bucketName,path,new ByteArrayInputStream(bytes));
          //设置过期时间 -- 十年
          Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
          //取出文件上传路径
          String url = ossClient.generatePresignedUrl(bucketName, path,expiration).toString();
          return url;
      }
}
