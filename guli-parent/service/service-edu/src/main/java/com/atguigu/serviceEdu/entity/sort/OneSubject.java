package com.atguigu.serviceEdu.entity.sort;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * 一级分类课程
 */

/**
 * @author Super
 * @date 2020-04-01 16:33
 */
@Data
public class OneSubject {
    /**
     * 一级分类id
     */
    private String id;

    /**
     * 一级分类title
     */
    private String title;

    /**
     * 由于显示到页面的是树形结构，所以建立一级和二级的关系，将二级分类课程作为一级课程的属性
     */
    private List<TwoSubject> children = new ArrayList<>();

}
