package com.atguigu.serviceUserCenter.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Super
 * @date 2020-04-08 23:55
 */
@Data
@ApiModel(value = "注册对象",description = "注册对象信息")
public class RegisterVo {

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String code;
}
