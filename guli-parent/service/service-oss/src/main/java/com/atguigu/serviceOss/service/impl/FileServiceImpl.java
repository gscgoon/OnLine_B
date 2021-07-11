package com.atguigu.serviceOss.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.atguigu.serviceOss.service.FileService;
import com.atguigu.serviceOss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author Super
 * @date 2020-03-31 17:18
 */
@Service
public class FileServiceImpl implements FileService {

    @Override
    public String upload(MultipartFile file) {

        //1.获取阿里云oss常量
        String endpoint = ConstantPropertiesUtil.END_POINT;
        String keyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String keySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        String fileHost = ConstantPropertiesUtil.FILE_HOST;

        //2.定义文件路径
        String uploadUrl = null;

        try {
            //3.判断oss实例是否存在
            OSSClient ossClient = (OSSClient) new OSSClientBuilder().build(endpoint,keyId,keySecret);
            if(!ossClient.doesBucketExist(bucketName)){
                //创建
                ossClient.createBucket(bucketName);
                //设置权限
                ossClient.setBucketAcl(bucketName,CannedAccessControlList.PublicRead);
            }

            //4.获取上传文件流
            InputStream inputStream = file.getInputStream();

            //5.文件存放路径
            String filePath = new DateTime().toString("yyyy/MM/dd");

            //6.文件名
            //源文件
            String originalFilename = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString().replaceAll("-","");
            //后缀名定义文件类型
            String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
            //文件重命名
            String fileNewName = fileName + fileType;
            //文件路径
            String fileUrl = fileHost + "/" + filePath + "/" + fileNewName ;

            //7.文件上传阿里云oss
            ossClient.putObject(bucketName,fileUrl,inputStream);

            //8.关闭oss
            ossClient.shutdown();

            // https://gulixueyuan-super.oss-cn-beijing.aliyuncs.com/photo/2020-40-16/3b88c147-af42-4b90-897e-f3f6c2f2156a.png
            //9.获取文件的上传路径
            uploadUrl = "https://" + bucketName + "." + endpoint +  "/" + fileUrl;

        } catch (IOException e) {
            e.printStackTrace();
        }

        //返回文件上传路径
        return uploadUrl;
    }
}
