package com.atguigu.serviceOss.contorller;

import com.atguigu.commonUtils.R;
import com.atguigu.serviceOss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Super
 * @date 2020-03-31 17:03
 */
@Api(description = "oss文件模块")
//@CrossOrigin 因为使用了spring cloud 的gateway网关配置，所以不需要再次配置跨域注解
@RestController
@RequestMapping("/admin/serviceOss/oss")
public class OssController {

    @Autowired
    private FileService fileService;

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public R upload(@ApiParam(name = "file",value = "上传的文件",required = true)
                    @RequestParam("file") MultipartFile file
                    ){
        //上传文件,返回文件地址
        String fileUrl = fileService.upload(file);
        return R.ok().message("文件上传成功！").data("fileUrl",fileUrl);
    }
}
