package com.atguigu.serviceBase.handler;

import com.atguigu.commonUtils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Super
 * @date 2020-04-01 14:40
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error();
    }


    /**
     * 自定义异常类
     * @param e
     * @return
     */
    public R error(GuliException e){
        e.printStackTrace();
        return R.error().message(e.getMessage()).code(e.getCode());
    }
}
