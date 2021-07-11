package com.atguigu.serviceBase.handler;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义异常类
 */

/**
 * @author Super
 * @date 2020-04-01 14:49
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliException extends  RuntimeException{

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 信息
     */
    private  String message;
}
