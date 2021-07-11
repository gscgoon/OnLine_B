package com.atguigu.serviceMsm.controller;

import com.atguigu.commonUtils.R;
import com.atguigu.serviceMsm.service.MsmService;
import com.atguigu.serviceMsm.utils.RandomUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author Super
 * @date 2020-04-08 22:54
 */
@Api(description = "发送验证码")
@RestController
@RequestMapping("/admin/serviceMsm/msm")
//@CrossOrigin 因为使用了spring cloud 的gateway网关配置，所以不需要再次配置跨域注解
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("/getPhone/{phoneNumber}")
    public R sendNumber(@PathVariable String phoneNumber){

        //1.查redis,返回验证码
        String code = redisTemplate.opsForValue().get("phoneNumber");
        if(!StringUtils.isEmpty(code)){
            return R.ok();
        }
        //2.生成验证码
        //生成随机码
        code = RandomUtil.getSixBitRandom();
        HashMap<String,Object> param = new HashMap<>();
        param.put("code",code);
        //返回标识
        boolean flag = msmService.sendNumber(phoneNumber,param);
        if(flag){
            //将验证码存到redis中
            redisTemplate.opsForValue().set(phoneNumber,code,5,TimeUnit.MINUTES);
            return R.ok().message("验证码发送成功！");
        }else {
            return R.error().message("验证码发送失败！");
        }
    }
}
