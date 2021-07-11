package com.atguigu.serviceEdu.entity.excel;
import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;


/**
 * @author Super
 * @date 2020-04-01 10:36
 */
@Data
public class ExcelSubjectData  {

    /**
     * 对应课程分类excel的两列属性一级分类
     */
    @ExcelProperty(index = 0)
    private String oneSubjectSort;

    /**
     * 对应课程分类excel的两列属性二级分类
     */
    @ExcelProperty(index = 1)
    private String twoSubjectSort;

}
