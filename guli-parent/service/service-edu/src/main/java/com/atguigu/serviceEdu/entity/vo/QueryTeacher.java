package com.atguigu.serviceEdu.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Super
 * @date 2020-03-29 10:38
 */
@Data
@ApiModel(value = "Teacher查询对象", description = "讲师查询对象封装")
public class QueryTeacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名字，模糊查询")
    private String name;

    @ApiModelProperty(value = "头衔 1 高级讲师 2 首席讲师")
    private Integer level;

    @ApiModelProperty(value = "查询开始时间",example = "2019-11-15 21:47:12")
    private String begin;

    @ApiModelProperty(value = "查询结束时间",example = "2019-11-15 21:47:12")
    private String end ;
}
