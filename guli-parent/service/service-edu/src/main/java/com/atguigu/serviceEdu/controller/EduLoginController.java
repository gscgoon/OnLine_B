package com.atguigu.serviceEdu.controller;

import com.atguigu.commonUtils.R;
import org.springframework.web.bind.annotation.*;

/**
 * @author Super
 * @date 2020-03-30 1:02
 */
@RestController
@RequestMapping("/admin/serviceEdu/user")
//@CrossOrigin 因为使用了spring cloud 的gateway网关配置，所以不需要再次配置跨域注解
public class EduLoginController {

    @PostMapping("/login")
    public  R login(){
        return R.ok().data("token","admin");
    }

    @GetMapping("/info")
    public R info(){
        return R.ok().data("roles","11111").data("name","super").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
