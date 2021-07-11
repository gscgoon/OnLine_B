package com.atguigu.serviceEdu.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Super
 * @date 2020-04-05 14:18
 */
@ApiModel(value = "课程发布信息")
@Data
public class CoursePublishVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    //只用于显示
    private String price;
}
